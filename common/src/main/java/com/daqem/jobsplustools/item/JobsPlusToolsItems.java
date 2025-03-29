package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.function.Function;
import java.util.function.Supplier;

public interface JobsPlusToolsItems {

    Registrar<Item> ITEMS = JobsPlusTools.MANAGER.get().get(Registries.ITEM);

    RegistrySupplier<Item> WOODEN_LONGSWORD = item(JobsPlusTools.getId("wooden_longsword"), p -> new LongswordItem(p.sword(ToolMaterial.WOOD, 4, -2.4F)));
    RegistrySupplier<Item> WOODEN_COMPOUND_BOW = item(JobsPlusTools.getId("wooden_compound_bow"), p -> new CompoundBowItem(ToolMaterial.WOOD, p));
    RegistrySupplier<Item> WOODEN_EXCAVATOR = item(JobsPlusTools.getId("wooden_excavator"), p -> new ExcavatorItem(ToolMaterial.WOOD, 1.5f, -3.0F, p.shovel(ToolMaterial.WOOD, 1.5f, -3.0F)));
    RegistrySupplier<Item> WOODEN_HAMMER = item(JobsPlusTools.getId("wooden_hammer"), p -> new HammerItem(ToolMaterial.WOOD, p.pickaxe(ToolMaterial.WOOD, 1, -2.8F)));
    RegistrySupplier<Item> WOODEN_HATCHET = item(JobsPlusTools.getId("wooden_hatchet"), p -> new HatchetItem(ToolMaterial.WOOD, 6.0f, -3.2F, p.axe(ToolMaterial.WOOD, 6.0f, -3.2F)));
    RegistrySupplier<Item> WOODEN_HARVESTER = item(JobsPlusTools.getId("wooden_harvester"), p -> new HarvesterItem(ToolMaterial.WOOD, 0, -3.0F, p.hoe(ToolMaterial.WOOD, 0, -3.0F)));
    RegistrySupplier<Item> WOODEN_CORE = item(JobsPlusTools.getId("wooden_core"), Item::new);
    RegistrySupplier<Item> WOODEN_GRIP = item(JobsPlusTools.getId("wooden_grip"), Item::new);

    RegistrySupplier<Item> STONE_LONGSWORD = item(JobsPlusTools.getId("stone_longsword"), p -> new LongswordItem(p.sword(ToolMaterial.STONE, 4, -2.4F)));
    RegistrySupplier<Item> STONE_COMPOUND_BOW = item(JobsPlusTools.getId("stone_compound_bow"), p -> new CompoundBowItem(ToolMaterial.STONE, p));
    RegistrySupplier<Item> STONE_EXCAVATOR = item(JobsPlusTools.getId("stone_excavator"), p -> new ExcavatorItem(ToolMaterial.STONE, 1.5f, -3.0F, p.shovel(ToolMaterial.STONE, 1.5f, -3.0F)));
    RegistrySupplier<Item> STONE_HAMMER = item(JobsPlusTools.getId("stone_hammer"), p -> new HammerItem(ToolMaterial.STONE, p.pickaxe(ToolMaterial.STONE, 1, -2.8F)));
    RegistrySupplier<Item> STONE_HATCHET = item(JobsPlusTools.getId("stone_hatchet"), p -> new HatchetItem(ToolMaterial.STONE, 7.0f, -3.2f, p.axe(ToolMaterial.STONE, 7.0f, -3.2f)));
    RegistrySupplier<Item> STONE_HARVESTER = item(JobsPlusTools.getId("stone_harvester"), p -> new HarvesterItem(ToolMaterial.STONE, -1, -2.0f, p.hoe(ToolMaterial.STONE, -1, -2.0f)));
    RegistrySupplier<Item> STONE_CORE = item(JobsPlusTools.getId("stone_core"), Item::new);
    RegistrySupplier<Item> STONE_GRIP = item(JobsPlusTools.getId("stone_grip"), Item::new);

