package com.leo.soulmancy.compat.jei;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.client.screen.SoulManipulatorScreen;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModRecipes;
import com.leo.soulmancy.recipe.AnvilCrushRecipe;
import com.leo.soulmancy.recipe.BaseRitualRecipe;
import com.leo.soulmancy.recipe.ItemRitualRecipe;
import com.leo.soulmancy.recipe.MobRitualRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulTransformRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
            new AnvilCrush(registration.getJeiHelpers().getGuiHelper()),
            new SoulTransform(registration.getJeiHelpers().getGuiHelper()),
            new SoulBurn(registration.getJeiHelpers().getGuiHelper()),
            new VesselStrengthen(registration.getJeiHelpers().getGuiHelper()),
            new ImbuementRitual(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(
            AnvilCrush.RECIPE_TYPE,
            gatherRecipes(manager.getAllRecipesFor(ModRecipes.ANVIL_CRUSH_TYPE.get()))
        );

        registration.addRecipes(
            SoulTransform.RECIPE_TYPE,
            gatherRecipes(manager.getAllRecipesFor(ModRecipes.SOUL_TRANSFORM_TYPE.get()))
        );

        registration.addRecipes(
            SoulBurn.RECIPE_TYPE,
            gatherRecipes(manager.getAllRecipesFor(ModRecipes.SOUL_BURN_TYPE.get()))
        );

        registration.addRecipes(
            VesselStrengthen.RECIPE_TYPE,
            gatherRecipes(manager.getAllRecipesFor(ModRecipes.VESSEL_STRENGTHEN_TYPE.get()))
        );

        List<MobRitualRecipe> mobRitualRecipes = gatherRecipes(manager.getAllRecipesFor(ModRecipes.MOB_RITUAL_TYPE.get()));
        List<ItemRitualRecipe> itemRitualRecipes = gatherRecipes(manager.getAllRecipesFor(ModRecipes.ITEM_RITUAL_TYPE.get()));

        List<BaseRitualRecipe> ritualRecipes = new ArrayList<>(mobRitualRecipes);
        ritualRecipes.addAll(itemRitualRecipes);

        registration.addRecipes(
            ImbuementRitual.RECIPE_TYPE,
            ritualRecipes
        );
    }

    private <T extends Recipe<?>> List<T> gatherRecipes(List<RecipeHolder<T>> recipeHolders) {
        List<T> recipes = new ArrayList<>();

        for (RecipeHolder<T> recipeHolder : recipeHolders) {
            recipes.add(recipeHolder.value());
        }

        return recipes;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
            Blocks.ANVIL,
            AnvilCrush.RECIPE_TYPE
        );

        registration.addRecipeCatalyst(
            ModBlocks.SOUL_MANIPULATOR.get(),
            SoulTransform.RECIPE_TYPE,
            SoulBurn.RECIPE_TYPE,
            VesselStrengthen.RECIPE_TYPE
        );

        registration.addRecipeCatalyst(
            ModBlocks.RITUAL_PEDESTAL.get(),
            ImbuementRitual.RECIPE_TYPE
        );

        registration.addRecipeCatalyst(
            ModBlocks.PEDESTAL.get(),
            ImbuementRitual.RECIPE_TYPE
        );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(
            SoulManipulatorScreen.class,
            72,
            40,
            32,
            10,
            SoulTransform.RECIPE_TYPE,
            SoulBurn.RECIPE_TYPE,
            VesselStrengthen.RECIPE_TYPE
        );
    }
}
