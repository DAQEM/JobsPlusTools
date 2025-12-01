package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.breaker.MultiBlockBreaker;
import com.daqem.jobsplustools.item.mode.IMode;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class HammerItem extends Item implements MultiBlockBreaker {

    private final ToolMaterial toolMaterial;

    public HammerItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.pickaxe(toolMaterial, attackDamage, attackSpeed));
        this.toolMaterial = toolMaterial;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown() && player instanceof ServerPlayer serverPlayer) switchMode(serverPlayer, player.getItemInHand(hand));
        return super.use(level, player, hand);
    }

    @Override
    public List<IMode> getAvailableModes() {
        return MultiBlockBreaker.generateAvailableModes(this.toolMaterial);
    }
}
