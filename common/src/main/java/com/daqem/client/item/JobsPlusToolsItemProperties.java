package com.daqem.client.item;

import com.daqem.jobsplustools.item.JobsPlusToolsItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class JobsPlusToolsItemProperties {

    public static void init() {
//        initBow(JobsPlusToolsItems.WOODEN_COMPOUND_BOW.get());
//        initBow(JobsPlusToolsItems.STONE_COMPOUND_BOW.get());
//        initBow(JobsPlusToolsItems.IRON_COMPOUND_BOW.get());
//        initBow(JobsPlusToolsItems.GOLDEN_COMPOUND_BOW.get());
//        initBow(JobsPlusToolsItems.DIAMOND_COMPOUND_BOW.get());
//        initBow(JobsPlusToolsItems.NETHERITE_COMPOUND_BOW.get());
    }

    private static void initBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174620_, p_174621_, p_174622_, p_174623_) -> {
            if (p_174622_ == null) {
                return 0.0F;
            } else {
                return (float) (p_174620_.getUseDuration() - p_174622_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
    }
}
