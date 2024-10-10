package com.leo.soulmancy.client.render.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ArmBandRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                          float netHeadYaw, float headPitch) {

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        matrixStack.pushPose();
        //matrixStack.mulPose(Axis.YP.rotationDegrees(netHeadYaw));
        //matrixStack.mulPose(Axis.XP.rotationDegrees(headPitch));

        matrixStack.pushPose();

        matrixStack.mulPose(Axis.ZN.rotationDegrees(180));
        matrixStack.translate(0, 0.35, 0.025);

        renderer.renderStatic(
            stack,
            ItemDisplayContext.HEAD,
            light,
            OverlayTexture.NO_OVERLAY,
            matrixStack,
            renderTypeBuffer,
            Minecraft.getInstance().level,
            1
        );

        matrixStack.popPose();
        matrixStack.popPose();
    }
}
