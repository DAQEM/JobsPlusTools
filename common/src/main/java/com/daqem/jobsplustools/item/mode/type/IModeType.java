package com.daqem.jobsplustools.item.mode.type;

import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.breaker.ConnectedBlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.breaker.MultiBlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.breaker.TreeBreakerType;
import com.daqem.jobsplustools.item.mode.type.replacer.CropReplacerType;
import com.daqem.jobsplustools.item.mode.type.replacer.MultiBlockReplacerType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public interface IModeType {

    Map<ResourceLocation, IModeType> MODE_TYPES = new HashMap<>();

    ConnectedBlockBreakerType CONNECTED_BLOCK_BREAKER_MODE = register(new ConnectedBlockBreakerType());
    TreeBreakerType TREE_BREAKER_MODE = register(new TreeBreakerType());
    MultiBlockBreakerType MULTI_BLOCK_BREAKER_MODE = register(new MultiBlockBreakerType());
    MultiBlockReplacerType MULTI_BLOCK_REPLACER_MODE = register(new MultiBlockReplacerType());
    CropReplacerType CROP_REPLACER_MODE = register(new CropReplacerType());

    static <T extends IModeType> T register(T modeType) {
        MODE_TYPES.put(modeType.getId(), modeType);
        return modeType;
    }

    default IMode getSelectedMode(ModeItemComponent component) {
        IMode[] allModes = getModeClass().getEnumConstants();
        return allModes[component.selectedMode()];
    }

    ResourceLocation getId();

    Class<? extends IMode> getModeClass();
}
