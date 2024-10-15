package com.leo.soulmancy.client.render.be;

import com.leo.soulmancy.block.entity.SoulSacrificerBE;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.Level;

public class SoulSacrificerRenderer implements BlockEntityRenderer<SoulSacrificerBE> {

    public SoulSacrificerRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(SoulSacrificerBE be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        Level level = be.getLevel();
        if(level == null) return;

        if(!be.showRange()) return;

        int[] range = be.getRange();

        int x, y, z;

        x = range[0];
        z = range[1];
        y = range[2];

        int color = be.mode()? 0x9f00fe: 0xFF3b005e;

        LevelRenderer.renderLineBox(
            poseStack,
            buffer.getBuffer(RenderType.lines()),
            -x,
            -y,
            -z,
            x + 1,
            y + 1,
            z + 1,
            (color >> 16 & 0xFF) / 255f,
            (color >> 8 & 0xFF)  / 255f,
            (color & 0xFF) / 255f,
            1.0F,
            (color >> 16 & 0xFF) / 255f,
            (color >> 8 & 0xFF)  / 255f,
            (color & 0xFF) / 255f
        );
    }

}
