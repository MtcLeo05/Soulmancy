package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> FAUX_ONYX = createTag("faux_onyx");
    public static final TagKey<Item> EYE_CURIOS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "eye"));

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FAUX_ONYX)
            .add(
                ModItems.ONYX.get(),
                ModItems.ARTIFICIAL_ONYX.get()
            );

        addVanillaTags();

        this.tag(EYE_CURIOS)
            .add(
                ModItems.REVEALING_EYE.get(),
                ModItems.SIGHT_LENS.get()
            );

        this.tag(ItemTags.WEAPON_ENCHANTABLE)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );
    }

    private void addVanillaTags(){
        this.tag(ItemTags.LOGS)
            .add(
                ModBlocks.EBONY_LOG.get().asItem(),
                ModBlocks.EBONY_WOOD.get().asItem(),
                ModBlocks.STRIPPED_EBONY_LOG.get().asItem(),
                ModBlocks.STRIPPED_EBONY_WOOD.get().asItem(),
                ModBlocks.EYED_EBONY_LOG.get().asItem()
            );

        this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL)
            .add(
                ModBlocks.EBONY_LOG.get().asItem(),
                ModBlocks.EBONY_WOOD.get().asItem(),
                ModBlocks.EBONY_LEAVES.get().asItem(),
                ModBlocks.STRIPPED_EBONY_LOG.get().asItem(),
                ModBlocks.STRIPPED_EBONY_WOOD.get().asItem(),
                ModBlocks.EYED_EBONY_LOG.get().asItem()
            );

        this.tag(ItemTags.LOGS_THAT_BURN)
            .add(
                ModBlocks.EBONY_LOG.get().asItem(),
                ModBlocks.EBONY_WOOD.get().asItem(),
                ModBlocks.STRIPPED_EBONY_LOG.get().asItem(),
                ModBlocks.STRIPPED_EBONY_WOOD.get().asItem(),
                ModBlocks.EYED_EBONY_LOG.get().asItem()
            );

        this.tag(ItemTags.PLANKS)
            .add(
                ModBlocks.EBONY_PLANKS.get().asItem()
            );

        this.tag(ItemTags.STAIRS)
            .add(
                ModBlocks.EBONY_STAIRS.get().asItem()
            );

        this.tag(ItemTags.WOODEN_STAIRS)
            .add(
                ModBlocks.EBONY_STAIRS.get().asItem()
            );

        this.tag(ItemTags.SLABS)
            .add(
                ModBlocks.EBONY_SLAB.get().asItem()
            );

        this.tag(ItemTags.WOODEN_SLABS)
            .add(
                ModBlocks.EBONY_SLAB.get().asItem()
            );

        this.tag(ItemTags.FENCES)
            .add(
                ModBlocks.EBONY_FENCE.get().asItem()
            );

        this.tag(ItemTags.FENCE_GATES)
            .add(
                ModBlocks.EBONY_FENCE_GATE.get().asItem()
            );

        this.tag(ItemTags.BUTTONS)
            .add(
                ModBlocks.EBONY_BUTTON.get().asItem()
            );

        this.tag(ItemTags.WOODEN_BUTTONS)
            .add(
                ModBlocks.EBONY_BUTTON.get().asItem()
            );

        this.tag(ItemTags.DOORS)
            .add(
                ModBlocks.EBONY_DOOR.get().asItem()
            );

        this.tag(ItemTags.WOODEN_DOORS)
            .add(
                ModBlocks.EBONY_DOOR.get().asItem()
            );

        this.tag(ItemTags.TRAPDOORS)
            .add(
                ModBlocks.EBONY_TRAPDOOR.get().asItem()
            );

        this.tag(ItemTags.WOODEN_TRAPDOORS)
            .add(
                ModBlocks.EBONY_TRAPDOOR.get().asItem()
            );
    }

    private static TagKey<Item> createTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, name));
    }
}
