package com.leo.soulmancy.network;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.data.SoulData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
            PlayerData.TYPE,
            PlayerData.STREAM_CODEC,
            PlayerData::handleDataOnClient
        );

        registrar.playToClient(
            SoulData.TYPE,
            SoulData.STREAM_CODEC,
            SoulData::handleDataOnClient
        );
    }


}
