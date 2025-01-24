package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class LongswordItem extends SwordItem {

    public LongswordItem(Tier tier, Properties properties) {
        //noinspection UnstableApiUsage
        super(tier, properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB));
    }
}