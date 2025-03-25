package com.daqem.jobsplustools.client.item;

import com.daqem.jobsplustools.item.JobsPlusToolsItems;
import com.daqem.jobsplustools.mixin.client.MixinItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class JobsPlusToolsItemProperties {

    public static void init() {
        initBow(JobsPlusToolsItems.WOODEN_COMPOUND_BOW.get());
        initBow(JobsPlusToolsItems.STONE_COMPOUND_BOW.get());
        initBow(JobsPlusToolsItems.IRON_COMPOUND_BOW.get());
        initBow(JobsPlusToolsItems.GOLDEN_COMPOUND_BOW.get());
        initBow(JobsPlusToolsItems.DIAMOND_COMPOUND_BOW.get());
        initBow(JobsPlusToolsItems.NETHERITE_COMPOUND_BOW.get());
    }

    private static void initBow(Item item) {
        MixinItemProperties.invokeRegister(item, ResourceLocation.parse("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return (float) (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        MixinItemProperties.invokeRegister(item, ResourceLocation.parse("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
    }
}
