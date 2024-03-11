package com.daqem.jobsplustools.item.breaker;

import com.daqem.jobsplustools.item.mode.ModeItem;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectBlockBreakerModes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public interface ConnectedBlockBreaker extends ModeItem, BlockBreaker {

    @Override
    default void breakBlocks(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        if (getActiveMode(player.getMainHandItem()) == ConnectBlockBreakerModes.OFF) return;
        if (!player.getMainHandItem().isCorrectToolForDrops(state)) return;

        breakConnectedBlocks(player, level, pos, state);
    }

    default void breakConnectedBlocks(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        getBlocksToMine(level, pos, state)
                .forEach(blockPos -> breakBlock(player, blockPos, level, level.getBlockState(blockPos)));
    }

    default Set<BlockPos> getBlocksToMine(Level level, BlockPos pos, BlockState state) {
        Set<BlockPos> connectedBlocks = new HashSet<>();

        if (!isValidBlock(state)) return connectedBlocks;

        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(pos);
        connectedBlocks.add(pos);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) continue;
                        BlockPos adjacentPos = currentPos.offset(x, y, z);
                        BlockState blockState = level.getBlockState(adjacentPos);
                        if (!connectedBlocks.contains(adjacentPos) &&
                                blockState.getBlock() == state.getBlock()
                        ) {
                            connectedBlocks.add(adjacentPos);
                            queue.add(adjacentPos);
                        }
                    }
                }
            }
        }
        return connectedBlocks;
    }

    boolean isValidBlock(BlockState blockState);
}
