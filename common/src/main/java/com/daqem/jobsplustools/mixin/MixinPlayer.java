package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.item.breaker.MultiBlockBreaker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {

    @Inject(at = @At("RETURN"), method = "getDestroySpeed", cancellable = true)
    public void getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof MultiBlockBreaker multiBlockBreaker) {
            float returnValue = cir.getReturnValue();
            int blocksToMine = multiBlockBreaker.getBlocksToMine(player, player.level()).size();
            float speedMultiplier = multiBlockBreaker.getActiveMode(itemStack).getSpeedMultiplier(blocksToMine);
            returnValue *= speedMultiplier;
            cir.setReturnValue(returnValue);
        }
    }
}
