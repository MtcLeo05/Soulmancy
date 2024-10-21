package com.leo.soulmancy.recipe;

import com.leo.soulmancy.init.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ItemRitualRecipe extends BaseRitualRecipe {

    private final ItemStack output;

    public ItemRitualRecipe(ItemStack catalyst, List<ItemStack> pedestalItems, int soul, int duration, ItemStack output) {
        super(catalyst, pedestalItems, soul, duration);
        this.output = output;
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

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public boolean matches(ModRecipeInput input, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(ModRecipeInput input, HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ITEM_RITUAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ITEM_RITUAL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ItemRitualRecipe> {
        public static final MapCodec<ItemRitualRecipe> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                ItemStack.CODEC.fieldOf("catalyst").forGetter(ItemRitualRecipe::getCatalyst),
                ItemStack.CODEC.listOf().fieldOf("pedestalItems").forGetter(ItemRitualRecipe::getPedestalItems),
                Codec.INT.fieldOf("soul").forGetter(ItemRitualRecipe::getSoul),
                Codec.INT.fieldOf("duration").forGetter(ItemRitualRecipe::getDuration),
                ItemStack.CODEC.fieldOf("output").forGetter(ItemRitualRecipe::getOutput)
            ).apply(inst, ItemRitualRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, ItemRitualRecipe> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public void encode(RegistryFriendlyByteBuf buf, ItemRitualRecipe recipe) {
                ItemStack.STREAM_CODEC.encode(buf, recipe.catalyst);
                ItemStack.LIST_STREAM_CODEC.encode(buf, recipe.pedestalItems);
                ByteBufCodecs.INT.encode(buf, recipe.soul);
                ByteBufCodecs.INT.encode(buf, recipe.duration);
                ItemStack.STREAM_CODEC.encode(buf, recipe.output);
            }

            @Override
            public ItemRitualRecipe decode(RegistryFriendlyByteBuf buf) {
                ItemStack catalyst = ItemStack.STREAM_CODEC.decode(buf);
                List<ItemStack> pedestalItems = ItemStack.LIST_STREAM_CODEC.decode(buf);
                int soul = ByteBufCodecs.INT.decode(buf);
                int duration = ByteBufCodecs.INT.decode(buf);
                ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                return new ItemRitualRecipe(catalyst, pedestalItems, soul, duration, output);
            }
        };

        @Override
        public MapCodec<ItemRitualRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ItemRitualRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder  {
        private ItemRitualRecipe recipe;

        private Builder() {
            this.recipe = new ItemRitualRecipe(ItemStack.EMPTY, List.of(), 0, 0, ItemStack.EMPTY);
        }

        public static Builder builder(){
            return new Builder();
        }

        public Builder withInput(ItemStack input){
            recipe = new ItemRitualRecipe(input, recipe.pedestalItems, recipe.soul, recipe.duration, recipe.output);
            return this;
        }

        public Builder addPedestalItem(ItemStack output){
            List<ItemStack> items = new ArrayList<>(recipe.pedestalItems);
            items.add(output);

            recipe = new ItemRitualRecipe(recipe.catalyst, items, recipe.soul, recipe.duration, recipe.output);
            return this;
        }

        public Builder withSoul(int soul) {
            recipe = new ItemRitualRecipe(recipe.catalyst, recipe.pedestalItems, soul, recipe.duration, recipe.output);
            return this;
        }

        public Builder withDuration(int duration) {
            recipe = new ItemRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, duration, recipe.output);
            return this;
        }

        public Builder withOutput(ItemStack output){
            recipe = new ItemRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, recipe.duration, output);
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
            return recipe.getOutput().getItem();
        }

        @Override
        public void save(RecipeOutput recipeOutput, ResourceLocation id) {
            recipeOutput.accept(
                id.withPrefix("ritual/item/"),
                recipe,
                null
            );
        }
    }
}
