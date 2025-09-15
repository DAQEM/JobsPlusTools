package com.daqem.jobsplustools.fabric;

import com.daqem.jobsplustools.client.JobsPlusToolsClient;
import net.fabricmc.api.ClientModInitializer;

public class JobsPlusToolsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        JobsPlusToolsClient.initClient();
    }
}
