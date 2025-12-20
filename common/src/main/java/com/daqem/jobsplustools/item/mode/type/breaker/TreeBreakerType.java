package com.daqem.jobsplustools.item.mode.type.breaker;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TreeBreakerType extends ConnectedBlockBreakerType {

    @Override
    public boolean isValidBlock(ItemStack stack, BlockState blockState) {
        return blockState.is(BlockTags.LOGS);
    }

    @Override
    public Identifier getId() {
        return JobsPlusTools.getId("tree_breaker");
    }
}
