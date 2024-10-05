package com.leo.soulmancy.client.render.be;

import com.leo.soulmancy.block.entity.SoulManipulatorBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class SoulManipulatorRenderer implements BlockEntityRenderer<SoulManipulatorBE> {

    public SoulManipulatorRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(SoulManipulatorBE soulManipulatorBE, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        Level level = soulManipulatorBE.getLevel();
        if(level == null) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack input = soulManipulatorBE.getInventory().getStackInSlot(0);
        ItemStack output = soulManipulatorBE.getInventory().getStackInSlot(1);

        if(!input.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(12.75 / 16f, 17 / 16f, 0.5f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            BakedModel inputModel = itemRenderer.getModel(input, level, null, 0);

            itemRenderer.render(
                input,
                ItemDisplayContext.FIXED,
                false,
                poseStack,
                multiBufferSource,
                light,
                overlay,
                inputModel
            );
            poseStack.popPose();
        }

        if(!output.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(3.25 / 16f, 17 / 16f, 0.5f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            BakedModel outputModel = itemRenderer.getModel(output, level, null, 0);

            itemRenderer.render(
                output,
                ItemDisplayContext.GUI,
                false,
                poseStack,
                multiBufferSource,
                light,
                overlay,
                outputModel
            );
            poseStack.popPose();
        }
    }

}
