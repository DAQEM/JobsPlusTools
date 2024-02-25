package com.daqem.jobsplustools.fabric;

import com.daqem.jobsplustools.JobsPlusTools;
import net.fabricmc.api.ModInitializer;

public class JobsPlusToolsCommonFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        JobsPlusTools.init();
    }

}
