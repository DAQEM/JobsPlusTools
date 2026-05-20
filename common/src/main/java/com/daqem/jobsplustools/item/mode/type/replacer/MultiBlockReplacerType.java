package com.daqem.jobsplustools.item.mode.type.replacer;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.replacer.MultiBlockReplacerMode;
import com.daqem.jobsplustools.item.mode.replacer.MultiBlockReplacerModes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiBlockReplacerType extends BlockReplacerType {

    @Override
    public void replaceBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos) {
        if (player.isShiftKeyDown()) return;

        getBlocksToMine(selectedMode, player, level, pos).forEach(blockPos ->
                replaceBlock(player, blockPos, level, level.getBlockState(blockPos)));
    }

    public Set<BlockPos> getBlocksInRange(BlockPos pos, int rangeX, int rangeY, int rangeZ) {
        Set<BlockPos> blocks = new HashSet<>();
        for (int x = -rangeX; x <= rangeX; x++) {
            for (int y = -rangeY; y <= rangeY; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    blocks.add(pos.offset(x, y, z));
                }
            }
        }
        return blocks;
    }

    @Override
    public void breakBlocks(IMode selectedMode, ServerPlayer player, Level level, BlockPos pos, BlockState state) {

    }

    @Override
    public Set<BlockPos> getBlocksToMine(IMode selectedMode, Player player, Level level, BlockPos pos) {
        if (selectedMode instanceof MultiBlockReplacerMode mode && !player.isCrouching()) {
            int rangeX = mode.getRangeX() / 2;
            int rangeY = mode.getRangeY() / 2;
            int rangeZ = mode.getRangeZ() / 2;

            return getBlocksInRange(pos, rangeX, rangeY, rangeZ);
        }
        return Collections.emptySet();
    }

    @Override
    public Identifier getId() {
        return JobsPlusTools.API.getId("multi_block_replacer");
    }

    @Override
    public Class<? extends IMode> getModeClass() {
        return MultiBlockReplacerModes.class;
    }
}
