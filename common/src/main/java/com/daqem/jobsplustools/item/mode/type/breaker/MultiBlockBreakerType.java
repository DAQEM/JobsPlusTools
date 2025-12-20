package com.daqem.jobsplustools.item.mode.type.breaker;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerMode;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerModes;
import com.daqem.jobsplustools.item.mode.type.BlockBreakerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiBlockBreakerType extends BlockBreakerType {

    @Override
    public void breakBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        if (player.isShiftKeyDown()) return;

        getBlocksToMine(selectedMode, player, level, pos).forEach(blockPos -> {
            if (!blockPos.equals(pos)) {
                breakBlock(player, blockPos, level);
            }
        });
    }

    public Set<BlockPos> getBlocksToMine(IMode selectedMode, Player player, Level level, BlockPos pos) {
        if (selectedMode instanceof MultiBlockBreakerMode mode) {

            if (player.isShiftKeyDown()) {
                return Collections.emptySet();
            }

            // Calculate half-ranges
            int rX = mode.getRangeX() / 2;
            int rY = mode.getRangeY() / 2;
            int rZ = mode.getRangeZ() / 2;

            BlockHitResult blockHitResult = BlockBreakerType.getBlockHitResult(player, level);
            if (blockHitResult.getType() != BlockHitResult.Type.BLOCK) {
                return Collections.emptySet();
            }

            Direction.Axis axis = blockHitResult.getDirection().getAxis();
            int[] adj = adjustRanges(axis, rX, rY, rZ);

            return getBlocksInRange(player, level, blockHitResult.getBlockPos(), adj[0], adj[1], adj[2]);
        }
        return Collections.emptySet();
    }

    public Set<BlockPos> getBlocksInRange(Player player, Level level, BlockPos origin, int rangeX, int rangeY, int rangeZ) {
        // Calculate exact capacity to prevent HashSet resizing overhead
        // (range * 2 + 1) gives the full width of the axis
        int capacity = (rangeX * 2 + 1) * (rangeY * 2 + 1) * (rangeZ * 2 + 1);
        Set<BlockPos> blocks = new HashSet<>(capacity);

        ItemStack tool = player.getMainHandItem();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -rangeX; x <= rangeX; x++) {
            for (int y = -rangeY; y <= rangeY; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    // Updates the mutable pos values without creating a new object
                    mutablePos.setWithOffset(origin, x, y, z);

                    BlockState blockState = level.getBlockState(mutablePos);

                    // Only create the immutable BlockPos object if we are actually keeping it
                    if (tool.isCorrectToolForDrops(blockState)) {
                        blocks.add(mutablePos.immutable());
                    }
                }
            }
        }
        return blocks;
    }

    private int[] adjustRanges(Direction.Axis axis, int rangeX, int rangeY, int rangeZ) {
        if (axis == Direction.Axis.Y) {
            return new int[]{rangeX, rangeZ, rangeY};
        } else if (axis == Direction.Axis.X) {
            return new int[]{rangeZ, rangeY, rangeX};
        } else {
            return new int[]{rangeX, rangeY, rangeZ};
        }
    }

    @Override
    public Identifier getId() {
        return JobsPlusTools.getId("multi_block_breaker");
    }

    @Override
    public Class<? extends IMode> getModeClass() {
        return MultiBlockBreakerModes.class;
    }
}
