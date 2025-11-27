package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class CompoundBowItem extends BowItem {

    private final ToolMaterial toolMaterial;

    public CompoundBowItem(ToolMaterial toolMaterial, Properties properties) {
        super(properties.durability(toolMaterial.durability()).enchantable(1));
        this.toolMaterial = toolMaterial;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        consumer.accept(JobsPlusTools.translatable("tooltip.bonus_damage", getBonusDamage()).copy().withStyle(JobsPlusTools.ITEM_TOOLTIP_STYLE));
    }

    public double getBonusDamage() {
        return toolMaterial.attackDamageBonus() * 0.5 + 1.0;
    }
}