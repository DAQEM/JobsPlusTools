package com.daqem.jobsplustools.item.mode.type.replacer;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.type.replacer.result.ReplaceableResult;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CropReplacerType extends MultiBlockReplacerType {

    @Override
    public ReplaceableResult isReplaceable(BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
            return ReplaceableResult.breakAndPlace();
        } else if (state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) == NetherWartBlock.MAX_AGE) {
            return ReplaceableResult.breakAndPlace();
        } else {
            return ReplaceableResult.none();
        }
    }

    @Override
    public Identifier getId() {
        return JobsPlusTools.getId("crop_replacer");
    }
}
