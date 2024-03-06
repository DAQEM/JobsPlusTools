package com.daqem.jobsplustools.forge;

import dev.architectury.platform.forge.EventBuses;
import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JobsPlusTools.MOD_ID)
public class JobsPlusToolsForge {

    public JobsPlusToolsForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(JobsPlusTools.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        JobsPlusTools.init();
        DistExecutor.safeRunForDist(
                () -> SideProxyForge.Client::new,
                () -> SideProxyForge.Server::new);
    }
}
