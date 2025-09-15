package com.daqem.jobsplustools.neoforge;

import com.daqem.jobsplustools.JobsPlusTools;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(JobsPlusTools.MOD_ID)
public class JobsPlusToolsNeoForge {

    public JobsPlusToolsNeoForge(IEventBus modEventBus) {
        JobsPlusTools.init();
    }
}
