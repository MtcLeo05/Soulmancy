package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.worldgen.ModBiomeModifiers;
import com.leo.soulmancy.worldgen.ModConfiguredFeatures;
import com.leo.soulmancy.worldgen.ModPlacedFeatures;
import com.leo.soulmancy.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModMiscDataProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(Registries.BIOME, ModBiomes::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);

    public ModMiscDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of(Soulmancy.MODID));
    }
}
