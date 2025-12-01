package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.item.WrenchItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(ArmorStand.class)
public abstract class MixinArmorStand extends LivingEntity {

    private static final EquipmentSlot[] jobsplustools$EQUIPMENT_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET,
            EquipmentSlot.MAINHAND
    };

    @Shadow
    public abstract void setShowArms(boolean bl);

    @Shadow
    public abstract boolean showArms();

    protected MixinArmorStand(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "interactAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/decoration/ArmorStand;getEquipmentSlotForItem(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/EquipmentSlot;",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void jobsplustools$interactAt(Player player, Vec3 vec3, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir, ItemStack itemStack) {
        if (player instanceof ServerPlayer && itemStack.getItem() instanceof WrenchItem) {
            if (this.equipment.isEmpty()) {
                this.setShowArms(!this.showArms());
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                // 1. Identify all damaged items on the Armor Stand
                List<ItemStack> damagedItems = new ArrayList<>();
                for (EquipmentSlot slot : jobsplustools$EQUIPMENT_SLOTS) {
                    ItemStack equippedStack = this.getItemBySlot(slot);
                    if (!equippedStack.isEmpty() && equippedStack.isDamaged()) {
                        damagedItems.add(equippedStack);
                    }
                }

                // If nothing needs repair, return SUCCESS to prevent swapping the Wrench with the item in hand
                if (damagedItems.isEmpty()) {
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }

                // 2. Calculate available repair points based on Wrench durability
                int wrenchDamage = itemStack.getDamageValue();
                int wrenchMaxDamage = itemStack.getMaxDamage();
                int wrenchAvailableDurability = wrenchMaxDamage - wrenchDamage;

                // 1 Wrench Durability = 10 Repair Points
                int maxRepairPoints = wrenchAvailableDurability * 10;

                if (maxRepairPoints <= 0) {
                    // Wrench is broken or has no durability left
                    cir.setReturnValue(InteractionResult.PASS);
                    return;
                }

                // 3. Calculate the total repair needed across all items
                int totalDamageAcrossItems = 0;
                for (ItemStack stack : damagedItems) {
                    totalDamageAcrossItems += stack.getDamageValue();
                }

                // The actual amount we will repair is limited by either the total damage needed
                // or the max repair points the wrench can provide.
                int pointsToSpend = Math.min(totalDamageAcrossItems, maxRepairPoints);
                int totalRepaired = 0;

                // 4. Distribute the points evenly
                // We loop until we have spent all our allocated points or repaired everything
                while (pointsToSpend > 0 && !damagedItems.isEmpty()) {
                    // Determine the 'fair share' for this pass
                    // We use ceil to ensure we don't get stuck with 1 point remainder not being assigned
                    int itemCount = damagedItems.size();
                    int budgetPerItem = (int) Math.ceil((double) pointsToSpend / itemCount);

                    Iterator<ItemStack> iterator = damagedItems.iterator();
                    while (iterator.hasNext()) {
                        ItemStack stack = iterator.next();
                        int currentItemDamage = stack.getDamageValue();

                        // Repair the item by the budget amount, but don't over-repair (exceeding 0 damage)
                        // and don't spend more than the global pointsToSpend
                        int amountToRepair = Math.min(currentItemDamage, budgetPerItem);
                        amountToRepair = Math.min(amountToRepair, pointsToSpend);

                        if (amountToRepair > 0) {
                            stack.setDamageValue(currentItemDamage - amountToRepair);
                            pointsToSpend -= amountToRepair;
                            totalRepaired += amountToRepair;
                        }

                        // If the item is fully repaired, remove it from the list
                        // so the remaining budget concentrates on the remaining items in the next loop
                        if (!stack.isDamaged()) {
                            iterator.remove();
                        }

                        // Break early if we ran out of points mid-loop
                        if (pointsToSpend == 0) break;
                    }
                }

                // 5. Apply the cost to the Wrench and provide feedback
                if (totalRepaired > 0) {
                    // Cost is 1 durability per 10 points repaired (rounded up)
                    int costToWrench = (int) Math.ceil((double) totalRepaired / 10.0);

                    itemStack.hurtAndBreak(costToWrench, player, interactionHand.asEquipmentSlot());

                    // Play an anvil sound to indicate repair success
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.ANVIL_USE,
                            SoundSource.PLAYERS, 1.0F, 1.0F);

                    // Spawn happy villager particles
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(
                                ParticleTypes.HAPPY_VILLAGER,
                                this.getX(),
                                this.getEyeY(),
                                this.getZ(),
                                10,
                                0.5,
                                0.5,
                                0.5,
                                0.0
                        );
                    }

                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
