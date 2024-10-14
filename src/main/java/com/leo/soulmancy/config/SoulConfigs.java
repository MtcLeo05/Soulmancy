package com.leo.soulmancy.config;

import com.leo.soulmancy.util.ConfigUtils;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class SoulConfigs {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.DoubleValue SOUL_FROM_KILL;

    public static final ModConfigSpec.DoubleValue VESSEL_FROM_KILL;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> SOUL_BIOME_DATA;

    static {
        BUILDER.push("Soul Configs");

        SOUL_FROM_KILL = BUILDER
            .comment("A percentage of how much of the entity max health should be added as soul [10]")
            .defineInRange("soulFromKill", 10d, 0, Double.MAX_VALUE);

        VESSEL_FROM_KILL = BUILDER
            .comment("A percentage of how much of the player max health should be added as vessel [2.5]")
            .defineInRange("vesselFromKill", 2.5d, 0, Double.MAX_VALUE);

        SOUL_BIOME_DATA = BUILDER
            .comment("A list of soul data for each biome")
            .comment("This won't be the exact values, as they are randomized around the one inserted")
            .comment("It should follow the syntax: modid:biome#startingSoul:maxSoul")
            .defineList("soulData", List.of("soulmancy:ebony_forest#100:100"), SoulConfigs::validateSoulConfig);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private static boolean validateSoulConfig(final Object obj) {
        return obj instanceof String string && ConfigUtils.BiomeSoulConfig.loadFromString(string).isPresent();
    }

    public static List<ConfigUtils.BiomeSoulConfig> getBiomeConfigs(){
        if(!SPEC.isLoaded()) return new ArrayList<>();

        List<ConfigUtils.BiomeSoulConfig> biomeConfigs = new ArrayList<>();

        for (String s : SOUL_BIOME_DATA.get()) {
            biomeConfigs.add(
                ConfigUtils.BiomeSoulConfig.loadFromString(s).get()
            );
        }

        return biomeConfigs;
    }
}
