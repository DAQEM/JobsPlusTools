package com.daqem.jobsplustools.neoforge;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.client.JobsPlusToolsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = JobsPlusTools.MOD_ID, dist = Dist.CLIENT)
public class JobsPlusToolsNeoForgeClient {

    public JobsPlusToolsNeoForgeClient() {
        JobsPlusToolsClient.initClient();
    }
}
