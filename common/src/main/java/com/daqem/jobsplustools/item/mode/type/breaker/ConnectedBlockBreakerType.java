package com.daqem.jobsplustools.item.mode.type.breaker;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectedBlockBreakerModes;
import com.daqem.jobsplustools.item.mode.type.BlockBreakerType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;

public class ConnectedBlockBreakerType extends BlockBreakerType {

    public static final int MAX_CONNECTED_BLOCKS = 32768;

    @Override
    public void breakBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        if (selectedMode instanceof ConnectedBlockBreakerModes modes) {
            if (modes == ConnectedBlockBreakerModes.OFF) return;
            if (!player.getMainHandItem().isCorrectToolForDrops(state)) return;

            breakConnectedBlocks(selectedMode, player, level, pos);
        }
    }

    public void breakConnectedBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos) {
        getBlocksToMine(selectedMode, player, level, pos).forEach(blockPos -> breakBlock(player, blockPos, level));
    }

    public Set<BlockPos> getBlocksToMine(IMode selectedMode, Player player, Level level, BlockPos pos) {
        BlockHitResult blockHitResult = BlockBreakerType.getBlockHitResult(player, level);

        if (blockHitResult.getType() != BlockHitResult.Type.BLOCK) {
            return Collections.emptySet();
        }

        BlockPos startPos = blockHitResult.getBlockPos();
        BlockState blockState = level.getBlockState(startPos);
        Block targetBlock = blockState.getBlock();

        if (!isValidBlock(player.getMainHandItem(), blockState)) {
            return Collections.emptySet();
        }

        Set<BlockPos> connectedBlocks = new HashSet<>(8);
        Queue<BlockPos> queue = new ArrayDeque<>(8);

        connectedBlocks.add(startPos);
        queue.add(startPos);

        // Reuse this single object for all 26 neighbor checks to save memory
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();

            // 3x3x3 Neighbor Loop
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) continue;

                        // Update the mutable position instead of creating a new object
                        mutablePos.setWithOffset(currentPos, x, y, z);

                        // Check if already visited (Fastest check first)
                        // BlockPos.MutableBlockPos works with HashSet.contains() correctly
                        if (connectedBlocks.contains(mutablePos)) {
                            continue;
                        }

                        // Check Block State (World access is slower, do this second)
                        if (level.getBlockState(mutablePos).is(targetBlock)) {

                            // Only create a new immutable BlockPos if we are keeping it
                            BlockPos immutablePos = mutablePos.immutable();

                            connectedBlocks.add(immutablePos);
                            queue.add(immutablePos);

                            // Optimization: Check limit immediately to avoid processing the rest of this cube
                            if (connectedBlocks.size() >= MAX_CONNECTED_BLOCKS) {
                                return connectedBlocks;
                            }
                        }
                    }
                }
            }
        }
        return connectedBlocks;
    }

    public boolean isValidBlock(ItemStack stack, BlockState blockState) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return JobsPlusTools.getId("connected_block_breaker");
    }

    @Override
    public Class<? extends IMode> getModeClass() {
        return ConnectedBlockBreakerModes.class;
    }
}
