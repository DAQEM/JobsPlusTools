package com.daqem.jobsplustools.fabric;

import com.daqem.client.JobsPlusToolsClient;
import com.daqem.client.item.JobsPlusToolsItemProperties;
import net.fabricmc.api.ClientModInitializer;

public class JobsPlusToolsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        JobsPlusToolsClient.initClient();
        JobsPlusToolsItemProperties.init();
    }
}
