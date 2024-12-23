package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.client.gui.overlay.SoulMeterHUD;
import com.leo.soulmancy.client.render.accessories.EyeCoverRenderer;
import com.leo.soulmancy.client.render.accessories.HeartRenderer;
import com.leo.soulmancy.client.render.accessories.PendantRenderer;
import com.leo.soulmancy.client.render.be.PedestalRenderer;
import com.leo.soulmancy.client.render.be.SoulCanalizerRenderer;
import com.leo.soulmancy.client.render.be.SoulManipulatorRenderer;
import com.leo.soulmancy.client.render.be.SoulSacrificerRenderer;
import com.leo.soulmancy.client.screen.SoulManipulatorScreen;
import com.leo.soulmancy.client.screen.SoulSmelteryScreen;
import com.leo.soulmancy.init.*;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.util.Utils;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClientEvents {

    @SubscribeEvent
    public static void registerBlockColorEvent(RegisterColorHandlersEvent.Block event){
        event.register(
            ((state, level, pos, tintIndex) -> {
                if(pos == null || level == null) return -1;

                return BiomeColors.getAverageFoliageColor(level, pos);
            }),
            ModBlocks.EBONY_LEAVES.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColorEvent(RegisterColorHandlersEvent.Item event){
        event.register(
            ((stack, tintIndex) -> {
                Level level = Minecraft.getInstance().level;
                if(level == null || Minecraft.getInstance().player == null) return -1;

                BlockPos pos = Minecraft.getInstance().player.blockPosition();

                return BiomeColors.getAverageFoliageColor(level, pos);
            }),
            ModBlocks.EBONY_LEAVES.get()
        );

        event.register(
            (stack, index) -> {
                if(index != 0) return -1;

                int[] soul = SoulContainer.getSoul(stack);
                float scaledSoul = soul[1] != 0 ? (float) soul[0] / soul[1]: 0;

                return Utils.colorGradient(scaledSoul, 0xFF9f00fe, 0xFF3b005e);
            },
            ModItems.SOUL_CAPSULE.get(),
            ModItems.CAPSULE_STACK.get()
        );
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(
            ModBlockEntities.SOUL_MANIPULATOR.get(),
            SoulManipulatorRenderer::new
        );

        event.registerBlockEntityRenderer(
            ModBlockEntities.SOUL_CANALIZER.get(),
            SoulCanalizerRenderer::new
        );

        event.registerBlockEntityRenderer(
            ModBlockEntities.SOUL_SACRIFICER.get(),
            SoulSacrificerRenderer::new
        );

        event.registerBlockEntityRenderer(
            ModBlockEntities.PEDESTAL.get(),
            PedestalRenderer::new
        );

        event.registerBlockEntityRenderer(
            ModBlockEntities.RITUAL_PEDESTAL.get(),
            PedestalRenderer::new
        );
    }

    @SubscribeEvent
    public static void modClientSetup(FMLClientSetupEvent ignore){
        ItemProperties.register(
            ModItems.OCCULT_COMPASS.get(),
            ResourceLocation.withDefaultNamespace("angle"),
            new CompassItemPropertyFunction((level, stack, entity) -> {
                if (stack.get(ModDataComponents.BIOME_POS) != null) {
                    BlockPos bPos = stack.get(ModDataComponents.BIOME_POS);
                    return GlobalPos.of(Level.OVERWORLD, bPos);
                }

                return null;
            })
        );
    }

    @SubscribeEvent
    public static void registerCuriosRenderer(FMLClientSetupEvent ignore){
        EyeCoverRenderer.registerItem(ModItems.REVEALING_EYE.get());
        EyeCoverRenderer.registerItem(ModItems.SIGHT_LENS.get());

        PendantRenderer.registerItem(ModItems.MINERS_TALISMAN1.get());
        PendantRenderer.registerItem(ModItems.MINERS_TALISMAN2.get());
        PendantRenderer.registerItem(ModItems.MINERS_TALISMAN3.get());

        PendantRenderer.registerItem(ModItems.FIGHTERS_TALISMAN1.get());
        PendantRenderer.registerItem(ModItems.FIGHTERS_TALISMAN2.get());
        PendantRenderer.registerItem(ModItems.FIGHTERS_TALISMAN3.get());

        PendantRenderer.registerItem(ModItems.RUNNERS_TALISMAN1.get());
        PendantRenderer.registerItem(ModItems.RUNNERS_TALISMAN2.get());

        HeartRenderer.registerItem(ModItems.SUSTENANCE_CHARM.get());

        AccessoriesRendererRegistry.registerArmorRendering(ModItems.SOULMANCERS_ROBE.get());
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(
            SoulMeterHUD.TEXTURE,
            new SoulMeterHUD()
        );
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(ModMenuTypes.SOUL_MANIPULATOR.get(), SoulManipulatorScreen::new);
        event.register(ModMenuTypes.SOUL_SMELTERY.get(), SoulSmelteryScreen::new);
    }
}
