package com.daqem.jobsplustools.item.mode.type;

import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public abstract class BlockBreakerType implements IModeType {

    public abstract void breakBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos, BlockState state);
    public abstract Set<BlockPos> getBlocksToMine(IMode selectedMode, Player player, Level level, BlockPos pos);

    public void breakBlock(ServerPlayer player, BlockPos pos, Level level) {
        if (player instanceof JobsPlusToolsPlayer extension) {
            BlockState state = level.getBlockState(pos);
            extension.jobsplustools$setBreakingBlock(true);
            player.gameMode.destroyBlock(pos);
            level.levelEvent(2001, pos, Block.getId(state));
            extension.jobsplustools$setBreakingBlock(false);
        }
    }

    public static BlockHitResult getBlockHitResult(Player player, Level level) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 target = eyePos.add(viewVec.x * 5, viewVec.y * 5, viewVec.z * 5);
        return level.clip(new ClipContext(eyePos, target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}
