package com.leo.soulmancy.worldgen.biome;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.worldgen.ModPlacedFeatures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {

    public static final ResourceKey<Biome> EBONY_FOREST = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "ebony_forest"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(
            EBONY_FOREST, ebonyForest(context)
        );
    }

    private static Biome ebonyForest(BootstrapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.creatureGenerationProbability(0f);
        /*spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));*/

        BiomeGenerationSettings.Builder biomeBuilder =
            new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.EBONY_TREE_PLACED_KEY);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .downfall(0f)
            .temperature(0.2f)
            .generationSettings(biomeBuilder.build())
            .mobSpawnSettings(spawnBuilder.build())
            .specialEffects(
                new BiomeSpecialEffects.Builder()
                .waterColor(0xFF303030)
                .waterFogColor(0xFF242424)
                .skyColor(10340818)
                .grassColorOverride(4539717)
                .foliageColorOverride(2368548)
                .fogColor(0xFF111111)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.ASH, 0.05f))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()
            )
            .build();
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }
}