    RegistrySupplier<Item> IRON_LONGSWORD = item(JobsPlusTools.getId("iron_longsword"), p -> new LongswordItem(p.sword(ToolMaterial.IRON, 5, -2.4F)));
    RegistrySupplier<Item> IRON_COMPOUND_BOW = item(JobsPlusTools.getId("iron_compound_bow"), p -> new CompoundBowItem(ToolMaterial.IRON, p));
    RegistrySupplier<Item> IRON_EXCAVATOR = item(JobsPlusTools.getId("iron_excavator"), p -> new ExcavatorItem(ToolMaterial.IRON, 1.5f, -3.0F, p.shovel(ToolMaterial.IRON, 1.5f, -3.0F)));
    RegistrySupplier<Item> IRON_HAMMER = item(JobsPlusTools.getId("iron_hammer"), p -> new HammerItem(ToolMaterial.IRON, p.pickaxe(ToolMaterial.IRON, 1, -2.8F)));
    RegistrySupplier<Item> IRON_HATCHET = item(JobsPlusTools.getId("iron_hatchet"), p -> new HatchetItem(ToolMaterial.IRON, 6.0f, -3.1f, p.axe(ToolMaterial.IRON, 6.0f, -3.1f)));
    RegistrySupplier<Item> IRON_HARVESTER = item(JobsPlusTools.getId("iron_harvester"), p -> new HarvesterItem(ToolMaterial.IRON, -2, -1.0f, p.hoe(ToolMaterial.IRON, -2, -1.0f)));
    RegistrySupplier<Item> IRON_CORE = item(JobsPlusTools.getId("iron_core"), Item::new);
    RegistrySupplier<Item> IRON_GRIP = item(JobsPlusTools.getId("iron_grip"), Item::new);

    RegistrySupplier<Item> GOLDEN_LONGSWORD = item(JobsPlusTools.getId("golden_longsword"), p -> new LongswordItem(p.sword(ToolMaterial.GOLD, 5, -2.4F)));
    RegistrySupplier<Item> GOLDEN_COMPOUND_BOW = item(JobsPlusTools.getId("golden_compound_bow"), p -> new CompoundBowItem(ToolMaterial.GOLD, p));
    RegistrySupplier<Item> GOLDEN_EXCAVATOR = item(JobsPlusTools.getId("golden_excavator"), p -> new ExcavatorItem(ToolMaterial.GOLD, 1.5f, -3.0F, p.shovel(ToolMaterial.GOLD, 1.5f, -3.0F)));
    RegistrySupplier<Item> GOLDEN_HAMMER = item(JobsPlusTools.getId("golden_hammer"), p -> new HammerItem(ToolMaterial.GOLD, p.pickaxe(ToolMaterial.GOLD, 1, -2.8F)));
    RegistrySupplier<Item> GOLDEN_HATCHET = item(JobsPlusTools.getId("golden_hatchet"), p -> new HatchetItem(ToolMaterial.GOLD, 5.5f, -3.1F, p.axe(ToolMaterial.GOLD, 5.5f, -3.1F)));
    RegistrySupplier<Item> GOLDEN_HARVESTER = item(JobsPlusTools.getId("golden_harvester"), p -> new HarvesterItem(ToolMaterial.GOLD, -2, -0.5F, p.hoe(ToolMaterial.GOLD, -2, -0.5F)));
    RegistrySupplier<Item> GOLDEN_CORE = item(JobsPlusTools.getId("golden_core"), Item::new);
    RegistrySupplier<Item> GOLDEN_GRIP = item(JobsPlusTools.getId("golden_grip"), Item::new);

