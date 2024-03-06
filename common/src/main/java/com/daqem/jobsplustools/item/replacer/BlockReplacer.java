package com.daqem.jobsplustools.item.replacer;

import com.daqem.jobsplustools.item.breaker.BlockBreaker;
import com.daqem.jobsplustools.item.replacer.result.ReplaceableResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockReplacer extends BlockBreaker {

    void replaceBlocks(ServerPlayer player, Level level, BlockPos pos);

    default void replaceBlock(ServerPlayer player, BlockPos pos, Level level, BlockState blockState) {
        ReplaceableResult result = isReplaceable(blockState);
        Block block = blockState.getBlock();

        if (result.shouldBreak()) {
            breakBlock(player, pos, level, blockState);
        }

        if (result.shouldPlace()) {
            level.setBlock(pos, block.defaultBlockState(), 3);
        }
    }

    default ReplaceableResult isReplaceable(BlockState state) {
        return ReplaceableResult.breakAndPlace();
    }
}
