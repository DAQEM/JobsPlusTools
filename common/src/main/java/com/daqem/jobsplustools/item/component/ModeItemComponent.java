package com.daqem.jobsplustools.item.component;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.IModeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record ModeItemComponent(ResourceLocation modeType, int selectedMode,
                                List<Integer> availableModes) implements TooltipProvider {

    public static final Codec<ModeItemComponent> CODEC = Codec.lazyInitialized(() ->
            RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("type").forGetter(ModeItemComponent::modeType),
                    Codec.INT.fieldOf("selected").forGetter(ModeItemComponent::selectedMode),
                    Codec.list(Codec.INT).fieldOf("available").forGetter(ModeItemComponent::availableModes)
            ).apply(instance, ModeItemComponent::new))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ModeItemComponent> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            ModeItemComponent::modeType,
            ByteBufCodecs.INT,
            ModeItemComponent::selectedMode,
            ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
            ModeItemComponent::availableModes,
            ModeItemComponent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        ModeItemComponent modeItemComponent = dataComponentGetter.get(JobsPlusDataComponentTypes.MODE_ITEM_COMPONENT.get());
        if (modeItemComponent != null) {
            ResourceLocation modeTypeLocation = modeItemComponent.modeType();
            IModeType modeType = IModeType.MODE_TYPES.get(modeTypeLocation);
            if (modeType != null) {
                Class<? extends IMode> modeClass = modeType.getModeClass();
                IMode[] allModes = modeClass.getEnumConstants();
                List<Integer> availableModesInts = modeItemComponent.availableModes();
                List<IMode> availableModes = new ArrayList<>();
                for (int i = 0; i < allModes.length; i++) {
                    if (availableModesInts.contains(i)) {
                        availableModes.add(allModes[i]);
                    }
                }

                if (availableModes.isEmpty()) {
                    consumer.accept(JobsPlusTools.translatable("tooltip.no_modes").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                    return;
                }
                MutableComponent modes = JobsPlusTools.literal("").copy();
                for (IMode mode : availableModes) {
                    MutableComponent component = mode.getName().copy();

                    if (mode.equals(allModes[modeItemComponent.selectedMode()])) {
                        component.setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
                    } else {
                        component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
                    }

                    if (availableModes.indexOf(mode) != availableModes.size() - 1) {
                        component.append(JobsPlusTools.literal(", ").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                    }
                    modes.append(component);
                }
                consumer.accept(JobsPlusTools.translatable("tooltip.modes", modes).copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                consumer.accept(JobsPlusTools.translatable("tooltip.switch_mode").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
            }
        }
    }
}
