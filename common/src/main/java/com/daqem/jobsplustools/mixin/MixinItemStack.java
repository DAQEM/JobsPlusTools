package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.item.component.JobsPlusDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> arg, Item.TooltipContext arg2, TooltipDisplay arg3, Consumer<Component> consumer, TooltipFlag arg4);

    @Inject(
            method = "addDetailsToTooltip",
            at = @At(value = "HEAD")
    )
    private void jobsplustools$addDetailsToTooltip(Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Player player, TooltipFlag tooltipFlag, Consumer<Component> consumer, CallbackInfo ci) {
        this.addToTooltip(JobsPlusDataComponentTypes.MODE_ITEM_COMPONENT.get(), tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }
}
