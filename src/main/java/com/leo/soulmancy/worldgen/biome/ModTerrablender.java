package com.leo.soulmancy.worldgen.biome;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {

    public static void registerBiomes(){
        Regions.register(
            new ModOverworldRegion(
                ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "ebony_forest"),
                40
            )
        );
    }

}
