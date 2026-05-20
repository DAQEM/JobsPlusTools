package com.daqem.jobsplustools.item.component;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface JobsPlusToolsDataComponentTypes {

    Registry<DataComponentType<?>> COMPONENTS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.DATA_COMPONENT_TYPE, JobsPlusTools.MOD_ID);

    RegistryEntry<DataComponentType<ModeItemComponent>> MODE_ITEM_COMPONENT = register("mode", ModeItemComponent.CODEC, ModeItemComponent.STREAM_CODEC);
    RegistryEntry<DataComponentType<ExperienceItemComponent>> EXPERIENCE_ITEM_COMPONENT = register("experience", ExperienceItemComponent.CODEC, ExperienceItemComponent.STREAM_CODEC);
    RegistryEntry<DataComponentType<PotionStorageItemComponent>> POTION_STORAGE_ITEM_COMPONENT = register("potion_storage", PotionStorageItemComponent.CODEC, PotionStorageItemComponent.STREAM_CODEC);

    static void init() {
    }

    static <T> RegistryEntry<DataComponentType<T>> register(String id, Codec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        return COMPONENTS.register(
                id,
                () -> DataComponentType.<T>builder()
                        .persistent(codec)
                        .networkSynchronized(streamCodec)
                        .build()
        );
    }
}
