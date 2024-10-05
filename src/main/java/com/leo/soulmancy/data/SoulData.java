package com.leo.soulmancy.data;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.client.ModClientData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

import static com.leo.soulmancy.init.ModAttachmentTypes.SOUL_DATA_ATTACHMENT;

public record SoulData(int soulValue, int maxSoulValue) implements CustomPacketPayload {

    public static final Type<SoulData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "soul_data"));

    public static final Codec<SoulData> CODEC = RecordCodecBuilder.create(
        inst -> inst.group(
            Codec.INT.fieldOf("soulValue").forGetter(d -> d.soulValue),
            Codec.INT.fieldOf("maxSoulValue").forGetter(d -> d.maxSoulValue)
        ).apply(inst, SoulData::new)
    );

    public static final StreamCodec<ByteBuf, SoulData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        SoulData::soulValue,
        ByteBufCodecs.INT,
        SoulData::maxSoulValue,
        SoulData::new
    );

    public static SoulData getOrCreateData(BlockPos pos, Level level, boolean isForest) {
        LevelChunk chunk = level.getChunkAt(pos);

        if(chunk.hasData(SOUL_DATA_ATTACHMENT)){
            return chunk.getData(SOUL_DATA_ATTACHMENT);
        }

        RandomSource random = level.getRandom();
        SoulData data;

        if(isForest){
            int soul;
            int maxSoul = soul = random.nextIntBetweenInclusive(50, 125);

            data = new SoulData(soul, maxSoul);
        } else {
            data = new SoulData(0, 0);
        }

        chunk.setData(SOUL_DATA_ATTACHMENT, data);

        return data;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnClient(final SoulData data, final IPayloadContext context) {
        ModClientData.setCurrentChunkData(data);
    }
}