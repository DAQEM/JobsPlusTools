package com.daqem.jobsplustools.item.component;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.IModeType;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public record ModeItemComponent(
        Identifier modeType,
        int selectedMode,
        List<Integer> availableModes
) implements TooltipProvider {

    public static final Codec<ModeItemComponent> CODEC = Codec.lazyInitialized(() ->
            RecordCodecBuilder.create(instance -> instance.group(
                    Identifier.CODEC.fieldOf("type").forGetter(ModeItemComponent::modeType),
                    Codec.INT.fieldOf("selected").forGetter(ModeItemComponent::selectedMode),
                    Codec.list(Codec.INT).fieldOf("available").forGetter(ModeItemComponent::availableModes)
            ).apply(instance, ModeItemComponent::new))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ModeItemComponent> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            ModeItemComponent::modeType,
            ByteBufCodecs.INT,
            ModeItemComponent::selectedMode,
            ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
            ModeItemComponent::availableModes,
            ModeItemComponent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        ModeItemComponent modeItemComponent = dataComponentGetter.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
        if (modeItemComponent != null) {
            IModeType modeType = modeItemComponent.getModeType();
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
                    consumer.accept(JobsPlusTools.API.translatable("tooltip.no_modes").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                    return;
                }
                MutableComponent modes = JobsPlusTools.API.literal("").copy();
                for (IMode mode : availableModes) {
                    MutableComponent component = mode.getName().copy();

                    int selectedIndex = modeItemComponent.selectedMode();
                    if (selectedIndex >= 0 && selectedIndex < allModes.length && mode.equals(allModes[selectedIndex])) {
                        component.setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
                    } else {
                        component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
                    }

                    if (availableModes.indexOf(mode) != availableModes.size() - 1) {
                        component.append(JobsPlusTools.API.literal(", ").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                    }
                    modes.append(component);
                }
                consumer.accept(JobsPlusTools.API.translatable("tooltip.modes", modes).copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                consumer.accept(JobsPlusTools.API.translatable("tooltip.switch_mode").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
            }
        }
    }

    public boolean switchMode(ItemStack itemStack) {
        ItemStack defaultInstance = itemStack.getItem().getDefaultInstance();
        ModeItemComponent defaultComponent = defaultInstance.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());

        List<Integer> correctModes = (defaultComponent != null) ? defaultComponent.availableModes() : this.availableModes();

        if (correctModes.size() <= 1) {
            return false;
        }

        itemStack.update(
                JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(),
                this,
                x -> {
                    int nextIndex = correctModes.indexOf(x.selectedMode()) + 1;
                    if (nextIndex >= correctModes.size()) {
                        nextIndex = 0;
                    }

                    return new ModeItemComponent(
                            x.modeType(),
                            correctModes.get(nextIndex),
                            correctModes
                    );
                }
        );
        return true;
    }

    public IModeType getModeType() {
        Set<Identifier> locations = IModeType.MODE_TYPES.keySet();
        if (locations.contains(this.modeType())) {
            return IModeType.MODE_TYPES.get(this.modeType());
        }
        throw new IllegalStateException("Invalid mode type: " + this.modeType());
    }
}