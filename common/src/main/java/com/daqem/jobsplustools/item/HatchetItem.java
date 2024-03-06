package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.breaker.ConnectedBlockBreaker;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectBlockBreakerModes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
}
