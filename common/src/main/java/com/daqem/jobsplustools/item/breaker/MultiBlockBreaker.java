package com.daqem.jobsplustools.item.breaker;

import com.daqem.jobsplustools.item.Tiers;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.ModeItem;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerMode;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerModes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface MultiBlockBreaker extends ModeItem, BlockBreaker {

    @Override
    default void breakBlocks(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        if (player.isShiftKeyDown()) return;

        getBlocksToMine(player, level).forEach(blockPos ->
                breakBlock(player, blockPos, level, level.getBlockState(blockPos)));
    }

    default List<BlockPos> getBlocksToMine(Player player, Level level) {
        if (player.isShiftKeyDown()) return new ArrayList<>();

        MultiBlockBreakerMode mode = (MultiBlockBreakerMode) getActiveMode(player.getMainHandItem());
        int rangeX = mode.getRangeX() / 2;
        int rangeY = mode.getRangeY() / 2;
        int rangeZ = mode.getRangeZ() / 2;

        BlockHitResult blockHitResult = getBlockHitResult(player, level);
        if (blockHitResult.getType() != BlockHitResult.Type.BLOCK) return null;

        Direction.Axis axis = blockHitResult.getDirection().getAxis();
        int[] adjustedRanges = adjustRanges(axis, rangeX, rangeY, rangeZ);

        return getBlocksInRange(player, level, blockHitResult.getBlockPos(), adjustedRanges[0], adjustedRanges[1], adjustedRanges[2]);
    }

    default List<BlockPos> getBlocksInRange(Player player, Level level, BlockPos pos, int rangeX, int rangeY, int rangeZ) {
        List<BlockPos> blocks = new ArrayList<>();
        for (int x = -rangeX; x <= rangeX; x++) {
            for (int y = -rangeY; y <= rangeY; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    BlockPos offset = pos.offset(x, y, z);
                    BlockState blockState = level.getBlockState(offset);
                    if (player.getMainHandItem().isCorrectToolForDrops(blockState)) {
                        blocks.add(offset);
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

    private BlockHitResult getBlockHitResult(Player player, Level level) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 target = eyePos.add(viewVec.x * 5, viewVec.y * 5, viewVec.z * 5);
        return level.clip(new ClipContext(eyePos, target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    static List<IMode> generateAvailableModes(Tier tier) {
        return switch ((Tiers) tier) {
            case IRON -> Arrays.asList(
                    MultiBlockBreakerModes.THREE_BY_THREE,
                    MultiBlockBreakerModes.THREE_BY_THREE_BY_THREE
            );
            case GOLD -> Arrays.asList(
                    MultiBlockBreakerModes.THREE_BY_THREE,
                    MultiBlockBreakerModes.THREE_BY_THREE_BY_THREE,
                    MultiBlockBreakerModes.FIVE_BY_FIVE
            );
            case DIAMOND, NETHERITE -> Arrays.asList(
                    MultiBlockBreakerModes.THREE_BY_THREE,
                    MultiBlockBreakerModes.THREE_BY_THREE_BY_THREE,
                    MultiBlockBreakerModes.FIVE_BY_FIVE,
                    MultiBlockBreakerModes.FIVE_BY_FIVE_BY_FIVE
            );
            default -> List.of(MultiBlockBreakerModes.THREE_BY_THREE);
        };
    }
}
