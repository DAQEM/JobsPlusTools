package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.world.item.BowItem;

public class CompoundBowItem extends BowItem {

    public CompoundBowItem(Properties properties) {
        super(properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB.get()));
    }
}
