package com.leo.soulmancy.worldgen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.worldgen.tree.EbonyTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ONYX_STONE_VEIN = registerKey("onyx_stone_vein");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ONYX_DEEPSLATE_VEIN = registerKey("onyx_deepslate_vein");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_STONE_VEIN = registerKey("soul_stone_vein");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_TREE = registerKey("ebony_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(
            context,
            EBONY_TREE,
            Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.EBONY_LOG.get()),
                new EbonyTrunkPlacer(4, 0, 2),

                BlockStateProvider.simple(ModBlocks.EBONY_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),

                new TwoLayersFeatureSize(1, 0, 2)
            ).build()
        );

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        register(context, ONYX_STONE_VEIN, Feature.ORE, new OreConfiguration(stoneReplaceable, ModBlocks.ONYX_ORE.get().defaultBlockState(),4));
        register(context, ONYX_DEEPSLATE_VEIN, Feature.ORE, new OreConfiguration(deepslateReplaceable, ModBlocks.DEEPSLATE_ONYX_ORE.get().defaultBlockState(),9));

        register(context, SOUL_STONE_VEIN, Feature.ORE, new OreConfiguration(stoneReplaceable, ModBlocks.SOUL_STONE.get().defaultBlockState(), 15));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
