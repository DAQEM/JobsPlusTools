package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class LongswordItem extends SwordItem {

    public LongswordItem(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB.get()));
    }
}