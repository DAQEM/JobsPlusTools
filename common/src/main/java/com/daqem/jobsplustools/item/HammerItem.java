package com.daqem.jobsplustools.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class HammerItem extends Item {

    public HammerItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.pickaxe(toolMaterial, attackDamage, attackSpeed));
    }
}
