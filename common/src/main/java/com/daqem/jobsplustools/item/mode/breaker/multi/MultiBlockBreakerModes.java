package com.daqem.jobsplustools.item.mode.breaker.multi;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.Component;

public enum MultiBlockBreakerModes implements MultiBlockBreakerMode {

    ONE_BY_ONE("1x1", 1, 1, 1),
    THREE_BY_THREE("3x3", 3, 3, 1),
    THREE_BY_THREE_BY_THREE("3x3x3", 3, 3, 3),
    FIVE_BY_FIVE("5x5", 5, 5, 1),
    FIVE_BY_FIVE_BY_FIVE("5x5x5", 5, 5, 5);

    private final String name;
    private final int rangeX;
    private final int rangeY;
    private final int rangeZ;

    MultiBlockBreakerModes(String name, int rangeX, int rangeY, int rangeZ) {
        this.name = name;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
    }

    @Override
    public Component getName() {
        return JobsPlusTools.translatable("item.mode.breaker.multi." + name);
    }

    @Override
    public int getRangeX() {
        return rangeX;
    }

    @Override
    public int getRangeY() {
        return rangeY;
    }

    @Override
    public int getRangeZ() {
        return rangeZ;
    }
}
