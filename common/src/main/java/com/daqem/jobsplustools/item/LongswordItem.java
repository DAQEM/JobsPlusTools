package com.daqem.jobsplustools.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class LongswordItem extends Item {

    public LongswordItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.sword(toolMaterial, attackDamage, attackSpeed));
    }
}