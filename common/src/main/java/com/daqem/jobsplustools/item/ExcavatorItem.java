package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.breaker.MultiBlockBreaker;
import com.daqem.jobsplustools.item.mode.IMode;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExcavatorItem extends ShovelItem implements MultiBlockBreaker {

    public ExcavatorItem(Tier tier, Properties properties) {
        //noinspection UnstableApiUsage
        super(tier, properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown() && player instanceof ServerPlayer serverPlayer) switchMode(serverPlayer, player.getItemInHand(hand));
        return super.use(level, player, hand);
    }

    @Override
    public List<IMode> getAvailableModes() {
        return MultiBlockBreaker.generateAvailableModes(getTier());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        list.addAll(getModesTooltip(itemStack));
    }
}