package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public interface JobsPlusToolsItems {

    Registrar<Item> ITEMS = JobsPlusTools.MANAGER.get().get(Registries.ITEM);

    RegistrySupplier<Item> WOODEN_LONGSWORD = ITEMS.register(JobsPlusTools.getId("wooden_longsword"), () -> new LongswordItem(Tiers.WOOD, new Item.Properties().attributes(LongswordItem.createAttributes(Tiers.WOOD, 4, -2.4F))));
    RegistrySupplier<Item> WOODEN_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("wooden_compound_bow"), () -> new CompoundBowItem(Tiers.WOOD, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("wooden_excavator"), () -> new ExcavatorItem(Tiers.WOOD, new Item.Properties().attributes(ExcavatorItem.createAttributes(Tiers.WOOD, 1.5f, -3.0F))));
    RegistrySupplier<Item> WOODEN_HAMMER = ITEMS.register(JobsPlusTools.getId("wooden_hammer"), () -> new HammerItem(Tiers.WOOD, new Item.Properties().attributes(HammerItem.createAttributes(Tiers.WOOD, 1, -2.8F))));
    RegistrySupplier<Item> WOODEN_HATCHET = ITEMS.register(JobsPlusTools.getId("wooden_hatchet"), () -> new HatchetItem(Tiers.WOOD, new Item.Properties().attributes(HatchetItem.createAttributes(Tiers.WOOD, 6.0f, -3.2F))));
    RegistrySupplier<Item> WOODEN_HARVESTER = ITEMS.register(JobsPlusTools.getId("wooden_harvester"), () -> new HarvesterItem(Tiers.WOOD, new Item.Properties().attributes(HarvesterItem.createAttributes(Tiers.WOOD, 0, -3.0F))));
    RegistrySupplier<Item> WOODEN_CORE = ITEMS.register(JobsPlusTools.getId("wooden_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> WOODEN_GRIP = ITEMS.register(JobsPlusTools.getId("wooden_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> STONE_LONGSWORD = ITEMS.register(JobsPlusTools.getId("stone_longsword"), () -> new LongswordItem(Tiers.STONE, new Item.Properties().attributes(LongswordItem.createAttributes(Tiers.STONE, 4, -2.4F))));
    RegistrySupplier<Item> STONE_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("stone_compound_bow"), () -> new CompoundBowItem(Tiers.STONE, new Item.Properties()));
    RegistrySupplier<Item> STONE_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("stone_excavator"), () -> new ExcavatorItem(Tiers.STONE, new Item.Properties().attributes(ExcavatorItem.createAttributes(Tiers.STONE, 1.5f, -3.0F))));
    RegistrySupplier<Item> STONE_HAMMER = ITEMS.register(JobsPlusTools.getId("stone_hammer"), () -> new HammerItem(Tiers.STONE, new Item.Properties().attributes(HammerItem.createAttributes(Tiers.STONE, 1, -2.8F))));
    RegistrySupplier<Item> STONE_HATCHET = ITEMS.register(JobsPlusTools.getId("stone_hatchet"), () -> new HatchetItem(Tiers.STONE, new Item.Properties().attributes(HatchetItem.createAttributes(Tiers.STONE, 7.0f, -3.2f))));
    RegistrySupplier<Item> STONE_HARVESTER = ITEMS.register(JobsPlusTools.getId("stone_harvester"), () -> new HarvesterItem(Tiers.STONE, new Item.Properties().attributes(HarvesterItem.createAttributes(Tiers.STONE, -1, -2.0f))));
    RegistrySupplier<Item> STONE_CORE = ITEMS.register(JobsPlusTools.getId("stone_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> STONE_GRIP = ITEMS.register(JobsPlusTools.getId("stone_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> IRON_LONGSWORD = ITEMS.register(JobsPlusTools.getId("iron_longsword"), () -> new LongswordItem(Tiers.IRON, new Item.Properties().attributes(LongswordItem.createAttributes(Tiers.IRON, 5, -2.4F))));
    RegistrySupplier<Item> IRON_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("iron_compound_bow"), () -> new CompoundBowItem(Tiers.IRON, new Item.Properties()));
    RegistrySupplier<Item> IRON_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("iron_excavator"), () -> new ExcavatorItem(Tiers.IRON, new Item.Properties().attributes(ExcavatorItem.createAttributes(Tiers.IRON, 1.5f, -3.0F))));
    RegistrySupplier<Item> IRON_HAMMER = ITEMS.register(JobsPlusTools.getId("iron_hammer"), () -> new HammerItem(Tiers.IRON, new Item.Properties().attributes(HammerItem.createAttributes(Tiers.IRON, 1, -2.8F))));
    RegistrySupplier<Item> IRON_HATCHET = ITEMS.register(JobsPlusTools.getId("iron_hatchet"), () -> new HatchetItem(Tiers.IRON, new Item.Properties().attributes(HatchetItem.createAttributes(Tiers.IRON, 6.0f, -3.1f))));
    RegistrySupplier<Item> IRON_HARVESTER = ITEMS.register(JobsPlusTools.getId("iron_harvester"), () -> new HarvesterItem(Tiers.IRON, new Item.Properties().attributes(HarvesterItem.createAttributes(Tiers.IRON, -2, -1.0f))));
    RegistrySupplier<Item> IRON_CORE = ITEMS.register(JobsPlusTools.getId("iron_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> IRON_GRIP = ITEMS.register(JobsPlusTools.getId("iron_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> GOLDEN_LONGSWORD = ITEMS.register(JobsPlusTools.getId("golden_longsword"), () -> new LongswordItem(Tiers.GOLD, new Item.Properties().attributes(LongswordItem.createAttributes(Tiers.GOLD, 5, -2.4F))));
    RegistrySupplier<Item> GOLDEN_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("golden_compound_bow"), () -> new CompoundBowItem(Tiers.GOLD, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("golden_excavator"), () -> new ExcavatorItem(Tiers.GOLD, new Item.Properties().attributes(ExcavatorItem.createAttributes(Tiers.GOLD, 1.5f, -3.0F))));
    RegistrySupplier<Item> GOLDEN_HAMMER = ITEMS.register(JobsPlusTools.getId("golden_hammer"), () -> new HammerItem(Tiers.GOLD, new Item.Properties().attributes(HammerItem.createAttributes(Tiers.GOLD, 1, -2.8F))));
    RegistrySupplier<Item> GOLDEN_HATCHET = ITEMS.register(JobsPlusTools.getId("golden_hatchet"), () -> new HatchetItem(Tiers.GOLD, new Item.Properties().attributes(HatchetItem.createAttributes(Tiers.GOLD, 5.5f, -3.1F))));
    RegistrySupplier<Item> GOLDEN_HARVESTER = ITEMS.register(JobsPlusTools.getId("golden_harvester"), () -> new HarvesterItem(Tiers.GOLD, new Item.Properties().attributes(HarvesterItem.createAttributes(Tiers.GOLD, -2, -0.5F))));
    RegistrySupplier<Item> GOLDEN_CORE = ITEMS.register(JobsPlusTools.getId("golden_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> GOLDEN_GRIP = ITEMS.register(JobsPlusTools.getId("golden_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> DIAMOND_LONGSWORD = ITEMS.register(JobsPlusTools.getId("diamond_longsword"), () -> new LongswordItem(Tiers.DIAMOND, new Item.Properties().attributes(LongswordItem.createAttributes(Tiers.DIAMOND, 6, -2.4F))));
    RegistrySupplier<Item> DIAMOND_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("diamond_compound_bow"), () -> new CompoundBowItem(Tiers.DIAMOND, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("diamond_excavator"), () -> new ExcavatorItem(Tiers.DIAMOND, new Item.Properties().attributes(ExcavatorItem.createAttributes(Tiers.DIAMOND, 1.5f, -3.0F))));
    RegistrySupplier<Item> DIAMOND_HAMMER = ITEMS.register(JobsPlusTools.getId("diamond_hammer"), () -> new HammerItem(Tiers.DIAMOND, new Item.Properties().attributes(HammerItem.createAttributes(Tiers.DIAMOND, 1, -2.8F))));
    RegistrySupplier<Item> DIAMOND_HATCHET = ITEMS.register(JobsPlusTools.getId("diamond_hatchet"), () -> new HatchetItem(Tiers.DIAMOND,  new Item.Properties().attributes(HatchetItem.createAttributes(Tiers.DIAMOND, 5.0f, -3.0f))));
    RegistrySupplier<Item> DIAMOND_HARVESTER = ITEMS.register(JobsPlusTools.getId("diamond_harvester"), () -> new HarvesterItem(Tiers.DIAMOND, new Item.Properties().attributes(HarvesterItem.createAttributes(Tiers.DIAMOND, -3, 0.0F))));
    RegistrySupplier<Item> DIAMOND_CORE = ITEMS.register(JobsPlusTools.getId("diamond_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> DIAMOND_GRIP = ITEMS.register(JobsPlusTools.getId("diamond_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> NETHERITE_LONGSWORD = ITEMS.register(JobsPlusTools.getId("netherite_longsword"), () -> new LongswordItem(Tiers.NETHERITE, new Item.Properties().fireResistant().attributes(LongswordItem.createAttributes(Tiers.NETHERITE, 6, -2.4F))));
    RegistrySupplier<Item> NETHERITE_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("netherite_compound_bow"), () -> new CompoundBowItem(Tiers.NETHERITE, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("netherite_excavator"), () -> new ExcavatorItem(Tiers.NETHERITE, new Item.Properties().fireResistant().attributes(ExcavatorItem.createAttributes(Tiers.NETHERITE, 1.5f, -3.0F))));
    RegistrySupplier<Item> NETHERITE_HAMMER = ITEMS.register(JobsPlusTools.getId("netherite_hammer"), () -> new HammerItem(Tiers.NETHERITE, new Item.Properties().fireResistant().attributes(HammerItem.createAttributes(Tiers.NETHERITE, 1, -2.8F))));
    RegistrySupplier<Item> NETHERITE_HATCHET = ITEMS.register(JobsPlusTools.getId("netherite_hatchet"), () -> new HatchetItem(Tiers.NETHERITE, new Item.Properties().fireResistant().attributes(HatchetItem.createAttributes(Tiers.NETHERITE, 5.0f, -3.0F))));
    RegistrySupplier<Item> NETHERITE_HARVESTER = ITEMS.register(JobsPlusTools.getId("netherite_harvester"), () -> new HarvesterItem(Tiers.NETHERITE, new Item.Properties().fireResistant().attributes(HarvesterItem.createAttributes(Tiers.NETHERITE, -4, 0.0F))));
    RegistrySupplier<Item> NETHERITE_CORE = ITEMS.register(JobsPlusTools.getId("netherite_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB).fireResistant()));

    static void init() {
    }
}
