package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.item.CompoundBowItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class MixinAbstractArrow {

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;)V")
    private void init(EntityType<? extends AbstractArrow> entityType, LivingEntity livingEntity, Level level, CallbackInfo ci) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CompoundBowItem compoundBowItem = null;
            if (serverPlayer.getMainHandItem().getItem() instanceof CompoundBowItem) {
                compoundBowItem = (CompoundBowItem) serverPlayer.getMainHandItem().getItem();
            } else if (serverPlayer.getOffhandItem().getItem() instanceof CompoundBowItem) {
                compoundBowItem = (CompoundBowItem) serverPlayer.getOffhandItem().getItem();
            }

            if (compoundBowItem != null) {
                AbstractArrow abstractArrow = (AbstractArrow) (Object) this;
                abstractArrow.setBaseDamage(abstractArrow.getBaseDamage() + compoundBowItem.getBonusDamage());
            }
        }
    }
}
