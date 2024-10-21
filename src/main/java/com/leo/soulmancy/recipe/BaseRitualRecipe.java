package com.leo.soulmancy.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class BaseRitualRecipe implements Recipe<ModRecipeInput> {
    protected final ItemStack catalyst;
    protected final List<ItemStack> pedestalItems;
    protected final int soul;
    protected final int duration;

    public BaseRitualRecipe(ItemStack catalyst, List<ItemStack> pedestalItems, int soul, int duration) {
        this.catalyst = catalyst;
        this.pedestalItems = pedestalItems;
        this.soul = soul;
        this.duration = duration;
    }

    public List<ItemStack> getPedestalItems() {
        return pedestalItems;
    }

    public ItemStack getCatalyst() {
        return catalyst;
    }

    public int getDuration() {
        return duration;
    }

    public int getSoul() {
        return soul;
    }

    @Override
    public boolean matches(ModRecipeInput input, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(ModRecipeInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }
}
