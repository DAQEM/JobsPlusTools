package com.daqem.jobsplustools.item.mode.fishing;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.MutableComponent;

public enum MultiBobberModes implements MultiBobberMode {
    ONE("1", 1),
    TWO("2", 2),
    THREE("3", 3),
    FIVE("5", 5);

    private final String name;
    private final int amount;

    MultiBobberModes(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public MutableComponent getName() {
        return JobsPlusTools.API.translatable("item.mode.fishing.multi." + name);
    }

    @Override
    public int getAmount() {
        return amount;
    }
}