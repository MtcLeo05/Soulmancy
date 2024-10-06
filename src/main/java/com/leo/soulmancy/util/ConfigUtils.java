package com.leo.soulmancy.util;

import com.leo.soulmancy.data.SoulData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class ConfigUtils {


    public static class BiomeSoulConfig {
        public ResourceKey<Biome> biome;
        public SoulData dataForBiome;

        public BiomeSoulConfig(ResourceKey<Biome> biome, int soul, int maxSoul) {
            this.biome = biome;
            dataForBiome = new SoulData(soul, maxSoul);
        }


        public static Optional<BiomeSoulConfig> loadFromString(String string){
            String[] biomeSoul = string.split("#");
            if(biomeSoul.length < 2) return Optional.empty();

            String[] soulDataString = biomeSoul[1].split(":");
            if(soulDataString.length < 2) return Optional.empty();

            int soul = Integer.parseInt(soulDataString[0]), maxSoul = Integer.parseInt(soulDataString[1]);

            if(soul > maxSoul) return Optional.empty();

            ResourceKey<Biome> biome = ResourceKey.create(Registries.BIOME, ResourceLocation.parse(biomeSoul[0]));

            return Optional.of(
                new BiomeSoulConfig(biome, soul, maxSoul)
            );
        }
    }
}
