package com.leo.soulmancy.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class ModRecipeInput implements RecipeInput {

    private final ItemStack stack;

    public ModRecipeInput(ItemStack stack){
        this.stack = stack;
    }

    @Override
    public ItemStack getItem(int index) {
        return stack;
    }

    @Override
    public int size() {
        return 1;
    }
}
