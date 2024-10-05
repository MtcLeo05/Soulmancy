package com.leo.soulmancy.worldgen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static ResourceKey<BiomeModifier> EBONY_FOREST_GEN = registerKey("ebony_forest_gen");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(
            EBONY_FOREST_GEN,
            new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                    biomes.getOrThrow(ModBiomes.EBONY_FOREST)
                ),
                HolderSet.direct(
                    placedFeatures.getOrThrow(ModPlacedFeatures.ONYX_STONE_PLACED_KEY),
                    placedFeatures.getOrThrow(ModPlacedFeatures.ONYX_DEEPSLATE_PLACED_KEY),
                    placedFeatures.getOrThrow(ModPlacedFeatures.SOUL_STONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
            )
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, name));
    }
}
