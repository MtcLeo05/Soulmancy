package com.leo.soulmancy.client.render.be;

import com.leo.soulmancy.block.entity.PedestalBE;
import com.leo.soulmancy.block.entity.RitualPedestalBE;
import com.leo.soulmancy.network.packet.StopPedestalParticlesC2SPacket;
import com.leo.soulmancy.util.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBE> {

    public PedestalRenderer(BlockEntityRendererProvider.Context context){}

    private static final int darkColor = 0x2d0047;
    private static final int lightColor = 0x9f00fe;

    private static final DustParticleOptions darkParticle = new DustParticleOptions(
        new Vector3f(
            (darkColor >> 16 & 0xFF) / 255f,
            (darkColor >> 8 & 0xFF)  / 255f,
            (darkColor & 0xFF) / 255f
        ),
        1f
    );

    private static final DustParticleOptions lightParticle = new DustParticleOptions(
        new Vector3f(
            (lightColor >> 16 & 0xFF) / 255f,
            (lightColor >> 8 & 0xFF)  / 255f,
            (lightColor & 0xFF) / 255f
        ),
        1f
    );

    @Override
    public void render(PedestalBE be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        Level level = be.getLevel();
        if(level == null) return;

        ItemStack input = be.getInventory().getStackInSlot(0);

        if(!input.isEmpty()) {
            Utils.renderSpinningItem(
                poseStack,
                0.5f, 17 / 16f, 0.5f, 0.2f, 2,
                level,
                input,
                light,
                overlay,
                buffer
            );
        }

        if(!(be instanceof RitualPedestalBE rpbe)) return;

        BlockPos pos = be.getBlockPos();

        if(rpbe.hasStructure() && rpbe.showParticle) {
            level.addParticle(
                darkParticle,
                pos.getX() + 0.5f,
                pos.getY() + 1,
                pos.getZ() + 0.5f,
                0,
                0,
                0
            );

            if(!rpbe.getCachedPedestals().isEmpty()) {
                for (BlockPos cachedPedestal : rpbe.getCachedPedestals()) {
                    level.addParticle(
                        lightParticle,
                        cachedPedestal.getX() + 0.5f,
                        cachedPedestal.getY() + 1,
                        cachedPedestal.getZ() + 0.5f,
                        0,
                        0,
                        0
                    );
                }

                rpbe.showParticle = false;
                PacketDistributor.sendToServer(new StopPedestalParticlesC2SPacket(pos));
            }
        }

        if(!rpbe.showRange()) return;

        int[] range = rpbe.getRange();
        int x, y, z;
        x = range[0];
        z = range[1];
        y = range[2];

        LevelRenderer.renderLineBox(
            poseStack,
            buffer.getBuffer(RenderType.lines()),
            -x,
            -y,
            -z,
            x + 1,
            y + 1,
            z + 1,
            (darkColor >> 16 & 0xFF) / 255f,
            (darkColor >> 8 & 0xFF)  / 255f,
            (darkColor & 0xFF) / 255f,
            1.0F,
            (darkColor >> 16 & 0xFF) / 255f,
            (darkColor >> 8 & 0xFF)  / 255f,
            (darkColor & 0xFF) / 255f
        );
    }

}
