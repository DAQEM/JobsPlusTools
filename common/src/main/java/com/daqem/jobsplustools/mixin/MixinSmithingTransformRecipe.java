package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.item.component.ExperienceItemComponent;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.component.PotionStorageItemComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public class MixinSmithingTransformRecipe {

    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/SmithingRecipeInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At("RETURN"), cancellable = true)
    private void jobsplustools$assemble(SmithingRecipeInput input, HolderLookup.Provider provider, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack result = cir.getReturnValue();

        if (!result.isEmpty()) {
            ItemStack defaultStack = result.getItem().getDefaultInstance();

            // 1. Fix Modes (adds 5x5x5, etc.)
            if (result.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
                ModeItemComponent defaultMode = defaultStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
                if (defaultMode != null) {
                    result.update(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), defaultMode, current ->
                            new ModeItemComponent(
                                    current.modeType(),
                                    current.selectedMode(),
                                    defaultMode.availableModes() // Use the new tier's available modes
                            )
                    );
                }
            }

            // 2. Fix Experience Jar Capacity (e.g. Diamond 10k -> Netherite 25k)
            if (result.has(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get())) {
                ExperienceItemComponent defaultExp = defaultStack.get(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get());
                if (defaultExp != null) {
                    result.update(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), defaultExp, current ->
                            new ExperienceItemComponent(
                                    current.experience(),
                                    defaultExp.capacity() // Use the new tier's capacity
                            )
                    );
                }
            }

            // 3. Fix Wand Potion Capacity
            if (result.has(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get())) {
                PotionStorageItemComponent defaultPotion = defaultStack.get(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get());
                if (defaultPotion != null) {
                    result.update(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), defaultPotion, current ->
                            new PotionStorageItemComponent(
                                    current.contents(),
                                    current.charges(),
                                    defaultPotion.capacity(), // Use the new tier's capacity
                                    current.isLingering()
                            )
                    );
                }
            }
        }
    }
}