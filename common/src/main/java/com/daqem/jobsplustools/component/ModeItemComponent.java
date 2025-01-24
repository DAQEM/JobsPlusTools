package com.daqem.jobsplustools.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ModeItemComponent(int mode) {

    public static final Codec<ModeItemComponent> CODEC = Codec.lazyInitialized(() ->
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("mobName").forGetter(ModeItemComponent::mode)
            ).apply(instance, ModeItemComponent::new))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ModeItemComponent> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull ModeItemComponent decode(RegistryFriendlyByteBuf buf) {
            return new ModeItemComponent(buf.readInt());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, @Nullable ModeItemComponent packet) {
            buf.writeInt(packet.mode());
        }
    };
}