    RegistrySupplier<Item> DIAMOND_LONGSWORD = item(JobsPlusTools.getId("diamond_longsword"), p -> new LongswordItem(p.sword(ToolMaterial.DIAMOND, 6, -2.4F)));
    RegistrySupplier<Item> DIAMOND_COMPOUND_BOW = item(JobsPlusTools.getId("diamond_compound_bow"), p -> new CompoundBowItem(ToolMaterial.DIAMOND, p));
    RegistrySupplier<Item> DIAMOND_EXCAVATOR = item(JobsPlusTools.getId("diamond_excavator"), p -> new ExcavatorItem(ToolMaterial.DIAMOND, 1.5f, -3.0F, p.shovel(ToolMaterial.DIAMOND, 1.5f, -3.0F)));
    RegistrySupplier<Item> DIAMOND_HAMMER = item(JobsPlusTools.getId("diamond_hammer"), p -> new HammerItem(ToolMaterial.DIAMOND, p.pickaxe(ToolMaterial.DIAMOND, 1, -2.8F)));
    RegistrySupplier<Item> DIAMOND_HATCHET = item(JobsPlusTools.getId("diamond_hatchet"), p -> new HatchetItem(ToolMaterial.DIAMOND, 5.0f, -3.0f,  p.axe(ToolMaterial.DIAMOND, 5.0f, -3.0f)));
    RegistrySupplier<Item> DIAMOND_HARVESTER = item(JobsPlusTools.getId("diamond_harvester"), p -> new HarvesterItem(ToolMaterial.DIAMOND, -3, 0.0F, p.hoe(ToolMaterial.DIAMOND, -3, 0.0F)));
    RegistrySupplier<Item> DIAMOND_CORE = item(JobsPlusTools.getId("diamond_core"), Item::new);
    RegistrySupplier<Item> DIAMOND_GRIP = item(JobsPlusTools.getId("diamond_grip"), Item::new);

    RegistrySupplier<Item> NETHERITE_LONGSWORD = item(JobsPlusTools.getId("netherite_longsword"), p -> new LongswordItem(p.fireResistant().sword(ToolMaterial.NETHERITE, 6, -2.4F)));
    RegistrySupplier<Item> NETHERITE_COMPOUND_BOW = item(JobsPlusTools.getId("netherite_compound_bow"), p -> new CompoundBowItem(ToolMaterial.NETHERITE, p.fireResistant()));
    RegistrySupplier<Item> NETHERITE_EXCAVATOR = item(JobsPlusTools.getId("netherite_excavator"), p -> new ExcavatorItem(ToolMaterial.NETHERITE, 1.5f, -3.0F, p.fireResistant().shovel(ToolMaterial.NETHERITE, 1.5f, -3.0F)));
    RegistrySupplier<Item> NETHERITE_HAMMER = item(JobsPlusTools.getId("netherite_hammer"), p -> new HammerItem(ToolMaterial.NETHERITE, p.fireResistant().pickaxe(ToolMaterial.NETHERITE, 1, -2.8F)));
    RegistrySupplier<Item> NETHERITE_HATCHET = item(JobsPlusTools.getId("netherite_hatchet"), p -> new HatchetItem(ToolMaterial.NETHERITE, 5.0f, -3.0F, p.fireResistant().axe(ToolMaterial.NETHERITE, 5.0f, -3.0F)));
    RegistrySupplier<Item> NETHERITE_HARVESTER = item(JobsPlusTools.getId("netherite_harvester"), p -> new HarvesterItem(ToolMaterial.NETHERITE, -4, 0.0F, p.fireResistant().hoe(ToolMaterial.NETHERITE, -4, 0.0F)));
    RegistrySupplier<Item> NETHERITE_CORE = item(JobsPlusTools.getId("netherite_core"), p -> new Item(p.fireResistant()));

    static void init() {
    }

    static <T extends Item> RegistrySupplier<T> item(ResourceLocation id, Function<Item.Properties, T> constructor) {
        return ITEMS.register(id, () -> constructor.apply(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB).setId(ResourceKey.create(Registries.ITEM, id))));
    }
}
