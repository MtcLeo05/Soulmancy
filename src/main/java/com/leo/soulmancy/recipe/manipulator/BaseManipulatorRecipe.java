package com.leo.soulmancy.recipe.manipulator;

import com.leo.soulmancy.recipe.ModRecipeInput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.Optional;

public abstract class BaseManipulatorRecipe implements Recipe<ModRecipeInput> {
    private ItemStack input;
    private Optional<ItemStack> output;
    private int soul, duration;

    public BaseManipulatorRecipe(ItemStack input, Optional<ItemStack> output, int soul, int duration) {
        this.input = input;
        this.output = output;
        this.soul = soul;
        this.duration = duration;
    }

    @Override
    public boolean matches(ModRecipeInput modRecipeInput, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(ModRecipeInput modRecipeInput, HolderLookup.Provider provider) {
        return output.orElse(null);
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.orElse(null);
    }

    public Optional<ItemStack> getOutputStack() {
        return output;
    }

    public ItemStack getInputStack() {
        return input;
    }

    public int getSoul() {
        return soul;
    }

    public int getDuration() {
        return duration;
    }

    public void setInput(ItemStack stack) {
        input = stack;
    }
}
