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

public class VesselStrengthenRecipe extends BaseManipulatorRecipe{

    public VesselStrengthenRecipe(ItemStack input, int soul, int duration) {
        this(input, Optional.empty(), soul, duration);
    }

    public VesselStrengthenRecipe(ItemStack input, Optional<ItemStack> output, int soul, int duration) {
        super(input, output, soul, duration);
    }

    public VesselStrengthenRecipe(ItemStack input, ItemStack output, int soul, int duration) {
        this(input, Optional.of(output), soul, duration);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.VESSEL_STRENGTHEN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.VESSEL_STRENGTHEN_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<VesselStrengthenRecipe> {
        public static final MapCodec<VesselStrengthenRecipe> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                ItemStack.CODEC.fieldOf("input").forGetter(VesselStrengthenRecipe::getInputStack),
                ItemStack.CODEC.optionalFieldOf("output").forGetter(VesselStrengthenRecipe::getOutputStack),
                Codec.INT.fieldOf("soul").forGetter(VesselStrengthenRecipe::getSoul),
                Codec.INT.fieldOf("duration").forGetter(VesselStrengthenRecipe::getDuration)
            ).apply(inst, VesselStrengthenRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, VesselStrengthenRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, VesselStrengthenRecipe::getInputStack,
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs::optional), VesselStrengthenRecipe::getOutputStack,
            ByteBufCodecs.INT, VesselStrengthenRecipe::getSoul,
            ByteBufCodecs.INT, VesselStrengthenRecipe::getDuration,
            VesselStrengthenRecipe::new
        );

        @Override
        public MapCodec<VesselStrengthenRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VesselStrengthenRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder {
        private VesselStrengthenRecipe recipe;

        private Builder() {
            recipe = new VesselStrengthenRecipe(
                ItemStack.EMPTY,
                Optional.empty(),
                -1,
                -1
            );
        }

        public static Builder builder(){
            return new Builder();
        }

        public Builder withInput(ItemStack stack){
            recipe = new VesselStrengthenRecipe(
                stack,
                recipe.getOutputStack(),
                recipe.getSoul(),
                recipe.getDuration()
            );
            return this;
        }

        public Builder withOutput(ItemStack stack) {
            recipe = new VesselStrengthenRecipe(
                recipe.getInputStack(),
                Optional.of(stack),
                recipe.getSoul(),
                recipe.getDuration()
            );
            return this;
        }

        public Builder withSoul(int soul) {
            recipe = new VesselStrengthenRecipe(
                recipe.getInputStack(),
                recipe.getOutputStack(),
                soul,
                recipe.getDuration()
            );
            return this;
        }

        public Builder withDuration(int duration) {
            recipe = new VesselStrengthenRecipe(
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
                id.withPrefix("vessel_strengthen/"),
                recipe,
                null
            );
        }
    }
}
