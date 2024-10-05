package com.leo.soulmancy.recipe.manipulator;

import com.leo.soulmancy.init.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SoulBurnRecipe extends BaseManipulatorRecipe{

    public SoulBurnRecipe(ItemStack input, int soul, int duration) {
        this(input, Optional.empty(), soul, duration);
    }

    public SoulBurnRecipe(ItemStack input, Optional<ItemStack> output, int soul, int duration) {
        super(input, output, soul, duration);
    }

    public SoulBurnRecipe(ItemStack input, ItemStack output, int soul, int duration) {
        this(input, Optional.of(output), soul, duration);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SOUL_BURN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SOUL_BURN_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SoulBurnRecipe> {
        public static final MapCodec<SoulBurnRecipe> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                ItemStack.CODEC.fieldOf("input").forGetter(SoulBurnRecipe::getInputStack),
                ItemStack.CODEC.optionalFieldOf("output").forGetter(SoulBurnRecipe::getOutputStack),
                Codec.INT.fieldOf("soul").forGetter(SoulBurnRecipe::getSoul),
                Codec.INT.fieldOf("duration").forGetter(SoulBurnRecipe::getDuration)
            ).apply(inst, SoulBurnRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, SoulBurnRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, SoulBurnRecipe::getInputStack,
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs::optional), SoulBurnRecipe::getOutputStack,
            ByteBufCodecs.INT, SoulBurnRecipe::getSoul,
            ByteBufCodecs.INT, SoulBurnRecipe::getDuration,
            SoulBurnRecipe::new
        );

        @Override
        public MapCodec<SoulBurnRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SoulBurnRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder {
        private SoulBurnRecipe recipe;

        private Builder() {
            recipe = new SoulBurnRecipe(
                ItemStack.EMPTY,
                Optional.empty(),
                -1,
                -1
            );
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withInput(ItemStack stack){
            recipe = new SoulBurnRecipe(
                stack,
                recipe.getOutputStack(),
                recipe.getSoul(),
                recipe.getDuration()
            );
            return this;
        }

        public Builder withOutput(ItemStack stack) {
            recipe = new SoulBurnRecipe(
                recipe.getInputStack(),
                Optional.of(stack),
                recipe.getSoul(),
                recipe.getDuration()
            );
            return this;
        }

        public Builder withSoul(int soul) {
            recipe = new SoulBurnRecipe(
                recipe.getInputStack(),
                recipe.getOutputStack(),
                soul,
                recipe.getDuration()
            );
            return this;
        }

        public Builder withDuration(int duration) {
            recipe = new SoulBurnRecipe(
                recipe.getInputStack(),
                recipe.getOutputStack(),
                recipe.getSoul(),
                duration
            );
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
            return recipe.getOutputStack().get().getItem();
        }

        @Override
        public void save(RecipeOutput recipeOutput, ResourceLocation id) {
            recipeOutput.accept(
                id.withPrefix("soul_burn/"),
                recipe,
                null
            );
        }
    }
}
