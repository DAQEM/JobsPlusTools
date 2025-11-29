package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class MixinPlayer implements JobsPlusToolsPlayer {

    @Unique
    private boolean jobsplustools$isBreakingBlock;

    @Override
    public boolean jobsplustools$isBreakingBlock() {
        return this.jobsplustools$isBreakingBlock;
    }

    @Override
    public void jobsplustools$setBreakingBlock(boolean isBreakingBlock) {
        this.jobsplustools$isBreakingBlock = isBreakingBlock;
    }
}
