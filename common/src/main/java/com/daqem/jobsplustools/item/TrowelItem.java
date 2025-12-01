package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.daqem.jobsplustools.item.mode.type.placer.MultiBlockPlacerType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class TrowelItem extends Item {
    public TrowelItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context.getItemInHand().has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
            ModeItemComponent modeItemComponent = context.getItemInHand().get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
            if (modeItemComponent != null) {
                IModeType modeType = modeItemComponent.getModeType();
                if (modeType instanceof MultiBlockPlacerType placerType) {
                    IMode selectedMode = placerType.getSelectedMode(modeItemComponent);
                    return placerType.onPlace(selectedMode, context);
                }
            }
        }
        return super.useOn(context);
    }
}