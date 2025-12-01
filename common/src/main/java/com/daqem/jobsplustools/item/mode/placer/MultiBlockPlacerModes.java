package com.daqem.jobsplustools.item.mode.placer;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.MutableComponent;

public enum MultiBlockPlacerModes implements MultiBlockPlacerMode {
    ONE_BY_ONE("1x1", 0),
    THREE_BY_THREE("3x3", 1),
    FIVE_BY_FIVE("5x5", 2);

    private final String name;
    private final int radius;

    MultiBlockPlacerModes(String name, int radius) {
        this.name = name;
        this.radius = radius;
    }

    @Override
    public MutableComponent getName() {
        return JobsPlusTools.translatable("item.mode.placer.multi." + name);
    }

    @Override
    public int getRadius() {
        return radius;
    }
}