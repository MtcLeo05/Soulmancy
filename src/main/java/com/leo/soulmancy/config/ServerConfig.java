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


    private static final ModConfigSpec.ConfigValue<List<? extends String>> SOUL_BIOME_DATA = BUILDER
        .comment("A list of soul data for each biome")
        .comment("It should follow the syntax: modid:biome#startingSoul:maxSoul")
        .defineList("soulData", List.of("soulmancy:ebony_forest#100:100"), ServerConfig::validateSolConfig);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static List<ConfigUtils.BiomeSoulConfig> biomeConfigs;

    private static boolean validateSolConfig(final Object obj) {
        return obj instanceof String string && ConfigUtils.BiomeSoulConfig.loadFromString(string).isPresent();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        biomeConfigs = new ArrayList<>();

        for (String s : SOUL_BIOME_DATA.get()) {
            biomeConfigs.add(
                ConfigUtils.BiomeSoulConfig.loadFromString(s).get()
            );
        }
    }
}
