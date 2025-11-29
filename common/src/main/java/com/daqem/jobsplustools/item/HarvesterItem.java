package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.item.breaker.BlockBreaker;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.replacer.BlockReplacer;
import com.daqem.jobsplustools.item.replacer.MultiBlockReplacer;
import com.daqem.jobsplustools.item.replacer.result.ReplaceableResult;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class HarvesterItem extends HoeItem implements MultiBlockReplacer {

    private final ToolMaterial toolMaterial;

    public HarvesterItem(ToolMaterial toolMaterial, float f, float g, Properties properties) {
        super(toolMaterial, f, g, properties);
        this.toolMaterial = toolMaterial;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (player.isShiftKeyDown()) {
                switchMode(serverPlayer, player.getItemInHand(hand));
            } else {
                if (hand == InteractionHand.MAIN_HAND) {
                    ItemStack itemStack = player.getMainHandItem();
                    if (itemStack.getItem() instanceof BlockReplacer blockReplacer) {
                        BlockHitResult blockHitResult = BlockBreaker.getBlockHitResult(player, level);
                        blockReplacer.replaceBlocks(serverPlayer, player.level(), blockHitResult.getBlockPos());
                    }
                }
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public List<IMode> getAvailableModes() {
        return MultiBlockReplacer.generateAvailableModes(toolMaterial);
    }

    @Override
    public ReplaceableResult isReplaceable(BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
            return ReplaceableResult.breakAndPlace();
        }
        else if (state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) == NetherWartBlock.MAX_AGE) {
            return ReplaceableResult.breakAndPlace();
        }
        else {
            return ReplaceableResult.none();
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        getModesTooltip(itemStack).forEach(consumer);
    }
}
