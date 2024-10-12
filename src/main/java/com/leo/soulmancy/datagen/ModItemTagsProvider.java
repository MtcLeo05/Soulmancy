package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.TalismanItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> FAUX_ONYX = createTag("faux_onyx");
    public static final TagKey<Item> EYE_CURIOS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "eye"));
    public static final TagKey<Item> TALISMAN_CURIOS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "talisman"));
    public static final TagKey<Item> CHARM_CURIOS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "charm"));
    public static final TagKey<Item> BACK_CURIOS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "back"));

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

        this.tag(TALISMAN_CURIOS)
            .add(
                ModItems.MINERS_TALISMAN1.get(),
                ModItems.MINERS_TALISMAN2.get(),
                ModItems.MINERS_TALISMAN3.get(),
                ModItems.FIGHTERS_TALISMAN1.get(),
                ModItems.FIGHTERS_TALISMAN2.get(),
                ModItems.FIGHTERS_TALISMAN3.get(),
                ModItems.RUNNERS_TALISMAN1.get(),
                ModItems.RUNNERS_TALISMAN2.get()
            );

        this.tag(CHARM_CURIOS)
            .add(
                ModItems.SUSTENANCE_CHARM.get()
            );

        this.tag(BACK_CURIOS)
            .add(
                ModItems.SOULMANCERS_ROBE.get()
            );

        this.tag(ItemTags.WEAPON_ENCHANTABLE)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(ItemTags.SWORD_ENCHANTABLE)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(ItemTags.VANISHING_ENCHANTABLE)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(Tags.Items.MELEE_WEAPON_TOOLS)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(Tags.Items.ENCHANTABLES)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
            .add(
                ModItems.SOUL_SCYTHE.get()
            );

        this.tag(ItemTags.DURABILITY_ENCHANTABLE)
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
