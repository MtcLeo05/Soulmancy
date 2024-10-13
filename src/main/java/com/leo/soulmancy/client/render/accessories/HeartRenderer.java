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
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HeartRenderer implements SimpleAccessoryRenderer {
    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack poseStack,
                                                EntityModel<M> model, MultiBufferSource buffer, int light, float limbSwing,
                                                float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        poseStack.pushPose();

        poseStack.mulPose(Axis.ZN.rotationDegrees(180));

        poseStack.scale(0.1f, 0.1f, 0.1f);
        poseStack.translate(1.5f, -2f, -1.5);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        renderer.renderStatic(
            stack,
            ItemDisplayContext.GUI,
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

    public static void registerItem(Item item){
        AccessoriesRendererRegistry.registerRenderer(
            item,
            HeartRenderer::new
        );
    }

    @Override
    public <M extends LivingEntity> void align(ItemStack itemStack, SlotReference slotReference, EntityModel<M> entityModel, PoseStack poseStack) {}
}
