package com.leo.soulmancy.worldgen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ONYX_STONE_PLACED_KEY = registerKey("onyx_stone_vein");
    public static final ResourceKey<PlacedFeature> ONYX_DEEPSLATE_PLACED_KEY = registerKey("onyx_deepslate_vein");
    public static final ResourceKey<PlacedFeature> SOUL_STONE_PLACED_KEY = registerKey("soul_stone_vein");


    public static final ResourceKey<PlacedFeature> EBONY_TREE_PLACED_KEY = registerKey("ebony_tree");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, EBONY_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.EBONY_TREE),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(7, 0.1f, 4),
                ModBlocks.EBONY_SAPLING.get()));


        register(context, ONYX_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ONYX_STONE_VEIN),
            ModOrePlacements.rareOrePlacement(
                4,
                HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(8),
                    VerticalAnchor.absolute(32)
                )
            )
        );

        register(context, ONYX_DEEPSLATE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ONYX_DEEPSLATE_VEIN),
            ModOrePlacements.commonOrePlacement(
                15,
                HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(-64),
                    VerticalAnchor.absolute(7)
                )
            )
        );

        register(context, SOUL_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SOUL_STONE_VEIN),
            ModOrePlacements.commonOrePlacement(
                15,
                HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(-64),
                    VerticalAnchor.absolute(320)
                )
            )
        );
    }



    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
