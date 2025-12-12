package com.daqem.jobsplustools.player;

import com.daqem.jobsplustools.entity.JobsPlusToolsFishingHook;
import net.minecraft.world.entity.projectile.FishingHook;

import java.util.List;

public interface JobsPlusToolsPlayer {
    boolean jobsplustools$isBreakingBlock();
    void jobsplustools$setBreakingBlock(boolean isBreakingBlock);

    List<JobsPlusToolsFishingHook> jobsplustools$getFishingHooks();
    void jobsplustools$addFishingHook(JobsPlusToolsFishingHook fishingHooks);
    void jobsplustools$removeFishingHook(JobsPlusToolsFishingHook fishingHooks);
    void jobsplustools$clearFishingHooks();
}
