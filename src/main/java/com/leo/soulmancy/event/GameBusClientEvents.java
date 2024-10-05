package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.util.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GameBusClientEvents {

    @SubscribeEvent
    public static void renderFogEvent(ViewportEvent.RenderFog event) {
        if(Minecraft.getInstance().player == null) return;

        Player player = Minecraft.getInstance().player;
        Level level = player.level();
        BlockPos pos = player.blockPosition();

        //On fast mode, switch directly between fog and no fog
        if(Minecraft.getInstance().options.graphicsMode().get().equals(GraphicsStatus.FAST)) {
            if (!Utils.isPosInsideForest(pos, level)) {
                return;
            }

            RenderSystem.setShaderFogStart(10);
            RenderSystem.setShaderFogEnd(20);
            return;
        }

        boolean nearForest = false;
        double distance = 9999;

        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                BlockPos toCheck = pos.north(x).east(z);

                if(!Utils.isPosInsideForest(toCheck, level)) {
                    continue;
                }

                nearForest = true;

                double temp = pos.getCenter().distanceTo(toCheck.getCenter());

                if(temp < distance) distance = temp;
            }
        }

        if(!nearForest) return;

        distance = Math.exp(-Math.abs(distance) / 10);

        RenderSystem.setShaderFogColor(0.1f, 0.1f, 0.1f, (float) distance);
        RenderSystem.setShaderFogStart(10);
        RenderSystem.setShaderFogEnd(20);
    }
}
