package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.entity.JobsPlusToolsFishingHook;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(Player.class)
public class MixinPlayer implements JobsPlusToolsPlayer {

    @Unique
    private boolean jobsplustools$isBreakingBlock;

    @Unique
    private final List<JobsPlusToolsFishingHook> jobsplustools$fishingHooks = new ArrayList<>();

    @Override
    public boolean jobsplustools$isBreakingBlock() {
        return this.jobsplustools$isBreakingBlock;
    }

    @Override
    public void jobsplustools$setBreakingBlock(boolean isBreakingBlock) {
        this.jobsplustools$isBreakingBlock = isBreakingBlock;
    }

    @Override
    public List<JobsPlusToolsFishingHook> jobsplustools$getFishingHooks() {
        return this.jobsplustools$fishingHooks;
    }

    @Override
    public void jobsplustools$addFishingHook(JobsPlusToolsFishingHook fishingHooks) {
        this.jobsplustools$fishingHooks.add(fishingHooks);
    }

    @Override
    public void jobsplustools$removeFishingHook(JobsPlusToolsFishingHook fishingHooks) {
        this.jobsplustools$fishingHooks.remove(fishingHooks);
    }

    @Override
    public void jobsplustools$clearFishingHooks() {
        this.jobsplustools$fishingHooks.clear();
    }


}
