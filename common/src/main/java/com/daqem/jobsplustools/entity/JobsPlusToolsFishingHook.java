package com.daqem.jobsplustools.entity;

import com.daqem.jobsplustools.item.FishingRodItem;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class JobsPlusToolsFishingHook extends FishingHook {

    private final boolean canFishInLava;

    public JobsPlusToolsFishingHook(Player player, Level level, int luck, int lure, boolean canFishInLava, int hookIndex, int totalHooks) {
        super(player, level, luck, lure);

        float playerPitch = player.getXRot();
        float playerYaw = player.getYRot();

        if (totalHooks > 1) {
            float spreadWidth = (totalHooks - 1) * 15.0F;

            float startAngle = -spreadWidth / 2.0F;

            float yawOffset = startAngle + (hookIndex * 15.0F);

            playerYaw += yawOffset;
        }

        float yawCos = Mth.cos(-playerYaw * (float) (Math.PI / 180.0) - (float) Math.PI);
        float yawSin = Mth.sin(-playerYaw * (float) (Math.PI / 180.0) - (float) Math.PI);

        float pitchCos = -Mth.cos(-playerPitch * (float) (Math.PI / 180.0));
        float pitchSin = Mth.sin(-playerPitch * (float) (Math.PI / 180.0));

        double spawnX = player.getX() - yawSin * 0.3;
        double spawnY = player.getEyeY();
        double spawnZ = player.getZ() - yawCos * 0.3;

        this.snapTo(spawnX, spawnY, spawnZ, playerYaw, playerPitch);

        Vec3 velocity = new Vec3(-yawSin, Mth.clamp(-(pitchSin / pitchCos), -5.0F, 5.0F), -yawCos);
        double vectorLength = velocity.length();

        velocity = velocity.multiply(
                0.6 / vectorLength + this.random.triangle(0.5, 0.0103365),
                0.6 / vectorLength + this.random.triangle(0.5, 0.0103365),
                0.6 / vectorLength + this.random.triangle(0.5, 0.0103365)
        );

        this.setDeltaMovement(velocity);

        this.setYRot((float)(Mth.atan2(velocity.x, velocity.z) * 180.0F / (float)Math.PI));
        this.setXRot((float)(Mth.atan2(velocity.y, velocity.horizontalDistance()) * 180.0F / (float)Math.PI));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();

        this.canFishInLava = canFishInLava;
    }

    public JobsPlusToolsFishingHook(EntityType<JobsPlusToolsFishingHook> entityType, Level level) {
        super(entityType, level);
        canFishInLava = false;
    }

    public void jobsplustools$updateOwnerInfo(@Nullable FishingHook fishingHook) {
        JobsPlusToolsPlayer player = this.getJobsPlusToolsPlayerOwner();
        if (player != null) {
            if (fishingHook != null) {
                player.jobsplustools$addFishingHook(this);
            }
        }
    }

    @Override
    public void onClientRemoval() {
        JobsPlusToolsPlayer player = this.getJobsPlusToolsPlayerOwner();
        if (player != null) {
            player.jobsplustools$removeFishingHook(this);
        }
    }

    @Override
    public void onRemoval(@NonNull RemovalReason removalReason) {
        JobsPlusToolsPlayer player = this.getJobsPlusToolsPlayerOwner();
        if (player != null) {
            player.jobsplustools$removeFishingHook(this);
        }
    }

    public boolean canFishInLava() {
        return this.canFishInLava;
    }

    @Nullable
    public JobsPlusToolsPlayer getJobsPlusToolsPlayerOwner() {
        return this.getOwner() instanceof JobsPlusToolsPlayer player ? player : null;
    }


    public boolean jobsplustools$shouldStopFishing(Player player) {
        if (player.canInteractWithLevel()) {
            ItemStack itemStack = player.getMainHandItem();
            ItemStack itemStack2 = player.getOffhandItem();
            boolean bl = itemStack.getItem() instanceof FishingRodItem;
            boolean bl2 = itemStack2.getItem() instanceof FishingRodItem;
            if ((bl || bl2) && this.distanceToSqr(player) <= (double) 1024.0F) {
                return false;
            }
        }

        this.discard();
        return true;
    }
}
