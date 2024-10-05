package com.leo.soulmancy.data;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.client.ModClientData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PlayerData implements CustomPacketPayload {

    public static final Type<PlayerData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "player_data"));

    public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(
        inst -> inst.group(
            Codec.BOOL.fieldOf("eyeEquipped").forGetter(d -> d.eyeEquipped),
            Codec.BOOL.fieldOf("pureVision").forGetter(d -> d.pureVision)
        ).apply(inst, PlayerData::new)
    );

    public static final StreamCodec<ByteBuf, PlayerData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        PlayerData::eyeEquipped,
        ByteBufCodecs.BOOL,
        PlayerData::pureVision,
        PlayerData::new
    );

    public boolean eyeEquipped;
    public boolean pureVision;

    public PlayerData(boolean eyeEquipped, boolean pureVision) {
        this.eyeEquipped = eyeEquipped;
        this.pureVision = pureVision;
    }

    public PlayerData() {
        this(false, false);
    }

    boolean eyeEquipped(){
        return eyeEquipped;
    }

    boolean pureVision(){
        return pureVision;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnClient(final PlayerData data, final IPayloadContext context) {
        ModClientData.setPlayerData(data);
    }


}
