package com.daqem.jobsplustools.item.component;

import com.daqem.jobsplustools.JobsPlusTools;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record ExperienceItemComponent(int experience, int capacity) implements TooltipProvider {

    public static final Codec<ExperienceItemComponent> CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("experience").forGetter(ExperienceItemComponent::experience),
                    Codec.INT.fieldOf("capacity").forGetter(ExperienceItemComponent::capacity)
            ).apply(instance, ExperienceItemComponent::new
    )));

    public static final StreamCodec<RegistryFriendlyByteBuf, ExperienceItemComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ExperienceItemComponent::experience,
            ByteBufCodecs.INT,
            ExperienceItemComponent::capacity,
            ExperienceItemComponent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        ExperienceItemComponent component = dataComponentGetter.get(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get());
        if (component != null) {
            consumer.accept(JobsPlusTools.translatable("tooltip.experience", component.experience(), component.capacity()).withStyle(ChatFormatting.GRAY));
            consumer.accept(JobsPlusTools.translatable("tooltip.experience.usage.insert").withStyle(ChatFormatting.GRAY));
            consumer.accept(JobsPlusTools.translatable("tooltip.experience.usage.extract").withStyle(ChatFormatting.GRAY));
        }
    }

    public void extractExperience(ItemStack itemStack, int experienceToExtract) {
        int newExperience = Math.max(0, this.experience - experienceToExtract);
        itemStack.update(
                JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(),
                this,
                component -> new ExperienceItemComponent(newExperience, this.capacity)
        );
    }

    public void insertExperience(ItemStack itemStack, int experienceToInsert) {
        int newExperience = Math.min(this.capacity, this.experience + experienceToInsert);
        itemStack.update(
                JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(),
                this,
                component -> new ExperienceItemComponent(newExperience, this.capacity)
        );
    }
}
