package com.daqem.jobsplustools.fabric;

import com.daqem.jobsplustools.client.JobsPlusToolsClient;
import com.daqem.jobsplustools.client.item.JobsPlusToolsItemProperties;
import net.fabricmc.api.ClientModInitializer;

public class JobsPlusToolsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        JobsPlusToolsClient.initClient();
        JobsPlusToolsItemProperties.init();
    }
}
