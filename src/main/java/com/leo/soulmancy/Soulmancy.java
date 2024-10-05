package com.leo.soulmancy;

import com.leo.soulmancy.init.*;
import com.leo.soulmancy.worldgen.biome.ModTerrablender;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Soulmancy.MODID)
public class Soulmancy {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "soulmancy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Soulmancy(IEventBus modEventBus, ModContainer modContainer) {

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        ModAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);

        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);

        ModTrees.TRUNK_PLACER_TYPES.register(modEventBus);
        ModTerrablender.registerBiomes();

        ModRecipes.RECIPE_TYPES.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}