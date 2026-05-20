package com.daqem.jobsplustools.event;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.BlockBreakerType;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.block.BlockEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BreakBlockEvent {

    public static void registerEvent() {
        BlockEvent.BREAK_BLOCK.register((level, pos, state, player) -> {

            ItemStack itemStack = player.getMainHandItem();
            Item item = itemStack.getItem();

            if (player instanceof JobsPlusToolsPlayer jobsPlusToolsPlayer
                    && !jobsPlusToolsPlayer.jobsplustools$isBreakingBlock()
                    && item.isCorrectToolForDrops(itemStack, state)
                    && itemStack.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {

                ModeItemComponent modeItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());

                if (modeItemComponent != null && modeItemComponent.getModeType() instanceof BlockBreakerType blockBreakerType) {
                    IMode selectedMode = blockBreakerType.getSelectedMode(modeItemComponent);
                    blockBreakerType.onBlockBreak(selectedMode, player, level, pos, state);
                }
            }
            return EventResult.PASS;
        });
    }
}
