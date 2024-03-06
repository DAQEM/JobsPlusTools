package com.daqem.jobsplustools.event;

import com.daqem.jobsplustools.item.breaker.BlockBreaker;
import com.daqem.jobsplustools.item.replacer.BlockReplacer;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BreakBlockEvent {

    public static void registerEvent() {
        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {

            ItemStack itemStack = player.getMainHandItem();
            Item item = itemStack.getItem();

            if (item instanceof BlockReplacer) return EventResult.pass();
            if (item instanceof BlockBreaker blockBreaker) {

                // Check if block is broken by the breaker and if so, pass the event
                if (player.getEntityData().hasItem(BlockBreaker.BREAKER) && player.getEntityData().get(BlockBreaker.BREAKER)) {
                    return EventResult.pass();
                }

                // Otherwise, break the blocks
                blockBreaker.breakBlocks(player, level, pos, state);
            }
            return EventResult.pass();
        });
    }
}
