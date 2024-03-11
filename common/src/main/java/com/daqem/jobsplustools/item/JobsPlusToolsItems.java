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

    RegistrySupplier<Item> WOODEN_LONGSWORD = ITEMS.register(JobsPlusTools.getId("wooden_longsword"), () -> new LongswordItem(Tiers.WOOD, 4, -2.4F, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("wooden_compound_bow"), () -> new CompoundBowItem(Tiers.WOOD, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("wooden_excavator"), () -> new ExcavatorItem(Tiers.WOOD, 1.5f, -3.0F, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_HAMMER = ITEMS.register(JobsPlusTools.getId("wooden_hammer"), () -> new HammerItem(Tiers.WOOD, 1, -2.8F, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_HATCHET = ITEMS.register(JobsPlusTools.getId("wooden_hatchet"), () -> new HatchetItem(Tiers.WOOD, 6.0f, -3.2F, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_HARVESTER = ITEMS.register(JobsPlusTools.getId("wooden_harvester"), () -> new HarvesterItem(Tiers.WOOD, 0, -3.0F, new Item.Properties()));
    RegistrySupplier<Item> WOODEN_CORE = ITEMS.register(JobsPlusTools.getId("wooden_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> WOODEN_GRIP = ITEMS.register(JobsPlusTools.getId("wooden_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> STONE_LONGSWORD = ITEMS.register(JobsPlusTools.getId("stone_longsword"), () -> new LongswordItem(Tiers.STONE, 4, -2.4F, new Item.Properties()));
    RegistrySupplier<Item> STONE_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("stone_compound_bow"), () -> new CompoundBowItem(Tiers.STONE, new Item.Properties()));
    RegistrySupplier<Item> STONE_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("stone_excavator"), () -> new ExcavatorItem(Tiers.STONE, 1.5f, -3.0F, new Item.Properties()));
    RegistrySupplier<Item> STONE_HAMMER = ITEMS.register(JobsPlusTools.getId("stone_hammer"), () -> new HammerItem(Tiers.STONE, 1, -2.8F, new Item.Properties()));
    RegistrySupplier<Item> STONE_HATCHET = ITEMS.register(JobsPlusTools.getId("stone_hatchet"), () -> new HatchetItem(Tiers.STONE, 7.0f, -3.2f, new Item.Properties()));
    RegistrySupplier<Item> STONE_HARVESTER = ITEMS.register(JobsPlusTools.getId("stone_harvester"), () -> new HarvesterItem(Tiers.STONE, -1, -2.0f, new Item.Properties()));
    RegistrySupplier<Item> STONE_CORE = ITEMS.register(JobsPlusTools.getId("stone_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> STONE_GRIP = ITEMS.register(JobsPlusTools.getId("stone_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> IRON_LONGSWORD = ITEMS.register(JobsPlusTools.getId("iron_longsword"), () -> new LongswordItem(Tiers.IRON, 5, -2.4F, new Item.Properties()));
    RegistrySupplier<Item> IRON_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("iron_compound_bow"), () -> new CompoundBowItem(Tiers.IRON, new Item.Properties()));
    RegistrySupplier<Item> IRON_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("iron_excavator"), () -> new ExcavatorItem(Tiers.IRON, 1.5f, -3.0F, new Item.Properties()));
    RegistrySupplier<Item> IRON_HAMMER = ITEMS.register(JobsPlusTools.getId("iron_hammer"), () -> new HammerItem(Tiers.IRON, 1, -2.8F, new Item.Properties()));
    RegistrySupplier<Item> IRON_HATCHET = ITEMS.register(JobsPlusTools.getId("iron_hatchet"), () -> new HatchetItem(Tiers.IRON, 6.0f, -3.1f, new Item.Properties()));
    RegistrySupplier<Item> IRON_HARVESTER = ITEMS.register(JobsPlusTools.getId("iron_harvester"), () -> new HarvesterItem(Tiers.IRON, -2, -1.0f, new Item.Properties()));
    RegistrySupplier<Item> IRON_CORE = ITEMS.register(JobsPlusTools.getId("iron_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> IRON_GRIP = ITEMS.register(JobsPlusTools.getId("iron_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> GOLDEN_LONGSWORD = ITEMS.register(JobsPlusTools.getId("golden_longsword"), () -> new LongswordItem(Tiers.GOLD, 5, -2.4F, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("golden_compound_bow"), () -> new CompoundBowItem(Tiers.GOLD, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("golden_excavator"), () -> new ExcavatorItem(Tiers.GOLD, 1.5f, -3.0F, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_HAMMER = ITEMS.register(JobsPlusTools.getId("golden_hammer"), () -> new HammerItem(Tiers.GOLD, 1, -2.8F, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_HATCHET = ITEMS.register(JobsPlusTools.getId("golden_hatchet"), () -> new HatchetItem(Tiers.GOLD, 5.5f, -3.1F, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_HARVESTER = ITEMS.register(JobsPlusTools.getId("golden_harvester"), () -> new HarvesterItem(Tiers.GOLD, -2, -0.5F, new Item.Properties()));
    RegistrySupplier<Item> GOLDEN_CORE = ITEMS.register(JobsPlusTools.getId("golden_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> GOLDEN_GRIP = ITEMS.register(JobsPlusTools.getId("golden_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> DIAMOND_LONGSWORD = ITEMS.register(JobsPlusTools.getId("diamond_longsword"), () -> new LongswordItem(Tiers.DIAMOND, 6, -2.4F, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("diamond_compound_bow"), () -> new CompoundBowItem(Tiers.DIAMOND, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("diamond_excavator"), () -> new ExcavatorItem(Tiers.DIAMOND, 1.5f, -3.0F, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_HAMMER = ITEMS.register(JobsPlusTools.getId("diamond_hammer"), () -> new HammerItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_HATCHET = ITEMS.register(JobsPlusTools.getId("diamond_hatchet"), () -> new HatchetItem(Tiers.DIAMOND, 5.0f, -3.0f, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_HARVESTER = ITEMS.register(JobsPlusTools.getId("diamond_harvester"), () -> new HarvesterItem(Tiers.DIAMOND, -3, 0.0F, new Item.Properties()));
    RegistrySupplier<Item> DIAMOND_CORE = ITEMS.register(JobsPlusTools.getId("diamond_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));
    RegistrySupplier<Item> DIAMOND_GRIP = ITEMS.register(JobsPlusTools.getId("diamond_grip"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB)));

    RegistrySupplier<Item> NETHERITE_LONGSWORD = ITEMS.register(JobsPlusTools.getId("netherite_longsword"), () -> new LongswordItem(Tiers.NETHERITE, 6, -2.4F, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_COMPOUND_BOW = ITEMS.register(JobsPlusTools.getId("netherite_compound_bow"), () -> new CompoundBowItem(Tiers.NETHERITE, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_EXCAVATOR = ITEMS.register(JobsPlusTools.getId("netherite_excavator"), () -> new ExcavatorItem(Tiers.NETHERITE, 1.5f, -3.0F, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_HAMMER = ITEMS.register(JobsPlusTools.getId("netherite_hammer"), () -> new HammerItem(Tiers.NETHERITE, 1, -2.8F, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_HATCHET = ITEMS.register(JobsPlusTools.getId("netherite_hatchet"), () -> new HatchetItem(Tiers.NETHERITE, 5.0f, -3.0F, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_HARVESTER = ITEMS.register(JobsPlusTools.getId("netherite_harvester"), () -> new HarvesterItem(Tiers.NETHERITE, -4, 0.0F, new Item.Properties().fireResistant()));
    RegistrySupplier<Item> NETHERITE_CORE = ITEMS.register(JobsPlusTools.getId("netherite_core"), () -> new Item(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB).fireResistant()));

    static void init() {
    }
}
