package com.daqem.jobsplustools.item.replacer;

import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.ModeItem;
import com.daqem.jobsplustools.item.mode.replacer.MultiBlockReplacerMode;
import com.daqem.jobsplustools.item.mode.replacer.MultiBlockReplacerModes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;

import java.util.*;

public interface MultiBlockReplacer extends ModeItem, BlockReplacer {

    @Override
    default void replaceBlocks(ServerPlayer player, Level level, BlockPos pos) {
        if (player.isShiftKeyDown()) return;

        getBlocksToReplace(player, pos).forEach(blockPos ->
                replaceBlock(player, blockPos, level, level.getBlockState(blockPos)));
    }

    default Set<BlockPos> getBlocksToReplace(Player player, BlockPos pos) {
        if (player.isShiftKeyDown()) return Collections.emptySet();

        MultiBlockReplacerMode mode = (MultiBlockReplacerMode) getActiveMode(player.getMainHandItem());
        int rangeX = mode.getRangeX() / 2;
        int rangeY = mode.getRangeY() / 2;
        int rangeZ = mode.getRangeZ() / 2;

        return getBlocksInRange(pos, rangeX, rangeY, rangeZ);
    }

    default Set<BlockPos> getBlocksInRange(BlockPos pos, int rangeX, int rangeY, int rangeZ) {
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

    static List<IMode> generateAvailableModes(ToolMaterial toolMaterial) {
        if (toolMaterial == ToolMaterial.IRON || toolMaterial == ToolMaterial.GOLD) {
            return Arrays.asList(
                    MultiBlockReplacerModes.ONE_BY_ONE,
                    MultiBlockReplacerModes.THREE_BY_THREE
            );
        } else if (toolMaterial == ToolMaterial.DIAMOND || toolMaterial == ToolMaterial.NETHERITE) {
            return Arrays.asList(
                    MultiBlockReplacerModes.ONE_BY_ONE,
                    MultiBlockReplacerModes.THREE_BY_THREE,
                    MultiBlockReplacerModes.FIVE_BY_FIVE
            );
        } else {
            return List.of(MultiBlockReplacerModes.ONE_BY_ONE);
        }
    }
}
