package com.daqem.jobsplustools.forge;

import com.daqem.jobsplustools.client.JobsPlusToolsClient;
import com.daqem.jobsplustools.client.item.JobsPlusToolsItemProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SideProxyForge {

    public SideProxyForge() {
        //Run common code
    }

    public static class Client extends SideProxyForge {

        public Client() {
            //Run client code
            JobsPlusToolsClient.initClient();

            IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
            eventBus.addListener(this::clientSetupHandler);

        }

        @SubscribeEvent
        public void clientSetupHandler(final FMLClientSetupEvent event) {
            JobsPlusToolsItemProperties.init();
        }

    }

    public static class Server extends SideProxyForge {

        public Server() {
            //Run server code
        }
    }
}
