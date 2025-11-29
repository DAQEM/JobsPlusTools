package com.daqem.jobsplustools.item.mode;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.component.JobsPlusDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ModeItem extends ItemLike {

    default @NotNull IMode getActiveMode(ItemStack stack) {
        return ModeItem.ModeItemSerializer.deserialize(stack, this);
    }

    default void setActiveMode(@NotNull ItemStack stack, @NotNull IMode mode) {
        ModeItem.ModeItemSerializer.serialize(stack, mode, this);
    }
    List<IMode> getAvailableModes();

    default IMode getDefaultMode() {
        return getAvailableModes().getFirst();
    }

    default IMode getNextMode(ItemStack stack) {
        List<IMode> availableModes = getAvailableModes();
        int index = availableModes.indexOf(getActiveMode(stack));
        if (index == availableModes.size() - 1) {
            return getDefaultMode();
        }
        return availableModes.get(index + 1);
    }

    default void switchMode(ServerPlayer player, ItemStack stack) {
        IMode nextMode = getNextMode(stack);
        setActiveMode(stack, nextMode);
        player.sendSystemMessage(nextMode.getName().copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), true);
    }

    default List<Component> getModesTooltip(ItemStack stack) {
        List<IMode> availableModes = getAvailableModes();
        if (availableModes.isEmpty()) return List.of(JobsPlusTools.translatable("tooltip.no_modes").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        MutableComponent modes = JobsPlusTools.literal("").copy();
        for (IMode mode : availableModes) {
            MutableComponent component = mode.getName().copy();

            if (mode.equals(getActiveMode(stack))) {
                component.setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
            } else {
                component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
            }

            if (availableModes.indexOf(mode) != availableModes.size() - 1) {
                component.append(JobsPlusTools.literal(", ").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
            }
            modes.append(component);
        }
        return List.of(JobsPlusTools.translatable("tooltip.modes", modes).copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)),
                JobsPlusTools.translatable("tooltip.switch_mode").copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }

    class ModeItemSerializer {

        public static void serialize(@NotNull ItemStack stack, IMode mode, ModeItem item) {
            stack.set(JobsPlusDataComponentTypes.MODE_ITEM_COMPONENT.get(), new ModeItemComponent(item.getAvailableModes().indexOf(mode)));
        }

        public static IMode deserialize(ItemStack stack, ModeItem item) {
            if (!stack.has(JobsPlusDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
                return item.getDefaultMode();
            }
            return item.getAvailableModes().get(stack.get(JobsPlusDataComponentTypes.MODE_ITEM_COMPONENT.get()).mode());
        }
    }
}
