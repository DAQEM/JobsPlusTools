package com.daqem.jobsplustools.client.item;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.PotionStorageItemComponent;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WandPotionTintProperty implements ItemTintSource {

    public static final MapCodec<WandPotionTintProperty> TYPE = MapCodec.unit(new WandPotionTintProperty());
    public static final int DEFAULT_COLOR = 0x00000000;


    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        PotionStorageItemComponent component = itemStack.get(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get());
        return (component != null && component.charges() > 0) ? ARGB.opaque(component.getColorOr(DEFAULT_COLOR)) : DEFAULT_COLOR;
    }

    @Override
    public @NotNull MapCodec<? extends ItemTintSource> type() {
        return TYPE;
    }
}
