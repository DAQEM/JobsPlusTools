package com.daqem.jobsplustools.client.item;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExperienceJarExperienceProperty implements RangeSelectItemModelProperty {

    public static final MapCodec<ExperienceJarExperienceProperty> TYPE = MapCodec.unit(new ExperienceJarExperienceProperty());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner itemOwner, int i) {
        var experienceComponent = itemStack.get(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get());
        if (experienceComponent != null) {
            int currentExperience = experienceComponent.experience();
            int capacity = experienceComponent.capacity();
            if (capacity != 0) {
                return (float) currentExperience / (float) capacity;
            }
        }
        return 0.0f;
    }

    @Override
    public @NotNull MapCodec<? extends RangeSelectItemModelProperty> type() {
        return TYPE;
    }
}
