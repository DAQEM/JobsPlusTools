package com.daqem.jobsplustools.entity;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public interface JobsPlusToolsEntityTypes {

    Registry<EntityType<?>> ENTITY_TYPES = Knot.REGISTRAR.createRegistry(BuiltInRegistries.ENTITY_TYPE, JobsPlusTools.MOD_ID);

    RegistryEntry<EntityType<JobsPlusToolsFishingHook>> FISHING_HOOK = entityType(
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

    static <T extends Entity> RegistryEntry<EntityType<T>> entityType(String id, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(id, builder::build);
    }
}
