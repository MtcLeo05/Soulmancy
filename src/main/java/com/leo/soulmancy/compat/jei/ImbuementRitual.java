package com.leo.soulmancy.compat.jei;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.recipe.BaseRitualRecipe;
import com.leo.soulmancy.recipe.ItemRitualRecipe;
import com.leo.soulmancy.recipe.MobRitualRecipe;
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
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;

public class ImbuementRitual implements IRecipeCategory<BaseRitualRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "imbuement_ritual");
    public static final ResourceLocation SLOT = ResourceLocation.fromNamespaceAndPath("jei", "textures/jei/atlas/gui/output_slot.png");
    public static final RecipeType<BaseRitualRecipe> RECIPE_TYPE = new RecipeType<>(UID, BaseRitualRecipe.class);

    private final IDrawable icon;

    public ImbuementRitual(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModBlocks.RITUAL_PEDESTAL.get().asItem().getDefaultInstance());
    }

    @Override
    public RecipeType<BaseRitualRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(Soulmancy.MODID + ".jei.imbuementRitual");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 128;
    }

    @Override
    public int getHeight() {
        return 128;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BaseRitualRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(
            RecipeIngredientRole.INPUT,
            (getWidth() / 2) - 8,
            (getHeight() / 2) - 8
        ).addItemStack(recipe.getCatalyst());

        ImbuementRitual.displayItemsInCircle(
            new Vector2f(
                (getWidth() / 2f),
                (getHeight() / 2f)
            ),
            recipe.getPedestalItems(),
            builder,
            50
        );

        if(recipe instanceof ItemRitualRecipe irr) {
            builder.addSlot(
                RecipeIngredientRole.OUTPUT,
                1,
                1
            ).addItemStack(irr.getOutput());
        }
    }


    @Override
    public void draw(BaseRitualRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(SLOT, 0, 0, 0, 0, 18, 18, 26, 26);

        if(recipe instanceof MobRitualRecipe mrr) {
            if(!((mrr.getEntityType().create(Minecraft.getInstance().level)) instanceof LivingEntity le)) return;

            InventoryScreen.renderEntityInInventory(
                guiGraphics,
                8,
                16,
                10,
                new Vector3f(0, 0, 0),
                (new Quaternionf()).rotationXYZ(0.43633232F, 47, 3.1415927F),
                null,
                le
            );
        }

        guiGraphics.drawString(
            Minecraft.getInstance().font,
            Component.translatable(Soulmancy.MODID + ".jei.consumeSoul", recipe.getSoul()),
            0,
            getHeight() - 5,
            0xFF9f00fe,
            false
        );
    }

    public static void displayItemsInCircle(Vector2f center, List<ItemStack> elements, IRecipeLayoutBuilder builder, float radius) {
        int numElements = elements.size();
        float angleStep = (float) (2 * Math.PI / numElements);

        for (int i = 0; i < numElements; i++) {
            float angle = i * angleStep;
            float x = center.x + radius * (float) Math.cos(angle);
            float z = center.y + radius * (float) Math.sin(angle);

            ItemStack itemStack = elements.get(i);

            builder.addSlot(
                RecipeIngredientRole.INPUT,
                (int) (x - 8),
                (int) (z - 8)
            ).addItemStack(itemStack);
        }
    }
}
