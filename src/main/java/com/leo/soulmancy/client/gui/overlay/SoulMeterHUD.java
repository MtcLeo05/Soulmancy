package com.leo.soulmancy.client.gui.overlay;


import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.client.ModClientData;
import com.leo.soulmancy.data.SoulData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class SoulMeterHUD implements LayeredDraw.Layer {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "textures/overlay/soul_hud.png");
    public static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "textures/overlay/eye_overlay.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!ModClientData.getPlayerData().eyeEquipped && !ModClientData.getPlayerData().pureVision) return;

        RenderSystem.enableBlend();

        if(!ModClientData.getPlayerData().pureVision) {
            guiGraphics.blit(
                OVERLAY,
                0,
                0,
                0,
                0,
                1920,
                1080,
                1920,
                1080
            );
        }

        int mainX = 20, mainY = 20;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.blit(
            TEXTURE,
            mainX,
            mainY,
            0,
            0,
            55,
            167
        );

        SoulData soulData = ModClientData.getCurrentChunkData();
        int scaledSoul = soulData.soulValue() != 0 && soulData.maxSoulValue() != 0 ? (soulData.soulValue() * 133) / soulData.maxSoulValue() : 0;

        if(scaledSoul > 0) {
            guiGraphics.blit(
                TEXTURE,
                mainX + 23,
                mainY + 24 + 133 - scaledSoul,
                60,
                0,
                9,
                scaledSoul
            );
        }

        RenderSystem.disableBlend();

        guiGraphics.pose().popPose();

        if (!Screen.hasShiftDown()) return;
        if (Minecraft.getInstance().screen != null) return;


        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawCenteredString(
            Minecraft.getInstance().font,
            soulData.soulValue() + "/" + soulData.maxSoulValue(),
            mainX + 28,
            mainY + 170,
            0xFFffb3ff
        );
        guiGraphics.pose().popPose();
    }
}
