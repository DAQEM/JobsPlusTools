package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.entity.JobsPlusToolsFishingHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class MixinFishingHook {

    @Inject(method = "updateOwnerInfo", at = @At("HEAD"), cancellable = true)
    private void jobsplustools$updateOwnerInfo(FishingHook hook, CallbackInfo ci) {
        FishingHook self = (FishingHook) (Object) this;
        if (self instanceof JobsPlusToolsFishingHook jobsPlusToolsFishingHook) {
            jobsPlusToolsFishingHook.jobsplustools$updateOwnerInfo(hook);
            ci.cancel();
        }
    }

    @Inject(method = "shouldStopFishing", at = @At("HEAD"), cancellable = true)
    private void jobsplustools$shouldStopFishing(Player owner, CallbackInfoReturnable<Boolean> cir) {
        FishingHook self = (FishingHook) (Object) this;
        if (self instanceof JobsPlusToolsFishingHook jobsPlusToolsFishingHook) {
            cir.setReturnValue(jobsPlusToolsFishingHook.jobsplustools$shouldStopFishing(owner));
        }
    }
}