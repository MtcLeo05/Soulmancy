package com.leo.soulmancy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class MachineryConfigs {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue SOUL_FURNACE_CONSUME;

    public static final ModConfigSpec.DoubleValue SOUL_FURNACE_SPEED;

    public static final ModConfigSpec.IntValue SOUL_CANALIZER_X;

    public static final ModConfigSpec.IntValue SOUL_CANALIZER_Y;

    public static final ModConfigSpec.IntValue SOUL_CANALIZER_SPEED;

    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> SOUL_CANALIZER_CONSUME;

    public static final ModConfigSpec.DoubleValue SOUL_CANALIZER_CONVERT_CHANCE;

    public static final ModConfigSpec.IntValue SOUL_CANALIZER_CONVERT_SOUL;

    static {
        BUILDER.push("Machinery Config");

            SOUL_FURNACE_CONSUME = BUILDER
                .comment("How much soul should the soul furnace consume [2]")
                .defineInRange("soulFurnaceConsume", 2, 0, Integer.MAX_VALUE);

            SOUL_FURNACE_SPEED = BUILDER
                .comment("A multiplier for the soul furnace speed [0.75]")
                .defineInRange("soulFurnaceMultiplier", .75d, 0, Double.MAX_VALUE);

            SOUL_CANALIZER_X = BUILDER
                .comment("The Horizontal Range of the Soul Canalizer [2]")
                .defineInRange("soulCanalizerX", 2, 0, Integer.MAX_VALUE);

            SOUL_CANALIZER_Y = BUILDER
                .comment("The Vertical Range of the Soul Canalizer [1]")
                .defineInRange("soulCanalizerY", 1,0, Integer.MAX_VALUE);

            SOUL_CANALIZER_SPEED = BUILDER
                .comment("The Speed in ticks of the Soul Canalizer [40]")
                .defineInRange("soulCanalizerSpeed", 40, 0, Integer.MAX_VALUE);

            SOUL_CANALIZER_CONSUME = BUILDER
                .comment("How much soul should the soul canalizer consume [1, 2]")
                .comment("First value is for crops, sugar canes and cactus, second value is for budding amethyst")
                .defineList("soulCanalizerConsume", List.of(1, 2), (o) -> o instanceof Integer i && i >= 0);

            SOUL_CANALIZER_CONVERT_CHANCE = BUILDER
                .comment("The chance the Soul Canalizer has to convert an Amethyst Block in a Budding Amethyst Block [0.01]")
                .comment("The value is between 0 and 1, so 0.1d is 10%")
                .comment("Set to 0 to disable")
                .defineInRange("soulCanalizerConvertChance", 0.01d, 0, 1);

            SOUL_CANALIZER_CONVERT_SOUL = BUILDER
                .comment("How much soul is consumed to convert an Amethyst Block [250]")
                .defineInRange("soulCanalizerConvertSoul", 250, 0, Integer.MAX_VALUE);

            BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
