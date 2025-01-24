package com.daqem.jobsplustools.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum Tiers implements Tier {

    WOOD(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 131, 3.0f, 0.5f, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE(BlockTags.INCORRECT_FOR_STONE_TOOL, 511, 4.0f, 1.0f, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(BlockTags.INCORRECT_FOR_IRON_TOOL, 891, 6.0f, 2.0f, 14, () -> Ingredient.of(Items.IRON_INGOT)),
    GOLD(BlockTags.INCORRECT_FOR_GOLD_TOOL, 1271, 7.0f, 2.5f, 16, () -> Ingredient.of(Items.GOLD_INGOT)),
    DIAMOND(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1651, 8.0f, 3.0f, 10, () -> Ingredient.of(Items.DIAMOND)),
    NETHERITE(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0f, 4.0f, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    Tiers(final TagKey<Block> tagKey, final int j, final float f, final float g, final int k, final Supplier<Ingredient> supplier) {
        this.incorrectBlocksForDrops = tagKey;
        this.uses = j;
        this.speed = f;
        this.damage = g;
        this.enchantmentValue = k;
        this.repairIngredient = supplier;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
