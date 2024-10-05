package com.leo.soulmancy.recipe;

import com.leo.soulmancy.init.ModRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AnvilCrushRecipe implements Recipe<ModRecipeInput> {

    private final ItemStack inputStack;
    private final ItemStack outputStack;

    public AnvilCrushRecipe(ItemStack inputStack, ItemStack outputStack) {
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }


    public ItemStack getInputStack() {
        return inputStack;
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

    @Override
    public boolean matches(ModRecipeInput input, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(ModRecipeInput input, HolderLookup.Provider registries) {
        return outputStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ANVIL_CRUSH_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ANVIL_CRUSH_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AnvilCrushRecipe> {
        public static final MapCodec<AnvilCrushRecipe> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                ItemStack.CODEC.fieldOf("input").forGetter(AnvilCrushRecipe::getInputStack),
                ItemStack.CODEC.fieldOf("output").forGetter(AnvilCrushRecipe::getOutputStack)
            ).apply(inst, AnvilCrushRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, AnvilCrushRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, AnvilCrushRecipe::getInputStack,
            ItemStack.STREAM_CODEC, AnvilCrushRecipe::getOutputStack,
            AnvilCrushRecipe::new
        );

        @Override
        public MapCodec<AnvilCrushRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AnvilCrushRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder  {
        private AnvilCrushRecipe recipe;

        private Builder() {
            this.recipe = new AnvilCrushRecipe(ItemStack.EMPTY, ItemStack.EMPTY);
        }

        public static Builder builder(){
            return new Builder();
        }

        public Builder withInput(ItemStack input){
            recipe = new AnvilCrushRecipe(input, recipe.outputStack);
            return this;
        }

        public Builder withOutput(ItemStack output){
            recipe = new AnvilCrushRecipe(recipe.inputStack, output);
            return this;
        }

        @Override
        public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
            return this;
        }

        @Override
        public RecipeBuilder group(@Nullable String groupName) {
            return this;
        }

        @Override
        public Item getResult() {
            return recipe.getOutputStack().getItem();
        }

        @Override
        public void save(RecipeOutput recipeOutput, ResourceLocation id) {
            recipeOutput.accept(
                id.withPrefix("anvil_crush/"),
                recipe,
                null
            );
        }
    }
}
