package com.leo.soulmancy.datagen.loot;

import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        List<Block> toIgnore = new ArrayList<>();
        toIgnore.add(ModBlocks.EBONY_LEAVES.get());
        toIgnore.add(ModBlocks.EBONY_SLAB.get());
        toIgnore.add(ModBlocks.ONYX_ORE.get());
        toIgnore.add(ModBlocks.DEEPSLATE_ONYX_ORE.get());
        toIgnore.add(ModBlocks.EBONY_DOOR.get());

        ModBlocks.BLOCKS.getEntries().stream()
            .map(DeferredHolder::get)
            .forEach(block -> {
                if(!toIgnore.contains(block)){
                    this.dropSelf(block);
                }
            });

        this.add(
            ModBlocks.EBONY_LEAVES.get(),
            block -> createLeavesDrops(block, ModBlocks.EBONY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
        );

        this.add(
            ModBlocks.EBONY_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.EBONY_SLAB.get())
        );

        this.add(
            ModBlocks.ONYX_ORE.get(),
            block -> createMultiOreDrop(block, ModItems.ONYX.get(), 1, 4)
        );

        this.add(
            ModBlocks.DEEPSLATE_ONYX_ORE.get(),
            block -> createMultiOreDrop(block, ModItems.ONYX.get(), 2, 5)
        );

        this.add(
            ModBlocks.EBONY_DOOR.get(),
            this::createDoorTable
        );
    }

    public LootTable.Builder createMultiOreDrop(Block block, ItemLike ore, float min, float max){
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
            block,
            this.applyExplosionDecay(
                block,
                LootItem.lootTableItem(ore)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                    .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
            )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();

        ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).forEach(blocks::add);

        return blocks;
    }
}