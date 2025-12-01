package com.daqem.jobsplustools.item.mode.type.replacer;

import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.BlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.replacer.result.ReplaceableResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockReplacerType extends BlockBreakerType {

    public abstract void replaceBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos);

    public void replaceBlock(ServerPlayer player, BlockPos pos, Level level, BlockState blockState) {
        ReplaceableResult result = isReplaceable(blockState);
        Block block = blockState.getBlock();

        if (result.shouldBreak()) {
            breakBlock(player, pos, level);
        }

        if (result.shouldPlace()) {
            level.setBlock(pos, block.defaultBlockState(), 3);
        }
    }

    public ReplaceableResult isReplaceable(BlockState state) {
        return ReplaceableResult.breakAndPlace();
    }
}
