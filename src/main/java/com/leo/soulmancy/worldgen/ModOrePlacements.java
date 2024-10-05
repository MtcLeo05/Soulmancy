package com.leo.soulmancy.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacements {

    public static List<PlacementModifier> orePlacement(PlacementModifier first, PlacementModifier second) {
        return List.of(first, InSquarePlacement.spread(), second, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(count), heightRange);
    }
}
