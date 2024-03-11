package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.breaker.ConnectedBlockBreaker;
import com.daqem.jobsplustools.item.breaker.MultiBlockBreaker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
        if (itemStack.getItem() instanceof ConnectedBlockBreaker connectedBlockBreaker) {
            float returnValue = cir.getReturnValue();
            BlockHitResult blockHitResult = connectedBlockBreaker.getBlockHitResult(player, player.level());
            int blocksToMine = connectedBlockBreaker.getBlocksToMine(player.level(), blockHitResult.getBlockPos(), blockState).size();
            float speedMultiplier = connectedBlockBreaker.getActiveMode(itemStack).getSpeedMultiplier(blocksToMine);
            returnValue *= speedMultiplier;
            cir.setReturnValue(returnValue);
        }
    }
}
