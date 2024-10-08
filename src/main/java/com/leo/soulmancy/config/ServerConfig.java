package com.leo.soulmancy.config;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.util.ConfigUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue SOUL_FROM_KILL = BUILDER
        .comment("A percentage of how much of the entity max health should be added as soul [10]")
        .defineInRange("soulFromKill", 10d, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue VESSEL_FROM_KILL = BUILDER
        .comment("A percentage of how much of the player max health should be added as vessel [2.5]")
        .defineInRange("vesselFromKill", 2.5d, 0, Double.MAX_VALUE);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> SOUL_BIOME_DATA = BUILDER
        .comment("A list of soul data for each biome")
        .comment("It should follow the syntax: modid:biome#startingSoul:maxSoul")
        .defineList("soulData", List.of("soulmancy:ebony_forest#100:100"), ServerConfig::validateSolConfig);

    private static final ModConfigSpec.IntValue SOUL_FURNACE_CONSUME = BUILDER
        .comment("How much soul should the soul furnace consume [2]")
        .defineInRange("soulFurnaceConsume", 2, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue SOUL_FURNACE_SPEED = BUILDER
        .comment("A multiplier for the soul furnace speed [0.75]")
        .defineInRange("soulFurnaceMultiplier", .75d, 0, Double.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static List<ConfigUtils.BiomeSoulConfig> biomeConfigs;
    public static double soulFromKill, vesselFromKill;
    public static int soulFurnaceConsume;
    public static double soulFurnaceSpeed;

    private static boolean validateSolConfig(final Object obj) {
        return obj instanceof String string && ConfigUtils.BiomeSoulConfig.loadFromString(string).isPresent();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if(!SPEC.isLoaded()) return;

        soulFromKill = SOUL_FROM_KILL.get();
        vesselFromKill = VESSEL_FROM_KILL.get();

        soulFurnaceConsume = SOUL_FURNACE_CONSUME.get();
        soulFurnaceSpeed = SOUL_FURNACE_SPEED.get();

        biomeConfigs = new ArrayList<>();

        for (String s : SOUL_BIOME_DATA.get()) {
            biomeConfigs.add(
                ConfigUtils.BiomeSoulConfig.loadFromString(s).get()
            );
        }
    }
}
