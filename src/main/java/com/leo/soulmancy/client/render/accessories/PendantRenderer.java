package com.leo.soulmancy.client.render.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PendantRenderer implements SimpleAccessoryRenderer {
    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack poseStack,
                                                                          EntityModel<M> model, MultiBufferSource buffer, int light, float limbSwing,
                                                                          float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        poseStack.pushPose();

        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.translate(0, 0.35, 0.05);

        renderer.renderStatic(
            stack,
            ItemDisplayContext.HEAD,
            light,
            OverlayTexture.NO_OVERLAY,
            poseStack,
            buffer,
            Minecraft.getInstance().level,
            1
        );

        poseStack.popPose();
        poseStack.popPose();
    }

    @Override
    public <M extends LivingEntity> void align(ItemStack itemStack, SlotReference slotReference, EntityModel<M> entityModel, PoseStack poseStack) {}

    public static void registerItem(Item item){
        AccessoriesRendererRegistry.registerRenderer(
            item,
            PendantRenderer::new
        );
    }
}
