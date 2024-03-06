package com.daqem.jobsplustools.event;

import com.daqem.jobsplustools.item.replacer.BlockReplacer;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class RightClickBlockEvent {

    public static void registerEvent() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                if (hand == InteractionHand.MAIN_HAND) {
                    ItemStack itemStack = player.getMainHandItem();
                    if (itemStack.getItem() instanceof BlockReplacer blockReplacer) {
                        blockReplacer.replaceBlocks(serverPlayer, player.level(), pos);
                    }
                }
            }
            return EventResult.pass();
        });
    }
}
