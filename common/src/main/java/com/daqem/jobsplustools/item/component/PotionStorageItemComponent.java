package com.daqem.jobsplustools.item.component;

import java.util.function.Consumer;

import com.daqem.jobsplustools.JobsPlusTools;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipProvider;

public record PotionStorageItemComponent(PotionContents contents, int charges, int capacity, boolean isLingering) implements TooltipProvider {

    public static final Codec<PotionStorageItemComponent> CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(
            instance -> instance.group(
                    PotionContents.CODEC.fieldOf("contents").forGetter(PotionStorageItemComponent::contents),
                    Codec.INT.fieldOf("charges").forGetter(PotionStorageItemComponent::charges),
                    Codec.INT.fieldOf("capacity").forGetter(PotionStorageItemComponent::capacity),
                    Codec.BOOL.fieldOf("is_lingering").forGetter(PotionStorageItemComponent::isLingering)
            ).apply(instance, PotionStorageItemComponent::new
            )));

    public static final StreamCodec<RegistryFriendlyByteBuf, PotionStorageItemComponent> STREAM_CODEC = StreamCodec.composite(
            PotionContents.STREAM_CODEC,
            PotionStorageItemComponent::contents,
            ByteBufCodecs.INT,
            PotionStorageItemComponent::charges,
            ByteBufCodecs.INT,
            PotionStorageItemComponent::capacity,
            ByteBufCodecs.BOOL,
            PotionStorageItemComponent::isLingering,
            PotionStorageItemComponent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        PotionStorageItemComponent component = dataComponentGetter.get(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get());
        if (component != null && component.charges() > 0) {
            consumer.accept(JobsPlusTools.translatable("tooltip.wand.shoot").withStyle(ChatFormatting.GRAY));
            PotionContents.addPotionTooltip(component.contents().getAllEffects(), consumer, 1.0F, tooltipContext.tickRate());
            consumer.accept(JobsPlusTools.translatable("tooltip.potion_storage.charges", component.charges(), component.capacity()).withStyle(ChatFormatting.GRAY));
            consumer.accept(JobsPlusTools.translatable("tooltip.potion_storage.type", 
                component.isLingering() ? 
                    JobsPlusTools.translatable("tooltip.potion_storage.type.lingering") : 
                    JobsPlusTools.translatable("tooltip.potion_storage.type.splash")
            ).withStyle(ChatFormatting.GRAY));
        } else {
            consumer.accept(JobsPlusTools.translatable("tooltip.wand.use").withStyle(ChatFormatting.GRAY));
            consumer.accept(JobsPlusTools.translatable("tooltip.wand.shoot").withStyle(ChatFormatting.GRAY));
        }
    }

    public InteractionResult addPotion(ItemStack stack, PotionContents potionContents, boolean isLingering) {
        int correctCapacity = getCorrectCapacity(stack);

        if (charges >= correctCapacity) return InteractionResult.PASS;

        // If empty, initialize
        if (charges == 0) {
            stack.set(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(),
                    new PotionStorageItemComponent(potionContents, 1, correctCapacity, isLingering));
            return InteractionResult.SUCCESS;
        }

        // Check compatibility
        if (this.isLingering == isLingering && this.contents.equals(potionContents)) {
            stack.set(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(),
                    new PotionStorageItemComponent(this.contents, this.charges + 1, correctCapacity, this.isLingering)
            );
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult consumeCharge(ItemStack stack) {
        int correctCapacity = getCorrectCapacity(stack);

        if (charges > 0) {
            stack.set(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(),
                    new PotionStorageItemComponent(this.contents, this.charges - 1, correctCapacity, this.isLingering)
            );
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private int getCorrectCapacity(ItemStack stack) {
        var defaultComp = stack.getItem().getDefaultInstance().get(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get());
        return defaultComp != null ? defaultComp.capacity() : this.capacity;
    }

    public int getColorOr(int defaultColor) {
        if (contents != null) {
            return contents.getColorOr(defaultColor);
        }
        return defaultColor;
    }
}
