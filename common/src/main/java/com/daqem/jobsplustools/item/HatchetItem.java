package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.breaker.ConnectedBlockBreaker;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectBlockBreakerModes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HatchetItem extends AxeItem implements ConnectedBlockBreaker {

    public HatchetItem(Tier tier, float f, float g, Properties properties) {
        super(tier, f, g, properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB.get()));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown() && player instanceof ServerPlayer serverPlayer) switchMode(serverPlayer, player.getItemInHand(hand));
        return super.use(level, player, hand);
    }

    @Override
    public List<IMode> getAvailableModes() {
        return List.of(ConnectBlockBreakerModes.values());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.addAll(getModesTooltip(itemStack));
    }

    @Override
    public boolean isValidBlock(BlockState blockState) {
        return this.isCorrectToolForDrops(blockState) && blockState.getBlock() instanceof RotatedPillarBlock;
    }
}
