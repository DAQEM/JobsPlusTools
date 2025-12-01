package com.daqem.jobsplustools.item.mode;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectedBlockBreakerModes;
import com.daqem.jobsplustools.item.mode.breaker.multi.MultiBlockBreakerModes;
import com.daqem.jobsplustools.item.mode.replacer.MultiBlockReplacerModes;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public interface IModeType {

    Map<ResourceLocation, IModeType> MODE_TYPES = new HashMap<>();

    IModeType CONNECTED_BLOCK_BREAKER_MODE = register("connected_block_breaker_mode", ConnectedBlockBreakerModes.class);
    IModeType MULTI_BLOCK_BREAKER_MODE = register("multi_block_breaker_mode", MultiBlockBreakerModes.class);
    IModeType MULTI_BLOCK_REPLACER_MODE = register("multi_block_replacer_mode", MultiBlockReplacerModes.class);

    static void init() {
    }

    static IModeType register(String name, Class<? extends IMode> modeClass) {
        ResourceLocation id = JobsPlusTools.getId(name);
        IModeType modeType = new IModeType() {
            @Override
            public ResourceLocation getId() {
                return id;
            }

            @Override
            public Class<? extends IMode> getModeClass() {
                return modeClass;
            }
        };
        MODE_TYPES.put(id, modeType);
        return modeType;
    }

    ResourceLocation getId();
    Class<? extends IMode> getModeClass();
}
