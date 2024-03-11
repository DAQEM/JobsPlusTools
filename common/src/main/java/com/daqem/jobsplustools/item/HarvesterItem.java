package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.replacer.BlockReplacer;
import com.daqem.jobsplustools.item.replacer.MultiBlockReplacer;
import com.daqem.jobsplustools.item.replacer.result.ReplaceableResult;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarvesterItem extends HoeItem implements MultiBlockReplacer {

    public HarvesterItem(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB.get()));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (player.isShiftKeyDown()) {
                switchMode(serverPlayer, player.getItemInHand(hand));
            } else {
                if (hand == InteractionHand.MAIN_HAND) {
                    ItemStack itemStack = player.getMainHandItem();
                    if (itemStack.getItem() instanceof BlockReplacer blockReplacer) {
                        BlockHitResult blockHitResult = getBlockHitResult(player, level);
                        blockReplacer.replaceBlocks(serverPlayer, player.level(), blockHitResult.getBlockPos());
                    }
                }
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public List<IMode> getAvailableModes() {
        return MultiBlockReplacer.generateAvailableModes(getTier());
    }

    @Override
    public ReplaceableResult isReplaceable(BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
            return ReplaceableResult.breakAndPlace();
        }
        else if (state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) == NetherWartBlock.MAX_AGE) {
            return ReplaceableResult.breakAndPlace();
        }
        else if (state.getBlock() instanceof StemGrownBlock) {
            return ReplaceableResult.onlyBreak();
        }
        else {
            return ReplaceableResult.none();
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.addAll(getModesTooltip(itemStack));
    }
}
