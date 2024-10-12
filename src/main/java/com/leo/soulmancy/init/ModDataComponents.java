package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.SoulData;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Soulmancy.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> BIOME_POS = DATA_COMPONENTS.register("biome_pos",
        () -> DataComponentType.<BlockPos>builder()
            .persistent(BlockPos.CODEC)
            .build()

    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulData>> SOUL_DATA = DATA_COMPONENTS.register("soul_data",
        () -> DataComponentType.<SoulData>builder()
            .persistent(SoulData.CODEC)
            .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> EQUIPPABLE_COOLDOWN = DATA_COMPONENTS.register("equippable_cooldown",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> GENERIC_MODE = DATA_COMPONENTS.register("generic_mode",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .build()
    );

}
