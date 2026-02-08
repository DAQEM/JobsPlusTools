package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.replacer.MultiBlockReplacerMode;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class HarvesterItem extends HoeItem {

    public HarvesterItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Properties properties) {
        super(toolMaterial, attackDamage, attackSpeed, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        InteractionResult result = super.useOn(context);

        Player player = context.getPlayer();
        if (player == null || player.isCrouching()) {
            return result;
        }

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();

        boolean isSuccess = result == InteractionResult.SUCCESS;
        boolean isFarmland = level.getBlockState(clickedPos).is(Blocks.FARMLAND);

        if (!isSuccess && !isFarmland) {
            return result;
        }

        ItemStack stack = context.getItemInHand();
        if (stack.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
            ModeItemComponent modeComponent = stack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());

            if (modeComponent != null) {
                IModeType modeType = modeComponent.getModeType();
                IMode selectedMode = modeType.getSelectedMode(modeComponent);

                if (selectedMode instanceof MultiBlockReplacerMode mode) {
                    int rangeX = mode.getRangeX() / 2;
                    int rangeZ = mode.getRangeZ() / 2;

                    if (rangeX == 0 && rangeZ == 0) return result;

                    if (context.getClickedFace() != Direction.DOWN) {
                        boolean aoeWorked = false;

                        for (int x = -rangeX; x <= rangeX; x++) {
                            for (int z = -rangeZ; z <= rangeZ; z++) {
                                if (x == 0 && z == 0) continue;

                                BlockPos targetPos = clickedPos.offset(x, 0, z);

                                if (tryTillBlock(context, targetPos, player, level)) {
                                    aoeWorked = true;
                                }
                            }
                        }

                        if (result != InteractionResult.SUCCESS && aoeWorked) {
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }

        return result;
    }

    private boolean tryTillBlock(UseOnContext originalContext, BlockPos targetPos, Player player, Level level) {
        BlockState state = level.getBlockState(targetPos);

        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = TILLABLES.get(state.getBlock());

        if (pair != null) {
            BlockHitResult neighborHitResult = new BlockHitResult(
                    new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5),
                    Direction.UP,
                    targetPos,
                    false
            );
            UseOnContext neighborContext = new UseOnContext(player, originalContext.getHand(), neighborHitResult);

            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();

            if (predicate.test(neighborContext)) {
                if (!level.isClientSide()) {
                    consumer.accept(neighborContext);
                    originalContext.getItemInHand().hurtAndBreak(1, player, originalContext.getHand().asEquipmentSlot());
                } else {
                    level.playSound(player, targetPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return true;
            }
        }
        return false;
    }
}