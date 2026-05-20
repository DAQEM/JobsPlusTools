package com.daqem.jobsplustools.item.mode.type.fishing;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.fishing.MultiBobberModes;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import net.minecraft.resources.Identifier;

public class MultiBobberType implements IModeType {

    @Override
    public Identifier getId() {
        return JobsPlusTools.API.getId("multi_bobber");
    }

    @Override
    public Class<? extends IMode> getModeClass() {
        return MultiBobberModes.class;
    }
}