package com.leo.soulmancy.client.render.be;

import com.leo.soulmancy.block.entity.SoulManipulatorBE;
import com.leo.soulmancy.util.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulManipulatorRenderer implements BlockEntityRenderer<SoulManipulatorBE> {

    public SoulManipulatorRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(SoulManipulatorBE soulManipulatorBE, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        Level level = soulManipulatorBE.getLevel();
        if(level == null) return;

        ItemStack input = soulManipulatorBE.getInventory().getStackInSlot(0);
        ItemStack output = soulManipulatorBE.getInventory().getStackInSlot(1);

        if(!input.isEmpty()) {
            Utils.renderSpinningItem(
                poseStack,
                12.9f / 16, 17 / 16f, 0.45f, 0.2f, 4,
                level,
                input,
                light,
                overlay,
                multiBufferSource
            );
        }

        if(!output.isEmpty()) {
            Utils.renderSpinningItem(
                poseStack,
                3.1f / 16f, 17 / 16f, 0.45f, 0.2f, 4,
                level,
                output,
                light,
                overlay,
                multiBufferSource
            );
        }
    }

}
