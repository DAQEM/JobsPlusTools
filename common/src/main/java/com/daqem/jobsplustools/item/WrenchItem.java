package com.daqem.jobsplustools.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Tool;

import java.util.List;

public class WrenchItem extends Item {

    public WrenchItem(Properties properties) {
        super(properties.component(DataComponents.TOOL, createToolProperties()));
    }

    public static Tool createToolProperties() {
        return new Tool(
                List.of(),
                1.0F,
                1,
                true
        );
    }
}
