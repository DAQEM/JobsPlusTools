package com.daqem.jobsplustools.item.mode;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ModeItem extends ItemLike {

    default @NotNull IMode getActiveMode(ItemStack stack) {
        return ModeItem.ModeItemSerializer.deserialize(stack.getOrCreateTag(), this);
    }

    default void setActiveMode(@NotNull ItemStack stack, @NotNull IMode mode) {
        ModeItem.ModeItemSerializer.serialize(stack.getOrCreateTag(), mode, this);
    }
    List<IMode> getAvailableModes();

    default IMode getDefaultMode() {
        return getAvailableModes().get(0);
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

    class ModeItemSerializer {

        public static final String MODE_TAG = "Mode";

        public static void serialize(CompoundTag tag, IMode mode, ModeItem item) {
            tag.putInt(MODE_TAG, item.getAvailableModes().indexOf(mode));
        }

        public static IMode deserialize(CompoundTag tag, ModeItem item) {
            return item.getAvailableModes().get(tag.getInt(MODE_TAG));
        }
    }
}
