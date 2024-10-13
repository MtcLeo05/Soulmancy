package com.leo.soulmancy.client.render.be;

import com.leo.soulmancy.block.entity.SoulCanalizerBE;
import com.leo.soulmancy.block.entity.SoulManipulatorBE;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulCanalizerRenderer implements BlockEntityRenderer<SoulCanalizerBE> {

    public SoulCanalizerRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(SoulCanalizerBE soulCanalizerBE, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        Level level = soulCanalizerBE.getLevel();
        if(level == null) return;

        if(!soulCanalizerBE.showRange()) return;

        int[] range = soulCanalizerBE.getRange();

        int x, y, z;

        x = range[0];
        z = range[1];
        y = range[2];

        int color = 0x9f00fe;

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
