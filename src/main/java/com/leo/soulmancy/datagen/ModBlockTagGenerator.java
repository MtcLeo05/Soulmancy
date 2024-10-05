package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Soulmancy.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.LOGS)
            .add(
                ModBlocks.EBONY_LOG.get(),
                ModBlocks.EBONY_WOOD.get(),
                ModBlocks.STRIPPED_EBONY_LOG.get(),
                ModBlocks.STRIPPED_EBONY_WOOD.get(),
                ModBlocks.EYED_EBONY_LOG.get()
            );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(
                ModBlocks.EBONY_LOG.get(),
                ModBlocks.EBONY_WOOD.get(),
                ModBlocks.EYED_EBONY_LOG.get(),
                ModBlocks.STRIPPED_EBONY_LOG.get(),
                ModBlocks.STRIPPED_EBONY_WOOD.get(),
                ModBlocks.EBONY_PLANKS.get(),
                ModBlocks.EBONY_STAIRS.get(),
                ModBlocks.EBONY_FENCE.get(),
                ModBlocks.EBONY_FENCE_GATE.get(),
                ModBlocks.EBONY_BUTTON.get(),
                ModBlocks.EBONY_PRESSURE_PLATE.get(),
                ModBlocks.EBONY_DOOR.get(),
                ModBlocks.EBONY_TRAPDOOR.get()
            );

        this.tag(BlockTags.LOGS_THAT_BURN)
            .add(
                ModBlocks.EBONY_LOG.get(),
                ModBlocks.EBONY_WOOD.get(),
                ModBlocks.STRIPPED_EBONY_LOG.get(),
                ModBlocks.STRIPPED_EBONY_WOOD.get(),
                ModBlocks.EYED_EBONY_LOG.get()
            );

        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
            .add(
                ModBlocks.EBONY_LOG.get(),
                ModBlocks.EBONY_WOOD.get(),
                ModBlocks.STRIPPED_EBONY_LOG.get(),
                ModBlocks.STRIPPED_EBONY_WOOD.get(),
                ModBlocks.EYED_EBONY_LOG.get()
            );

        this.tag(BlockTags.PLANKS)
            .add(
                ModBlocks.EBONY_PLANKS.get()
            );

        this.tag(BlockTags.STAIRS)
            .add(
                ModBlocks.EBONY_STAIRS.get()
            );

        this.tag(BlockTags.WOODEN_STAIRS)
            .add(
                ModBlocks.EBONY_STAIRS.get()
            );

        this.tag(BlockTags.SLABS)
            .add(
                ModBlocks.EBONY_SLAB.get()
            );

        this.tag(BlockTags.WOODEN_SLABS)
            .add(
                ModBlocks.EBONY_SLAB.get()
            );

        this.tag(BlockTags.FENCES)
            .add(
                ModBlocks.EBONY_FENCE.get()
            );

        this.tag(BlockTags.FENCE_GATES)
            .add(
                ModBlocks.EBONY_FENCE_GATE.get()
            );

        this.tag(BlockTags.BUTTONS)
            .add(
                ModBlocks.EBONY_BUTTON.get()
            );

        this.tag(BlockTags.WOODEN_BUTTONS)
            .add(
                ModBlocks.EBONY_BUTTON.get()
            );

        this.tag(BlockTags.PRESSURE_PLATES)
            .add(
                ModBlocks.EBONY_PRESSURE_PLATE.get()
            );

        this.tag(BlockTags.DOORS)
            .add(
                ModBlocks.EBONY_DOOR.get()
            );

        this.tag(BlockTags.WOODEN_DOORS)
            .add(
                ModBlocks.EBONY_DOOR.get()
            );

        this.tag(BlockTags.TRAPDOORS)
            .add(
                ModBlocks.EBONY_TRAPDOOR.get()
            );

        this.tag(BlockTags.WOODEN_TRAPDOORS)
            .add(
                ModBlocks.EBONY_TRAPDOOR.get()
            );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(
                ModBlocks.ONYX_ORE.get(),
                ModBlocks.DEEPSLATE_ONYX_ORE.get(),
                ModBlocks.SOUL_STONE.get(),
                ModBlocks.CONDENSED_SOUL.get(),
                ModBlocks.SOUL_MANIPULATOR.get()
            );

        this.tag(BlockTags.NEEDS_STONE_TOOL)
            .add(
                ModBlocks.EYED_EBONY_LOG.get(),
                ModBlocks.ONYX_ORE.get(),
                ModBlocks.DEEPSLATE_ONYX_ORE.get(),
                ModBlocks.SOUL_MANIPULATOR.get()
            );
    }
}
