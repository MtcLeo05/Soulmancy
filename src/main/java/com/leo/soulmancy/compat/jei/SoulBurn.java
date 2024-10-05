package com.leo.soulmancy.compat.jei;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.recipe.manipulator.SoulBurnRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulTransformRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.leo.soulmancy.client.screen.SoulManipulatorScreen.isMouseHovering;

public class SoulBurn implements IRecipeCategory<SoulBurnRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "soul_burn");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "textures/gui/jei/soul_manipulator.png");
    public static final RecipeType<SoulBurnRecipe> RECIPE_TYPE = new RecipeType<>(UID, SoulBurnRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SoulBurn(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 142, 61);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModBlocks.SOUL_MANIPULATOR.get().asItem().getDefaultInstance());
    }

    @Override
    public RecipeType<SoulBurnRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(Soulmancy.MODID + ".jei.soulBurn");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 142;
    }

    @Override
    public int getHeight() {
        return 61;
    }

    int progress = 0;

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SoulBurnRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(
            RecipeIngredientRole.INPUT,
            27,
            23
        ).addItemStack(recipe.getInputStack());

        if(recipe.getOutputStack().isPresent()) {
            builder.addSlot(
                RecipeIngredientRole.OUTPUT,
                99,
                23
            ).addItemStack(recipe.getOutputStack().get());
        }
    }


    @Override
    public void draw(SoulBurnRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);

        if(++progress >= recipe.getDuration()) progress = 0;

        int scaledProgress = progress * 32 / recipe.getDuration();

        guiGraphics.blit(
            TEXTURE,
            55,
            26,
            55,
            61,
            scaledProgress,
            10,
            256,
            256
        );

        if (isMouseHovering(55, 26, 32, 10, mouseX, mouseY)) {
            guiGraphics.renderTooltip(
                Minecraft.getInstance().font,
                Component.translatable(Soulmancy.MODID + ".jei.duration", recipe.getDuration()),
                (int) mouseX,
                (int) mouseY
            );
        }

        if (isMouseHovering(126, 7, 9, 43, mouseX, mouseY)) {
            guiGraphics.renderTooltip(
                Minecraft.getInstance().font,
                Component.translatable(Soulmancy.MODID + ".jei.produceSoul", recipe.getSoul()),
                (int) mouseX,
                (int) mouseY
            );
        }
    }
}
