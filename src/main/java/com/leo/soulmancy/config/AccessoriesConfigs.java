package com.leo.soulmancy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AccessoriesConfigs {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue SUSTENANCE_FOOD;
    public static final ModConfigSpec.IntValue SUSTENANCE_SATURATION;
    public static final ModConfigSpec.IntValue SUSTENANCE_SLOW_SPEED;
    public static final ModConfigSpec.IntValue SUSTENANCE_FAST_SPEED;
    public static final ModConfigSpec.IntValue SUSTENANCE_SLOW_CONSUME;
    public static final ModConfigSpec.IntValue SUSTENANCE_FAST_CONSUME;

    public static final ModConfigSpec.IntValue ROBE_SPEED;
    public static final ModConfigSpec.IntValue ROBE_SPEED_A;
    public static final ModConfigSpec.IntValue ROBE_PRODUCE;
    public static final ModConfigSpec.IntValue ROBE_PRODUCE_A;

    static {
        BUILDER.push("Accessories Configs");

        BUILDER.push("Sustenance Charm");
            SUSTENANCE_FOOD = BUILDER
                .comment("How much food should the charm give [1]")
                .comment("1 = Half hunger point")
                .defineInRange("charmFood", 1, 1, Integer.MAX_VALUE);

            SUSTENANCE_SATURATION = BUILDER
                .comment("How much saturation should the charm give [1]")
                .comment("1 = Half hunger point")
                .defineInRange("charmSaturation", 1, 1, Integer.MAX_VALUE);

            SUSTENANCE_SLOW_SPEED = BUILDER
                .comment("How fast is the charm in slow mode in ticks [20]")
                .defineInRange("charmSlowSpeed", 20, 1, Integer.MAX_VALUE);

            SUSTENANCE_FAST_SPEED = BUILDER
                .comment("How fast is the charm in fast mode in ticks [5]")
                .defineInRange("charmFastSpeed", 5, 1, Integer.MAX_VALUE);

            SUSTENANCE_SLOW_CONSUME = BUILDER
                .comment("How much soul does the charm consume in slow mode [1]")
                .defineInRange("charmSlowConsume", 1, 0, Integer.MAX_VALUE);

            SUSTENANCE_FAST_CONSUME = BUILDER
                .comment("How much soul does the charm consume in fast mode [4]")
                .defineInRange("charmFastConsume", 4, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Soul Robe");
            ROBE_SPEED = BUILDER
                .comment("How fast is the soulmancer's robe when equipped as chestplate in ticks [200]")
                .defineInRange("robeSpeed", 20, 1, Integer.MAX_VALUE);

            ROBE_SPEED_A = BUILDER
                .comment("How fast is the soulmancer's robe when equipped in the body accessory in ticks [20]")
                .defineInRange("robeSpeedA", 20, 1, Integer.MAX_VALUE);

            ROBE_PRODUCE = BUILDER
                .comment("How much soul does the soulmancer's robe produce when equipped as chestplate [12]")
                .defineInRange("robeProduce", 12, 1, Integer.MAX_VALUE);

            ROBE_PRODUCE_A = BUILDER
                .comment("How much soul does the soulmancer's robe produce when equipped in the body accessory [1]")
                .defineInRange("robeProduceA", 1, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
