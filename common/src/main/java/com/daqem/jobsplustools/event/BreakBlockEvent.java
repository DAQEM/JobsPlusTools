package com.daqem.jobsplustools.event;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.daqem.jobsplustools.item.mode.type.breaker.ConnectedBlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.breaker.MultiBlockBreakerType;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BreakBlockEvent {

    public static void registerEvent() {
        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {

            ItemStack itemStack = player.getMainHandItem();
            Item item = itemStack.getItem();
            if (player instanceof JobsPlusToolsPlayer jobsPlusToolsPlayer
                    && !jobsPlusToolsPlayer.jobsplustools$isBreakingBlock()
                    && item.isCorrectToolForDrops(itemStack, state)
                    && itemStack.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {

                ModeItemComponent modeItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
                if (modeItemComponent != null) {
                    IModeType modeType = modeItemComponent.getModeType();
                    IMode[] allModes = modeType.getModeClass().getEnumConstants();
                    IMode selectedMode = allModes[modeItemComponent.selectedMode()];
                    if (modeType instanceof ConnectedBlockBreakerType connectedBlockBreakerType) {
                        connectedBlockBreakerType.breakBlocks(selectedMode, player, level, pos, state);
                    } else if (modeType instanceof MultiBlockBreakerType multiBlockBreakerType) {
                        multiBlockBreakerType.breakBlocks(selectedMode, player, level, pos, state);
                    }
                }
            }
            return EventResult.pass();
        });
    }
}
