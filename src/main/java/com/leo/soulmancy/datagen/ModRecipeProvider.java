package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.recipe.AnvilCrushRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulBurnRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulTransformRecipe;
import com.leo.soulmancy.recipe.manipulator.VesselStrengthenRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);

        registerVanillaRecipes(recipeOutput);

        AnvilCrushRecipe.Builder.builder()
            .withInput(new ItemStack(Blocks.TINTED_GLASS,2))
            .withOutput(new ItemStack(ModItems.ARTIFICIAL_ONYX.get(),1))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID,"artificial_onyx_from_tinted_glass")
        );

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OCCULT_COMPASS.get(), 1)
            .define('O', ModItemTagsProvider.FAUX_ONYX)
            .define('C', Items.COMPASS)
            .pattern(" O ")
            .pattern("OCO")
            .pattern(" O ")
            .unlockedBy("hasItem", has(ModItemTagsProvider.FAUX_ONYX))
            .save(recipeOutput, "occult_compass");

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(ModItems.ARTIFICIAL_ONYX.get(),2))
            .withOutput(new ItemStack(ModItems.ONYX.get(),1))
            .withSoul(20)
            .withDuration(10)
            .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                Soulmancy.MODID,
                "onyx_from_artificial_onyx"
            )
        );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(ModBlocks.EYED_EBONY_LOG.get(),1))
            .withOutput(new ItemStack(ModItems.REVEALING_EYE.get(),1))
            .withSoul(50)
            .withDuration(20)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "revealing_eye"
                )
            );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(ModItems.REVEALING_EYE.get(),1))
            .withOutput(new ItemStack(ModItems.SIGHT_LENS.get(),1))
            .withSoul(200)
            .withDuration(20)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "sight_lens"
                )
            );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(Items.AMETHYST_SHARD,4))
            .withOutput(new ItemStack(ModItems.ARTIFICIAL_ONYX.get(),1))
            .withSoul(15)
            .withDuration(20)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "artificial_onyx_from_amethyst"
                )
            );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(Blocks.STONE,1))
            .withOutput(new ItemStack(ModBlocks.SOUL_STONE.get(),1))
            .withSoul(25)
            .withDuration(20)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "soul_stone_from_stone"
                )
            );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(Blocks.DEEPSLATE,1))
            .withOutput(new ItemStack(ModBlocks.SOUL_STONE.get(),1))
            .withSoul(15)
            .withDuration(20)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "soul_stone_from_deepslate"
                )
            );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(ModBlocks.SOUL_STONE.get(),1))
            .withOutput(new ItemStack(ModBlocks.CONDENSED_SOUL.get(),1))
            .withSoul(50)
            .withDuration(40)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "condensed_soul"
                )
            );

        SoulTransformRecipe.Builder.builder()
            .withInput(new ItemStack(Blocks.GLASS_PANE,1))
            .withOutput(new ItemStack(ModItems.SOUL_CAPSULE.get(),1))
            .withSoul(25)
            .withDuration(20)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "soul_capsule"
                )
            );

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CAPSULE_STACK.get())
            .requires(ModItems.SOUL_CAPSULE.get())
            .requires(ModItems.SOUL_CAPSULE.get())
            .requires(ModItems.SOUL_CAPSULE.get())
            .unlockedBy("hasItem", has(ModItems.SOUL_CAPSULE.get()))
            .save(recipeOutput, "capsule_stack");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_MANIPULATOR.get())
            .define('O', ModItems.ONYX.get())
            .define('S', ModBlocks.SOUL_STONE.get())
            .pattern("O O")
            .pattern(" S ")
            .pattern("SSS")
            .unlockedBy("hasItem", has(ModItems.ONYX.get()))
            .save(recipeOutput, "soul_manipulator");

        SoulBurnRecipe.Builder.builder()
            .withInput(new ItemStack(ModItems.CRYSTALLIZED_SOUL.get(),1))
            .withSoul(20)
            .withDuration(10)
            .save(
                recipeOutput,
                ResourceLocation.fromNamespaceAndPath(
                    Soulmancy.MODID,
                    "crystallized_soul_recycle"
                )
            );

        SoulBurnRecipe.Builder.builder()
            .withInput(new ItemStack(Blocks.SOUL_SAND,1))
            .withSoul(5)
            .withDuration(10)
            .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                Soulmancy.MODID,
                "soul_sand_extraction"
            )
        );

        VesselStrengthenRecipe.Builder.builder()
            .withInput(new ItemStack(ModBlocks.CONDENSED_SOUL.get(),1))
            .withSoul(15)
            .withDuration(80)
            .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                Soulmancy.MODID,
                "condensed_soul_sacrifice"
            )
        );
    }

    public void registerVanillaRecipes(RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_WOOD.get(), 3)
            .define('L', ModBlocks.EBONY_LOG.get())
            .pattern("LL")
            .pattern("LL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_LOG.get()))
            .save(output, "ebony_wood");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_PLANKS.get(), 4)
            .requires(Ingredient.of(ModBlocks.EBONY_LOG.get(),ModBlocks.EYED_EBONY_LOG.get(), ModBlocks.EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_WOOD.get()))
            .unlockedBy("hasItem", has(ModBlocks.EBONY_LOG.get()))
            .save(output, "ebony_planks");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_EBONY_WOOD.get(), 3)
            .define('L', ModBlocks.STRIPPED_EBONY_LOG.get())
            .pattern("LL")
            .pattern("LL")
            .unlockedBy("hasItem", has(ModBlocks.STRIPPED_EBONY_LOG.get()))
            .save(output, "stripped_ebony_wood");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_STAIRS.get(), 4)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .pattern("L  ")
            .pattern("LL ")
            .pattern("LLL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_stairs");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_SLAB.get(), 6)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .pattern("LLL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_slabs");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_FENCE.get(), 3)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .define('S', Items.STICK)
            .pattern("LSL")
            .pattern("LSL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_fence");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_FENCE_GATE.get(), 1)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .define('S', Items.STICK)
            .pattern("SLS")
            .pattern("SLS")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_fence_gate");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_BUTTON.get(), 1)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .pattern("L")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_button");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_PRESSURE_PLATE.get(), 1)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .pattern("LL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_pressure_plate");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_DOOR.get(), 3)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .pattern("LL")
            .pattern("LL")
            .pattern("LL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_door");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EBONY_TRAPDOOR.get(), 2)
            .define('L', ModBlocks.EBONY_PLANKS.get())
            .pattern("LLL")
            .pattern("LLL")
            .unlockedBy("hasItem", has(ModBlocks.EBONY_PLANKS.get()))
            .save(output, "ebony_trapdoor");

        oreRecipe(Ingredient.of(ModBlocks.ONYX_ORE.get(), ModBlocks.DEEPSLATE_ONYX_ORE.get()), ModItems.ONYX.get(), output, "onyx_ore");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ARTIFICIAL_ONYX_BLOCK.get())
            .define('O', ModItems.ARTIFICIAL_ONYX.get())
            .pattern("OOO")
            .pattern("OOO")
            .pattern("OOO")
            .unlockedBy("hasItem", has(ModItems.ARTIFICIAL_ONYX.get()))
            .save(output, "artificial_onyx_block");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ONYX_BLOCK.get())
            .define('O', ModItems.ONYX.get())
            .pattern("OOO")
            .pattern("OOO")
            .pattern("OOO")
            .unlockedBy("hasItem", has(ModItems.ONYX.get()))
            .save(output, "onyx_block");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONDENSED_SOUL.get())
            .define('O', ModItems.CRYSTALLIZED_SOUL.get())
            .pattern("OO")
            .pattern("OO")
            .unlockedBy("hasItem", has(ModItems.CRYSTALLIZED_SOUL.get()))
            .save(output, "condensed_soul_from_item");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ARTIFICIAL_ONYX.get(), 9)
            .requires(ModBlocks.ARTIFICIAL_ONYX_BLOCK.get())
            .unlockedBy("hasItem", has(ModBlocks.ARTIFICIAL_ONYX_BLOCK.get()))
            .save(output, "artificial_onyx_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ONYX.get(), 9)
            .requires(ModBlocks.ONYX_BLOCK.get())
            .unlockedBy("hasItem", has(ModBlocks.ONYX_BLOCK.get()))
            .save(output, "onyx_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CRYSTALLIZED_SOUL.get(), 4)
            .requires(ModBlocks.CONDENSED_SOUL.get())
            .unlockedBy("hasItem", has(ModBlocks.CONDENSED_SOUL.get()))
            .save(output, "crystallized_soul_from_block");
    }

    public void oreRecipe(Ingredient ores, ItemLike outputItem, RecipeOutput output, String id){
        SimpleCookingRecipeBuilder.blasting(
            ores,
            RecipeCategory.MISC,
            outputItem,
            0.75f,
            100
        ).unlockedBy("hasItem", has(ModItems.ONYX.get()))
            .save(output, "blasting/" + id);

        SimpleCookingRecipeBuilder.smelting(
            ores,
            RecipeCategory.MISC,
            outputItem,
            0.75f,
            200
        ).unlockedBy("hasItem", has(ModItems.ONYX.get()))
            .save(output, "smelting/" + id);
    }
}
