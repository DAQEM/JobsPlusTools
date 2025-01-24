package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompoundBowItem extends BowItem {

    private final Tier tier;

    public CompoundBowItem(Tier tier, Properties properties) {
        //noinspection UnstableApiUsage
        super(properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB).durability(tier.getUses()));
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        list.add(JobsPlusTools.translatable("tooltip.bonus_damage", getBonusDamage()).copy().withStyle(JobsPlusTools.ITEM_TOOLTIP_STYLE));
    }

    public double getBonusDamage() {
        return tier.getAttackDamageBonus();
    }

    @Override
    public boolean isValidRepairItem(ItemStack leftItem, ItemStack rightItem) {
        return tier.getRepairIngredient().test(rightItem) || super.isValidRepairItem(leftItem, rightItem);
    }
}
