package com.daqem.jobsplustools.item.mode.breaker.connected;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.Component;

public enum ConnectBlockBreakerModes implements ConnectBlockBreakerMode {

    ON("on"),
    OFF("off");

    private final String name;

    ConnectBlockBreakerModes(String name) {
        this.name = name;
    }

    @Override
    public Component getName() {
        return JobsPlusTools.translatable("item.mode.breaker.connected." + name);
    }
}
