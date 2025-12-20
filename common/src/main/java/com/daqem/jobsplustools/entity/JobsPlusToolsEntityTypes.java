package com.daqem.jobsplustools.entity;

import com.daqem.jobsplustools.JobsPlusTools;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public interface JobsPlusToolsEntityTypes {

    Registrar<EntityType<?>> ENTITY_TYPES = JobsPlusTools.MANAGER.get().get(Registries.ENTITY_TYPE);

    RegistrySupplier<EntityType<JobsPlusToolsFishingHook>> FISHING_HOOK = entityType(
            "fishing_hook",
            EntityType.Builder.<JobsPlusToolsFishingHook>of(
                            JobsPlusToolsFishingHook::new,
                            MobCategory.MISC
                    )
                    .noLootTable()
                    .noSave()
                    .noSummon()
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(5)
    );

    static void init() {
    }

    static <T extends Entity> RegistrySupplier<EntityType<T>> entityType(String name, EntityType.Builder<T> builder) {
        Identifier id = JobsPlusTools.getId(name);
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        return ENTITY_TYPES.register(id, () -> builder.build(key));
    }
}
