package com.daqem.jobsplustools.neoforge;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.client.JobsPlusToolsClient;
import com.daqem.jobsplustools.client.item.JobsPlusToolsItemProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class SideProxyNeoForge {

    public SideProxyNeoForge() {
        JobsPlusTools.init();
    }

    public static class Client extends SideProxyNeoForge {

        public Client(IEventBus modEventBus) {
            //Run client code
            JobsPlusToolsClient.initClient();
            modEventBus.addListener(this::clientSetupHandler);

        }

        @SubscribeEvent
        public void clientSetupHandler(final FMLClientSetupEvent event) {
            JobsPlusToolsItemProperties.init();
        }

    }

    public static class Server extends SideProxyNeoForge {

        public Server(IEventBus modEventBus) {
            //Run server code
        }
    }
}
