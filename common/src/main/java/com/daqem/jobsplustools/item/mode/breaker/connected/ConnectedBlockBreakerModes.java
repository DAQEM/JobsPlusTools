package com.daqem.jobsplustools.item.mode.breaker.connected;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.Component;

public enum ConnectedBlockBreakerModes implements ConnectedBlockBreakerMode {

    ON("on"),
    OFF("off");

    private final String name;

    ConnectedBlockBreakerModes(String name) {
        this.name = name;
    }

    @Override
    public Component getName() {
        return JobsPlusTools.translatable("item.mode.breaker.connected." + name);
    }
}
