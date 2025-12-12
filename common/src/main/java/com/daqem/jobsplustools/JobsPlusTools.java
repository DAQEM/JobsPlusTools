package com.daqem.jobsplustools;

import java.util.function.Supplier;

import com.daqem.jobsplustools.entity.JobsPlusToolsEntityTypes;
import org.slf4j.Logger;

import com.daqem.jobsplustools.event.BreakBlockEvent;
import com.daqem.jobsplustools.item.JobsPlusToolsItems;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class JobsPlusTools {
    public static final String MOD_ID = "jobsplustools";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ChatFormatting ITEM_TOOLTIP_STYLE = ChatFormatting.GRAY;

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Registrar<CreativeModeTab> TABS = JobsPlusTools.MANAGER.get().get(Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> JOBSPLUS_TOOLS_TAB = TABS.register(JobsPlusTools.getId(JobsPlusTools.MOD_ID + "_tab"), () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + JobsPlusTools.MOD_ID + "." + JobsPlusTools.MOD_ID + "_tab"),
                    () -> new ItemStack(JobsPlusToolsItems.DIAMOND_HAMMER.get())));

    public static void init() {
        JobsPlusToolsDataComponentTypes.init();
        JobsPlusToolsItems.init();
        JobsPlusToolsEntityTypes.init();

        registerEvents();
    }

    private static void registerEvents() {
        BreakBlockEvent.registerEvent();
    }

    public static MutableComponent translatable(String s) {
        return translatable(s, new Object[0]);
    }

    public static MutableComponent translatable(String s, Object... objects) {
        return Component.translatable(MOD_ID + "." + s, objects);
    }

    public static ResourceLocation getId(String str) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, str);
    }

    public static MutableComponent literal(String str) {
        return Component.literal(str);
    }
}
