package com.leo.soulmancy.compat.jei;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.recipe.AnvilCrushRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class AnvilCrush implements IRecipeCategory<AnvilCrushRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "anvil_crush");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "textures/gui/jei/anvil_crush.png");
    public static final RecipeType<AnvilCrushRecipe> RECIPE_TYPE = new RecipeType<>(UID, AnvilCrushRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public AnvilCrush(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 176, 54);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Blocks.ANVIL.asItem().getDefaultInstance());
    }

    @Override
    public RecipeType<AnvilCrushRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(Soulmancy.MODID + ".jei.anvilCrush");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 54;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AnvilCrushRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(
            RecipeIngredientRole.INPUT,
            33,
            31
        ).addItemStack(recipe.getInputStack());

        builder.addSlot(
            RecipeIngredientRole.RENDER_ONLY,
            80,
            7
        ).addItemStack(Blocks.ANVIL.asItem().getDefaultInstance());

        builder.addSlot(
            RecipeIngredientRole.OUTPUT,
            127,
            31
        ).addItemStack(recipe.getOutputStack());
    }


    @Override
    public void draw(AnvilCrushRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
    }
}
