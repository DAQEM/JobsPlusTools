package com.daqem.jobsplustools.item.component;

import com.daqem.jobsplustools.JobsPlusTools;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import static com.daqem.jobsplustools.JobsPlusTools.MANAGER;

public interface JobsPlusToolsDataComponentTypes {

    Registrar<DataComponentType<?>> COMPONENTS = MANAGER.get().get(Registries.DATA_COMPONENT_TYPE);

    RegistrySupplier<DataComponentType<ModeItemComponent>> MODE_ITEM_COMPONENT = register("mode", ModeItemComponent.CODEC, ModeItemComponent.STREAM_CODEC);

    static void init() {
    }

    static <T> RegistrySupplier<DataComponentType<T>> register(String id, Codec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        return COMPONENTS.register(
                JobsPlusTools.getId(id),
                () -> DataComponentType.<T>builder()
                        .persistent(codec)
                        .networkSynchronized(streamCodec)
                        .build()
        );
    }
}
