package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.worldgen.ModConfiguredFeatures;
import com.leo.soulmancy.worldgen.tree.EbonyTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class ModTrees {

    public static final TreeGrower EBONY_TREE_GROWER = new TreeGrower(
        "ebony_tree",
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.EBONY_TREE),
        Optional.empty()
    );


    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Soulmancy.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<EbonyTrunkPlacer>> EBONY_TRUNK_PLACER = TRUNK_PLACER_TYPES.register(
        "ebony_trunk_placer",
        () -> new TrunkPlacerType<>(
            EbonyTrunkPlacer.CODEC
        )
    );

}
