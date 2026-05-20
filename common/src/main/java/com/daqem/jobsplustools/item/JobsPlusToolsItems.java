package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.component.ExperienceItemComponent;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.component.PotionStorageItemComponent;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.registry.creativetab.ItemPropertiesExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;
import java.util.function.Function;

public interface JobsPlusToolsItems {

    Registry<Item> ITEMS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.ITEM, JobsPlusTools.MOD_ID);

    // WOODEN
    RegistryEntry<Item> WOODEN_LONGSWORD = item("wooden_longsword", p -> new LongswordItem(ToolMaterial.WOOD, 5, -3.2F, p));
    RegistryEntry<Item> WOODEN_COMPOUND_BOW = item("wooden_compound_bow", p -> new CompoundBowItem(ToolMaterial.WOOD, p));
    RegistryEntry<Item> WOODEN_EXCAVATOR = item("wooden_excavator", p -> new ExcavatorItem(ToolMaterial.WOOD, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> WOODEN_HAMMER = item("wooden_hammer", p -> new HammerItem(ToolMaterial.WOOD, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> WOODEN_HATCHET = item("wooden_hatchet", p -> new AxeItem(ToolMaterial.WOOD, 6.0f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> WOODEN_HARVESTER = item("wooden_harvester", p -> new HarvesterItem(ToolMaterial.WOOD, 0, -3.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0)))));
    RegistryEntry<Item> WOODEN_TROWEL = item("wooden_trowel", p -> new TrowelItem(ToolMaterial.WOOD, 0, -3.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0)))));
    RegistryEntry<Item> WOODEN_WRENCH = item("wooden_wrench", p -> new WrenchItem(ToolMaterial.WOOD, 0, -3.0F, p));
    RegistryEntry<Item> WOODEN_WAND = item("wooden_wand", p -> new WandItem(ToolMaterial.WOOD, 0, -3.0F, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 3, false))));
    RegistryEntry<Item> WOODEN_EXPERIENCE_JAR = item("wooden_experience_jar", p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 500))));
    RegistryEntry<Item> WOODEN_CORE = item("wooden_core", Item::new);
    RegistryEntry<Item> WOODEN_GRIP = item("wooden_grip", Item::new);

    // STONE
    RegistryEntry<Item> STONE_LONGSWORD = item("stone_longsword", p -> new LongswordItem(ToolMaterial.STONE, 5, -3.2F, p));
    RegistryEntry<Item> STONE_COMPOUND_BOW = item("stone_compound_bow", p -> new CompoundBowItem(ToolMaterial.STONE, p));
    RegistryEntry<Item> STONE_EXCAVATOR = item("stone_excavator", p -> new ExcavatorItem(ToolMaterial.STONE, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> STONE_HAMMER = item("stone_hammer", p -> new HammerItem(ToolMaterial.STONE, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> STONE_HATCHET = item("stone_hatchet", p -> new AxeItem(ToolMaterial.STONE, 7.0f, -3.2f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> STONE_HARVESTER = item("stone_harvester", p -> new HarvesterItem(ToolMaterial.STONE, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0)))));
    RegistryEntry<Item> STONE_FISHING_ROD = item("stone_fishing_rod", p -> new FishingRodItem(ToolMaterial.STONE, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> STONE_TROWEL = item("stone_trowel", p -> new TrowelItem(ToolMaterial.STONE, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0)))));
    RegistryEntry<Item> STONE_WRENCH = item("stone_wrench", p -> new WrenchItem(ToolMaterial.STONE, -1, -2.0f, p));
    RegistryEntry<Item> STONE_WAND = item("stone_wand", p -> new WandItem(ToolMaterial.STONE, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 9, false))));
    RegistryEntry<Item> STONE_EXPERIENCE_JAR = item("stone_experience_jar", p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 1000))));
    RegistryEntry<Item> STONE_CORE = item("stone_core", Item::new);
    RegistryEntry<Item> STONE_GRIP = item("stone_grip", Item::new);

    // COPPER
    RegistryEntry<Item> COPPER_LONGSWORD = item("copper_longsword", p -> new LongswordItem(ToolMaterial.COPPER, 5, -3.2F, p));
    RegistryEntry<Item> COPPER_COMPOUND_BOW = item("copper_compound_bow", p -> new CompoundBowItem(ToolMaterial.COPPER, p));
    RegistryEntry<Item> COPPER_EXCAVATOR = item("copper_excavator", p -> new ExcavatorItem(ToolMaterial.COPPER, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> COPPER_HAMMER = item("copper_hammer", p -> new HammerItem(ToolMaterial.COPPER, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> COPPER_HATCHET = item("copper_hatchet", p -> new AxeItem(ToolMaterial.COPPER, 7.0f, -3.2f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> COPPER_HARVESTER = item("copper_harvester", p -> new HarvesterItem(ToolMaterial.COPPER, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0)))));
    RegistryEntry<Item> COPPER_FISHING_ROD = item("copper_fishing_rod", p -> new FishingRodItem(ToolMaterial.COPPER, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> COPPER_TROWEL = item("copper_trowel", p -> new TrowelItem(ToolMaterial.COPPER, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> COPPER_WRENCH = item("copper_wrench", p -> new WrenchItem(ToolMaterial.COPPER, -1, -2.0f, p));
    RegistryEntry<Item> COPPER_WAND = item("copper_wand", p -> new WandItem(ToolMaterial.COPPER, -1, -2.0f, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 9, false))));
    RegistryEntry<Item> COPPER_EXPERIENCE_JAR = item("copper_experience_jar", p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 1500))));
    RegistryEntry<Item> COPPER_CORE = item("copper_core", Item::new);
    RegistryEntry<Item> COPPER_GRIP = item("copper_grip", Item::new);

    // IRON
    RegistryEntry<Item> IRON_LONGSWORD = item("iron_longsword", p -> new LongswordItem(ToolMaterial.IRON, 6, -3.1F, p));
    RegistryEntry<Item> IRON_COMPOUND_BOW = item("iron_compound_bow", p -> new CompoundBowItem(ToolMaterial.IRON, p));
    RegistryEntry<Item> IRON_EXCAVATOR = item("iron_excavator", p -> new ExcavatorItem(ToolMaterial.IRON, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> IRON_HAMMER = item("iron_hammer", p -> new HammerItem(ToolMaterial.IRON, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> IRON_HATCHET = item("iron_hatchet", p -> new AxeItem(ToolMaterial.IRON, 6.0f, -3.1f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> IRON_HARVESTER = item("iron_harvester", p -> new HarvesterItem(ToolMaterial.IRON, -2, -1.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> IRON_FISHING_ROD = item("iron_fishing_rod", p -> new FishingRodItem(ToolMaterial.IRON, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> IRON_TROWEL = item("iron_trowel", p -> new TrowelItem(ToolMaterial.IRON, -2, -1.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> IRON_WRENCH = item("iron_wrench", p -> new WrenchItem(ToolMaterial.IRON, -2, -1.0f, p));
    RegistryEntry<Item> IRON_WAND = item("iron_wand", p -> new WandItem(ToolMaterial.IRON, -2, -1.0f, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 27, false))));
    RegistryEntry<Item> IRON_EXPERIENCE_JAR = item("iron_experience_jar", p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 2500))));
    RegistryEntry<Item> IRON_CORE = item("iron_core", Item::new);
    RegistryEntry<Item> IRON_GRIP = item("iron_grip", Item::new);

    // GOLDEN
    RegistryEntry<Item> GOLDEN_LONGSWORD = item("golden_longsword", p -> new LongswordItem(ToolMaterial.GOLD, 6, -3.0F, p));
    RegistryEntry<Item> GOLDEN_COMPOUND_BOW = item("golden_compound_bow", p -> new CompoundBowItem(ToolMaterial.GOLD, p));
    RegistryEntry<Item> GOLDEN_EXCAVATOR = item("golden_excavator", p -> new ExcavatorItem(ToolMaterial.GOLD, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistryEntry<Item> GOLDEN_HAMMER = item("golden_hammer", p -> new HammerItem(ToolMaterial.GOLD, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistryEntry<Item> GOLDEN_HATCHET = item("golden_hatchet", p -> new AxeItem(ToolMaterial.GOLD, 5.5f, -3.1F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> GOLDEN_HARVESTER = item("golden_harvester", p -> new HarvesterItem(ToolMaterial.GOLD, -2, -0.5F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> GOLDEN_FISHING_ROD = item("golden_fishing_rod", p -> new FishingRodItem(ToolMaterial.GOLD, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> GOLDEN_TROWEL = item("golden_trowel", p -> new TrowelItem(ToolMaterial.GOLD, -2, -0.5F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> GOLDEN_WRENCH = item("golden_wrench", p -> new WrenchItem(ToolMaterial.GOLD, -2, -0.5F, p));
    RegistryEntry<Item> GOLDEN_WAND = item("golden_wand", p -> new WandItem(ToolMaterial.GOLD, -2, -0.5F, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 27, false))));
    RegistryEntry<Item> GOLDEN_EXPERIENCE_JAR = item("golden_experience_jar", p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 4000))));
    RegistryEntry<Item> GOLDEN_CORE = item("golden_core", Item::new);
    RegistryEntry<Item> GOLDEN_GRIP = item("golden_grip", Item::new);

    // DIAMOND
    RegistryEntry<Item> DIAMOND_LONGSWORD = item("diamond_longsword", p -> new LongswordItem(ToolMaterial.DIAMOND, 7, -3.0F, p));
    RegistryEntry<Item> DIAMOND_COMPOUND_BOW = item("diamond_compound_bow", p -> new CompoundBowItem(ToolMaterial.DIAMOND, p));
    RegistryEntry<Item> DIAMOND_EXCAVATOR = item("diamond_excavator", p -> new ExcavatorItem(ToolMaterial.DIAMOND, 1.5f, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistryEntry<Item> DIAMOND_HAMMER = item("diamond_hammer", p -> new HammerItem(ToolMaterial.DIAMOND, 1, -3.2F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistryEntry<Item> DIAMOND_HATCHET = item("diamond_hatchet", p -> new AxeItem(ToolMaterial.DIAMOND, 5.0f, -3.0f, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> DIAMOND_HARVESTER = item("diamond_harvester", p -> new HarvesterItem(ToolMaterial.DIAMOND, -3, 0.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> DIAMOND_FISHING_ROD = item("diamond_fishing_rod", p -> new FishingRodItem(ToolMaterial.DIAMOND, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> DIAMOND_TROWEL = item("diamond_trowel", p -> new TrowelItem(ToolMaterial.DIAMOND, -3, 0.0F, p.component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> DIAMOND_WRENCH = item("diamond_wrench", p -> new WrenchItem(ToolMaterial.DIAMOND, -3, 0.0F, p));
    RegistryEntry<Item> DIAMOND_WAND = item("diamond_wand", p -> new WandItem(ToolMaterial.DIAMOND, -3, 0.0F, p.component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 64, false))));
    RegistryEntry<Item> DIAMOND_EXPERIENCE_JAR = item("diamond_experience_jar", p -> new Item(p.stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 10000))));
    RegistryEntry<Item> DIAMOND_CORE = item("diamond_core", Item::new);
    RegistryEntry<Item> DIAMOND_GRIP = item("diamond_grip", Item::new);

    // NETHERITE
    RegistryEntry<Item> NETHERITE_LONGSWORD = item("netherite_longsword", p -> new LongswordItem(ToolMaterial.NETHERITE, 7, -3.0F, p.fireResistant()));
    RegistryEntry<Item> NETHERITE_COMPOUND_BOW = item("netherite_compound_bow", p -> new CompoundBowItem(ToolMaterial.NETHERITE, p.fireResistant()));
    RegistryEntry<Item> NETHERITE_EXCAVATOR = item("netherite_excavator", p -> new ExcavatorItem(ToolMaterial.NETHERITE, 1.5f, -3.2F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3, 4)))));
    RegistryEntry<Item> NETHERITE_HAMMER = item("netherite_hammer", p -> new HammerItem(ToolMaterial.NETHERITE, 1, -3.2F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_BREAKER_MODE.getId(), 0, List.of(0, 1, 2, 3, 4)))));
    RegistryEntry<Item> NETHERITE_HATCHET = item("netherite_hatchet", p -> new AxeItem(ToolMaterial.NETHERITE, 5.0f, -3.0F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.TREE_BREAKER_MODE.getId(), 0, List.of(0, 1)))));
    RegistryEntry<Item> NETHERITE_HARVESTER = item("netherite_harvester", p -> new HarvesterItem(ToolMaterial.NETHERITE, -4, 0.0F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.CROP_REPLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> NETHERITE_FISHING_ROD = item("netherite_fishing_rod", p -> new FishingRodItem(ToolMaterial.NETHERITE, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BOBBER_MODE.getId(), 0, List.of(0, 1, 2, 3)))));
    RegistryEntry<Item> NETHERITE_TROWEL = item("netherite_trowel", p -> new TrowelItem(ToolMaterial.NETHERITE, -4, 0.0f, p.fireResistant().component(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(IModeType.MULTI_BLOCK_PLACER_MODE.getId(), 0, List.of(0, 1, 2)))));
    RegistryEntry<Item> NETHERITE_WRENCH = item("netherite_wrench", p -> new WrenchItem(ToolMaterial.NETHERITE, -4, 0.0f, p.fireResistant()));
    RegistryEntry<Item> NETHERITE_WAND = item("netherite_wand", p -> new WandItem(ToolMaterial.NETHERITE, -4, 0.0F, p.fireResistant().component(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), new PotionStorageItemComponent(PotionContents.EMPTY, 0, 128, false))));
    RegistryEntry<Item> NETHERITE_EXPERIENCE_JAR = item("netherite_experience_jar", p -> new Item(p.fireResistant().stacksTo(1).component(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), new ExperienceItemComponent(0, 25000))));
    RegistryEntry<Item> NETHERITE_CORE = item("netherite_core", p -> new Item(p.fireResistant()));

    static <T extends Item> RegistryEntry<T> item(String id, Function<Item.Properties, T> constructor) {
        return ITEMS.register(id, (key) -> constructor.apply(((ItemPropertiesExtension) new Item.Properties().setId(key)).knot$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB.getKey())));
    }
}
