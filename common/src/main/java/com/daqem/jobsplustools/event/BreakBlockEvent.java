package com.daqem.jobsplustools.event;

import com.daqem.jobsplustools.item.breaker.BlockBreaker;
import com.daqem.jobsplustools.item.replacer.BlockReplacer;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BreakBlockEvent {

    public static void registerEvent() {
        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {

            ItemStack itemStack = player.getMainHandItem();
            Item item = itemStack.getItem();

            if (!(item instanceof BlockReplacer) && item instanceof BlockBreaker blockBreaker) {

                // Check if block is not broken by the breaker and if so, break the block
                if (player instanceof JobsPlusToolsPlayer extension) {
                    if (!extension.jobsplustools$isBreakingBlock() && item.isCorrectToolForDrops(itemStack, state)) {
                        blockBreaker.breakBlocks(player, level, pos, state);
                    }
                }
            }

            return EventResult.pass();
        });
    }
}
