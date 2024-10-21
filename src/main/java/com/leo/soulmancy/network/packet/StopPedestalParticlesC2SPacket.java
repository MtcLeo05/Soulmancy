package com.leo.soulmancy.network.packet;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.block.entity.RitualPedestalBE;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record StopPedestalParticlesC2SPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<StopPedestalParticlesC2SPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "stop_pedestal_particles"));

    public static final StreamCodec<ByteBuf, StopPedestalParticlesC2SPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        StopPedestalParticlesC2SPacket::pos,
        StopPedestalParticlesC2SPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnServer(final StopPedestalParticlesC2SPacket data, final IPayloadContext context) {
        if(!(context.player() instanceof ServerPlayer sPlayer)) return;

        ServerLevel serverLevel = sPlayer.serverLevel();

        BlockEntity e = serverLevel.getBlockEntity(data.pos);

        if(!(e instanceof RitualPedestalBE rpbe)) return;

        rpbe.showParticle = false;
    }
}
