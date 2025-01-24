package com.daqem.jobsplustools.component;

import com.daqem.jobsplustools.JobsPlusTools;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import static com.daqem.jobsplustools.JobsPlusTools.MANAGER;

public class ModDataComponentTypes {

    public static final Registrar<DataComponentType<?>> COMPONENTS = MANAGER.get().get(Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<ModeItemComponent>> MODE_ITEM_COMPONENT = COMPONENTS.register(JobsPlusTools.getId("lasso_data"), () -> ((DataComponentType.Builder) DataComponentType.builder()).persistent(ModeItemComponent.CODEC).networkSynchronized(ModeItemComponent.STREAM_CODEC).build());

    public static void init() {
    }
}
