package com.daqem.jobsplustools.item.breaker;

import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.ModeItem;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerMode;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerModes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;

public interface MultiBlockBreaker extends ModeItem, BlockBreaker {

    @Override
    default void breakBlocks(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        if (player.isShiftKeyDown()) return;

        getBlocksToMine(player, level).forEach(blockPos -> {
            if (!blockPos.equals(pos)) {
                breakBlock(player, blockPos, level);
            }
        });
    }

    default Set<BlockPos> getBlocksToMine(Player player, Level level) {
        if (player.isShiftKeyDown()) {
            return Collections.emptySet();
        }

        ItemStack mainHandItem = player.getMainHandItem();
        MultiBlockBreakerMode mode = (MultiBlockBreakerMode) getActiveMode(mainHandItem);

        // Calculate half-ranges
        int rX = mode.getRangeX() / 2;
        int rY = mode.getRangeY() / 2;
        int rZ = mode.getRangeZ() / 2;

        BlockHitResult blockHitResult = BlockBreaker.getBlockHitResult(player, level);
        if (blockHitResult.getType() != BlockHitResult.Type.BLOCK) {
            return Collections.emptySet();
        }

        Direction.Axis axis = blockHitResult.getDirection().getAxis();
        int[] adj = adjustRanges(axis, rX, rY, rZ);

        return getBlocksInRange(player, level, blockHitResult.getBlockPos(), adj[0], adj[1], adj[2]);
    }

    default Set<BlockPos> getBlocksInRange(Player player, Level level, BlockPos origin, int rangeX, int rangeY, int rangeZ) {
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

    static List<IMode> generateAvailableModes(ToolMaterial toolMaterial) {
        if (toolMaterial == ToolMaterial.NETHERITE) {
            return Arrays.asList(
                    MultiBlockBreakerModes.ONE_BY_ONE,
                    MultiBlockBreakerModes.THREE_BY_THREE,
                    MultiBlockBreakerModes.THREE_BY_THREE_BY_THREE,
                    MultiBlockBreakerModes.FIVE_BY_FIVE,
                    MultiBlockBreakerModes.FIVE_BY_FIVE_BY_FIVE
            );
        } else if (toolMaterial == ToolMaterial.DIAMOND || toolMaterial == ToolMaterial.GOLD) {
            return Arrays.asList(
                    MultiBlockBreakerModes.ONE_BY_ONE,
                    MultiBlockBreakerModes.THREE_BY_THREE,
                    MultiBlockBreakerModes.THREE_BY_THREE_BY_THREE,
                    MultiBlockBreakerModes.FIVE_BY_FIVE
            );
        } else if (toolMaterial == ToolMaterial.IRON) {
            return Arrays.asList(
                    MultiBlockBreakerModes.ONE_BY_ONE,
                    MultiBlockBreakerModes.THREE_BY_THREE,
                    MultiBlockBreakerModes.THREE_BY_THREE_BY_THREE
            );
        } else {
            return Arrays.asList(MultiBlockBreakerModes.ONE_BY_ONE, MultiBlockBreakerModes.THREE_BY_THREE);
        }
    }
}
