package com.daqem.jobsplustools;

import com.daqem.jobsplustools.entity.JobsPlusToolsEntityTypes;
import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;

import com.daqem.jobsplustools.event.BreakBlockEvent;
import com.daqem.jobsplustools.item.JobsPlusToolsItems;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class JobsPlusTools {
    public static final String MOD_ID = "jobsplustools";
    public static final Knot API = new Knot(MOD_ID);
    public static final ChatFormatting ITEM_TOOLTIP_STYLE = ChatFormatting.GRAY;

    public static final Registry<CreativeModeTab> TABS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryEntry<CreativeModeTab> JOBSPLUS_TOOLS_TAB = TABS.register("my_tab", () ->
            Knot.CREATIVE_TABS_REGISTRY.build(
                    Component.translatable("itemGroup." + JobsPlusTools.MOD_ID + "." + JobsPlusTools.MOD_ID + "_tab"),
                    () -> new ItemStack(JobsPlusToolsItems.DIAMOND_HAMMER.get())
            )
    );

    public static void init() {
        JobsPlusToolsDataComponentTypes.init();
        JobsPlusToolsItems.init();
        JobsPlusToolsEntityTypes.init();

        BreakBlockEvent.registerEvent();
    }
}
