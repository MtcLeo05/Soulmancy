package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.SoulData;
import com.leo.soulmancy.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameBusEvents {

    //Only check once in a while to prevent lag
    public static int cd = 0, maxCD = 10;

    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent.Post event){
        if(event.getEntity().level().isClientSide) return;
        if(++cd < maxCD) return;

        cd = 0;
        Player player = event.getEntity();
        BlockPos pos = player.getOnPos();
        Level level = player.level();
        BlockPos cPos = level.getChunkAt(pos).getPos().getMiddleBlockPosition(pos.getY());
        SoulData data = SoulData.getOrCreateData(pos, level, Utils.isPosInsideForest(cPos, level));
        PacketDistributor.sendToPlayer((ServerPlayer) player, data);
    }

}
