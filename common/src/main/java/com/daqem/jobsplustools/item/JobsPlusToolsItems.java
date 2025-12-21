package com.daqem.jobsplustools.item;

import java.util.List;
import java.util.function.Function;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.component.ExperienceItemComponent;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.component.PotionStorageItemComponent;
import com.daqem.jobsplustools.item.mode.type.IModeType;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.alchemy.PotionContents;

public interface JobsPlusToolsItems {

    Registrar<Item> ITEMS = JobsPlusTools.MANAGER.get().get(Registries.ITEM);

    // WOODEN
    RegistrySupplier<Item> WOODEN_LONGSWORD = item(JobsPlusTools.getId("wooden_longsword"), p -> new LongswordItem(ToolMaterial.WOOD, 5, -3.2F, p));
    RegistrySupplier<Item> WOODEN_COMPOUND_BOW = item(JobsPlusTools.getId("wooden_compound_bow"), p -> new CompoundBowItem(ToolMaterial.WOOD, p));
    RegistrySupplier<Item> WOODEN_EXCAVATOR = item(JobsPlusTools.getId("wooden_excavator"), p -> new ExcavatorItem(ToolMaterial.WOOD, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> WOODEN_HAMMER = item(JobsPlusTools.getId("wooden_hammer"), p -> new HammerItem(ToolMaterial.WOOD, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> WOODEN_HATCHET = item(JobsPlusTools.getId("wooden_hatchet"), p -> new AxeItem(ToolMaterial.WOOD, 6.0f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> WOODEN_HARVESTER = item(JobsPlusTools.getId("wooden_harvester"), p -> new HoeItem(ToolMaterial.WOOD, 0, -3.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0)))));
    RegistrySupplier<Item> WOODEN_TROWEL = item(JobsPlusTools.getId("wooden_trowel"), p -> new TrowelItem(ToolMaterial.WOOD, 0, -3.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0)))));
    RegistrySupplier<Item> WOODEN_WRENCH = item(JobsPlusTools.getId("wooden_wrench"), p -> new WrenchItem(ToolMaterial.WOOD, 0, -3.0F, p));
    RegistrySupplier<Item> WOODEN_WAND = item(JobsPlusTools.getId("wooden_wand"), p -> new WandItem(ToolMaterial.WOOD, 0, -3.0F, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 3, false))));
    RegistrySupplier<Item> WOODEN_EXPERIENCE_JAR = item(JobsPlusTools.getId("wooden_experience_jar"), p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 500))));
    RegistrySupplier<Item> WOODEN_CORE = item(JobsPlusTools.getId("wooden_core"), Item::new);
    RegistrySupplier<Item> WOODEN_GRIP = item(JobsPlusTools.getId("wooden_grip"), Item::new);

    // STONE
    RegistrySupplier<Item> STONE_LONGSWORD = item(JobsPlusTools.getId("stone_longsword"), p -> new LongswordItem(ToolMaterial.STONE, 5, -3.2F, p));
    RegistrySupplier<Item> STONE_COMPOUND_BOW = item(JobsPlusTools.getId("stone_compound_bow"), p -> new CompoundBowItem(ToolMaterial.STONE, p));
    RegistrySupplier<Item> STONE_EXCAVATOR = item(JobsPlusTools.getId("stone_excavator"), p -> new ExcavatorItem(ToolMaterial.STONE, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> STONE_HAMMER = item(JobsPlusTools.getId("stone_hammer"), p -> new HammerItem(ToolMaterial.STONE, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> STONE_HATCHET = item(JobsPlusTools.getId("stone_hatchet"), p -> new AxeItem(ToolMaterial.STONE, 7.0f, -3.2f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> STONE_HARVESTER = item(JobsPlusTools.getId("stone_harvester"), p -> new HoeItem(ToolMaterial.STONE, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0)))));
    RegistrySupplier<Item> STONE_FISHING_ROD = item(JobsPlusTools.getId("stone_fishing_rod"), p -> new FishingRodItem(ToolMaterial.STONE, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> STONE_TROWEL = item(JobsPlusTools.getId("stone_trowel"), p -> new TrowelItem(ToolMaterial.STONE, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0)))));
    RegistrySupplier<Item> STONE_WRENCH = item(JobsPlusTools.getId("stone_wrench"), p -> new WrenchItem(ToolMaterial.STONE, -1, -2.0f, p));
    RegistrySupplier<Item> STONE_WAND = item(JobsPlusTools.getId("stone_wand"), p -> new WandItem(ToolMaterial.STONE, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 9, false))));
    RegistrySupplier<Item> STONE_EXPERIENCE_JAR = item(JobsPlusTools.getId("stone_experience_jar"), p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 1000))));
    RegistrySupplier<Item> STONE_CORE = item(JobsPlusTools.getId("stone_core"), Item::new);
    RegistrySupplier<Item> STONE_GRIP = item(JobsPlusTools.getId("stone_grip"), Item::new);

    // COPPER
    RegistrySupplier<Item> COPPER_LONGSWORD = item(JobsPlusTools.getId("copper_longsword"), p -> new LongswordItem(ToolMaterial.COPPER, 5, -3.2F, p));
    RegistrySupplier<Item> COPPER_COMPOUND_BOW = item(JobsPlusTools.getId("copper_compound_bow"), p -> new CompoundBowItem(ToolMaterial.COPPER, p));
    RegistrySupplier<Item> COPPER_EXCAVATOR = item(JobsPlusTools.getId("copper_excavator"), p -> new ExcavatorItem(ToolMaterial.COPPER, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> COPPER_HAMMER = item(JobsPlusTools.getId("copper_hammer"), p -> new HammerItem(ToolMaterial.COPPER, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> COPPER_HATCHET = item(JobsPlusTools.getId("copper_hatchet"), p -> new AxeItem(ToolMaterial.COPPER, 7.0f, -3.2f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> COPPER_HARVESTER = item(JobsPlusTools.getId("copper_harvester"), p -> new HoeItem(ToolMaterial.COPPER, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0)))));
    RegistrySupplier<Item> COPPER_FISHING_ROD = item(JobsPlusTools.getId("copper_fishing_rod"), p -> new FishingRodItem(ToolMaterial.COPPER, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> COPPER_TROWEL = item(JobsPlusTools.getId("copper_trowel"), p -> new TrowelItem(ToolMaterial.COPPER, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> COPPER_WRENCH = item(JobsPlusTools.getId("copper_wrench"), p -> new WrenchItem(ToolMaterial.COPPER, -1, -2.0f, p));
    RegistrySupplier<Item> COPPER_WAND = item(JobsPlusTools.getId("copper_wand"), p -> new WandItem(ToolMaterial.COPPER, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 9, false))));
    RegistrySupplier<Item> COPPER_EXPERIENCE_JAR = item(JobsPlusTools.getId("copper_experience_jar"), p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 1500))));
    RegistrySupplier<Item> COPPER_CORE = item(JobsPlusTools.getId("copper_core"), Item::new);
    RegistrySupplier<Item> COPPER_GRIP = item(JobsPlusTools.getId("copper_grip"), Item::new);

    // IRON
    RegistrySupplier<Item> IRON_LONGSWORD = item(JobsPlusTools.getId("iron_longsword"), p -> new LongswordItem(ToolMaterial.IRON, 6, -3.1F, p));
    RegistrySupplier<Item> IRON_COMPOUND_BOW = item(JobsPlusTools.getId("iron_compound_bow"), p -> new CompoundBowItem(ToolMaterial.IRON, p));
    RegistrySupplier<Item> IRON_EXCAVATOR = item(JobsPlusTools.getId("iron_excavator"), p -> new ExcavatorItem(ToolMaterial.IRON, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> IRON_HAMMER = item(JobsPlusTools.getId("iron_hammer"), p -> new HammerItem(ToolMaterial.IRON, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> IRON_HATCHET = item(JobsPlusTools.getId("iron_hatchet"), p -> new AxeItem(ToolMaterial.IRON, 6.0f, -3.1f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> IRON_HARVESTER = item(JobsPlusTools.getId("iron_harvester"), p -> new HoeItem(ToolMaterial.IRON, -2, -1.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> IRON_FISHING_ROD = item(JobsPlusTools.getId("iron_fishing_rod"), p -> new FishingRodItem(ToolMaterial.IRON, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> IRON_TROWEL = item(JobsPlusTools.getId("iron_trowel"), p -> new TrowelItem(ToolMaterial.IRON, -2, -1.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> IRON_WRENCH = item(JobsPlusTools.getId("iron_wrench"), p -> new WrenchItem(ToolMaterial.IRON, -2, -1.0f, p));
    RegistrySupplier<Item> IRON_WAND = item(JobsPlusTools.getId("iron_wand"), p -> new WandItem(ToolMaterial.IRON, -2, -1.0f, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 27, false))));
    RegistrySupplier<Item> IRON_EXPERIENCE_JAR = item(JobsPlusTools.getId("iron_experience_jar"), p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 2500))));
    RegistrySupplier<Item> IRON_CORE = item(JobsPlusTools.getId("iron_core"), Item::new);
    RegistrySupplier<Item> IRON_GRIP = item(JobsPlusTools.getId("iron_grip"), Item::new);

    // GOLDEN
    RegistrySupplier<Item> GOLDEN_LONGSWORD = item(JobsPlusTools.getId("golden_longsword"), p -> new LongswordItem(ToolMaterial.GOLD, 6, -3.0F, p));
    RegistrySupplier<Item> GOLDEN_COMPOUND_BOW = item(JobsPlusTools.getId("golden_compound_bow"), p -> new CompoundBowItem(ToolMaterial.GOLD, p));
    RegistrySupplier<Item> GOLDEN_EXCAVATOR = item(JobsPlusTools.getId("golden_excavator"), p -> new ExcavatorItem(ToolMaterial.GOLD, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistrySupplier<Item> GOLDEN_HAMMER = item(JobsPlusTools.getId("golden_hammer"), p -> new HammerItem(ToolMaterial.GOLD, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistrySupplier<Item> GOLDEN_HATCHET = item(JobsPlusTools.getId("golden_hatchet"), p -> new AxeItem(ToolMaterial.GOLD, 5.5f, -3.1F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> GOLDEN_HARVESTER = item(JobsPlusTools.getId("golden_harvester"), p -> new HoeItem(ToolMaterial.GOLD, -2, -0.5F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> GOLDEN_FISHING_ROD = item(JobsPlusTools.getId("golden_fishing_rod"), p -> new FishingRodItem(ToolMaterial.GOLD, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> GOLDEN_TROWEL = item(JobsPlusTools.getId("golden_trowel"), p -> new TrowelItem(ToolMaterial.GOLD, -2, -0.5F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> GOLDEN_WRENCH = item(JobsPlusTools.getId("golden_wrench"), p -> new WrenchItem(ToolMaterial.GOLD, -2, -0.5F, p));
    RegistrySupplier<Item> GOLDEN_WAND = item(JobsPlusTools.getId("golden_wand"), p -> new WandItem(ToolMaterial.GOLD, -2, -0.5F, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 27, false))));
    RegistrySupplier<Item> GOLDEN_EXPERIENCE_JAR = item(JobsPlusTools.getId("golden_experience_jar"), p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 4000))));
    RegistrySupplier<Item> GOLDEN_CORE = item(JobsPlusTools.getId("golden_core"), Item::new);
    RegistrySupplier<Item> GOLDEN_GRIP = item(JobsPlusTools.getId("golden_grip"), Item::new);

    // DIAMOND
    RegistrySupplier<Item> DIAMOND_LONGSWORD = item(JobsPlusTools.getId("diamond_longsword"), p -> new LongswordItem(ToolMaterial.DIAMOND, 7, -3.0F, p));
    RegistrySupplier<Item> DIAMOND_COMPOUND_BOW = item(JobsPlusTools.getId("diamond_compound_bow"), p -> new CompoundBowItem(ToolMaterial.DIAMOND, p));
    RegistrySupplier<Item> DIAMOND_EXCAVATOR = item(JobsPlusTools.getId("diamond_excavator"), p -> new ExcavatorItem(ToolMaterial.DIAMOND, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistrySupplier<Item> DIAMOND_HAMMER = item(JobsPlusTools.getId("diamond_hammer"), p -> new HammerItem(ToolMaterial.DIAMOND, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistrySupplier<Item> DIAMOND_HATCHET = item(JobsPlusTools.getId("diamond_hatchet"), p -> new AxeItem(ToolMaterial.DIAMOND, 5.0f, -3.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> DIAMOND_HARVESTER = item(JobsPlusTools.getId("diamond_harvester"), p -> new HoeItem(ToolMaterial.DIAMOND, -3, 0.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> DIAMOND_FISHING_ROD = item(JobsPlusTools.getId("diamond_fishing_rod"), p -> new FishingRodItem(ToolMaterial.DIAMOND, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> DIAMOND_TROWEL = item(JobsPlusTools.getId("diamond_trowel"), p -> new TrowelItem(ToolMaterial.DIAMOND, -3, 0.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> DIAMOND_WRENCH = item(JobsPlusTools.getId("diamond_wrench"), p -> new WrenchItem(ToolMaterial.DIAMOND, -3, 0.0F, p));
    RegistrySupplier<Item> DIAMOND_WAND = item(JobsPlusTools.getId("diamond_wand"), p -> new WandItem(ToolMaterial.DIAMOND, -3, 0.0F, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 64, false))));
    RegistrySupplier<Item> DIAMOND_EXPERIENCE_JAR = item(JobsPlusTools.getId("diamond_experience_jar"), p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 10000))));
    RegistrySupplier<Item> DIAMOND_CORE = item(JobsPlusTools.getId("diamond_core"), Item::new);
    RegistrySupplier<Item> DIAMOND_GRIP = item(JobsPlusTools.getId("diamond_grip"), Item::new);

    // NETHERITE
    RegistrySupplier<Item> NETHERITE_LONGSWORD = item(JobsPlusTools.getId("netherite_longsword"), p -> new LongswordItem(ToolMaterial.NETHERITE, 7, -3.0F, p.fireResistant()));
    RegistrySupplier<Item> NETHERITE_COMPOUND_BOW = item(JobsPlusTools.getId("netherite_compound_bow"), p -> new CompoundBowItem(ToolMaterial.NETHERITE, p.fireResistant()));
    RegistrySupplier<Item> NETHERITE_EXCAVATOR = item(JobsPlusTools.getId("netherite_excavator"), p -> new ExcavatorItem(ToolMaterial.NETHERITE, 1.5f, -3.2F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3, 4)))));
    RegistrySupplier<Item> NETHERITE_HAMMER = item(JobsPlusTools.getId("netherite_hammer"), p -> new HammerItem(ToolMaterial.NETHERITE, 1, -3.2F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3, 4)))));
    RegistrySupplier<Item> NETHERITE_HATCHET = item(JobsPlusTools.getId("netherite_hatchet"), p -> new AxeItem(ToolMaterial.NETHERITE, 5.0f, -3.0F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistrySupplier<Item> NETHERITE_HARVESTER = item(JobsPlusTools.getId("netherite_harvester"), p -> new HoeItem(ToolMaterial.NETHERITE, -4, 0.0F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> NETHERITE_FISHING_ROD = item(JobsPlusTools.getId("netherite_fishing_rod"), p -> new FishingRodItem(ToolMaterial.NETHERITE, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistrySupplier<Item> NETHERITE_TROWEL = item(JobsPlusTools.getId("netherite_trowel"), p -> new TrowelItem(ToolMaterial.NETHERITE, -4, 0.0f, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistrySupplier<Item> NETHERITE_WRENCH = item(JobsPlusTools.getId("netherite_wrench"), p -> new WrenchItem(ToolMaterial.NETHERITE, -4, 0.0f, p.fireResistant()));
    RegistrySupplier<Item> NETHERITE_WAND = item(JobsPlusTools.getId("netherite_wand"), p -> new WandItem(ToolMaterial.NETHERITE, -4, 0.0F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 128, false))));
    RegistrySupplier<Item> NETHERITE_EXPERIENCE_JAR = item(JobsPlusTools.getId("netherite_experience_jar"), p -> new Item(p.fireResistant().stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 25000))));
    RegistrySupplier<Item> NETHERITE_CORE = item(JobsPlusTools.getId("netherite_core"), p -> new Item(p.fireResistant()));

    static void init() {
    }

    static <T extends Item> RegistrySupplier<T> item(Identifier id, Function<Item.Properties, T> constructor) {
        return ITEMS.register(id, () -> constructor.apply(new Item.Properties().arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB).setId(ResourceKey.create(Registries.ITEM, id))));
    }
}
