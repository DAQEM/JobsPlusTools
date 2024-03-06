package com.daqem.jobsplustools.item.mode.replacer;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.Component;

public enum MultiBlockReplacerModes implements MultiBlockReplacerMode {

    ONE_BY_ONE("1x1", 1, 1, 1),
    THREE_BY_THREE("3x3", 3, 1, 3),
    FIVE_BY_FIVE("5x5", 5, 1, 5),
    ;

    private final String name;
    private final int rangeX;
    private final int rangeY;
    private final int rangeZ;

    MultiBlockReplacerModes(String name, int rangeX, int rangeY, int rangeZ) {
        this.name = name;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
    }

    @Override
    public Component getName() {
        return JobsPlusTools.translatable("item.mode.replacer.multi." + name);
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
