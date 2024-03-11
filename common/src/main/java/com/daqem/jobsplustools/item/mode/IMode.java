package com.daqem.jobsplustools.item.mode;

import net.minecraft.network.chat.Component;

public interface IMode {

    Component getName();

    default float getSpeedMultiplier(int blocsToMine) {
        if (blocsToMine <= 1) return 1F;
        return 1F / blocsToMine * 1.5F;
    }
}
