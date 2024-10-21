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

public class MobRitualRecipe extends BaseRitualRecipe {

    private final EntityType<?> entityType;
    private final int count;
    private final CompoundTag tag;
    private final List<Holder<MobEffect>> effects;

    public MobRitualRecipe(ItemStack catalyst, List<ItemStack> pedestalItems, int soul, int duration, EntityType<?> entityType, int count, CompoundTag tag, List<Holder<MobEffect>> effects) {
        super(catalyst, pedestalItems, soul, duration);
        this.entityType = entityType;
        this.count = count;
        this.tag = tag;
        this.effects = effects;
    }

    public List<ItemStack> getPedestalItems() {
        return pedestalItems;
    }

    public ItemStack getCatalyst() {
        return catalyst;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public int getCount() {
        return count;
    }

    public CompoundTag getTag() {
        return tag;
    }

    public int getDuration() {
        return duration;
    }

    public List<Holder<MobEffect>> getEffects() {
        return effects;
    }

    public List<LivingEntity> getEntities(ServerLevel level) {
        List<LivingEntity> entities = new ArrayList<>();

        tag.putString("id", EntityType.getKey(entityType).toString());

        for (int i = 0; i < count; i++) {
            Entity entity = EntityType.loadEntityRecursive(tag, level, Function.identity());
            if(entity instanceof LivingEntity lEntity) {
                for (Holder<MobEffect> effect : effects) {
                    lEntity.addEffect(
                        new MobEffectInstance(
                            effect,
                            MobEffectInstance.INFINITE_DURATION
                        )
                    );
                }
                entities.add(lEntity);
            }
        }
        return entities;
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

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MOB_RITUAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MOB_RITUAL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MobRitualRecipe> {
        public static final MapCodec<MobRitualRecipe> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                ItemStack.CODEC.fieldOf("catalyst").forGetter(MobRitualRecipe::getCatalyst),
                ItemStack.CODEC.listOf().fieldOf("pedestalItems").forGetter(MobRitualRecipe::getPedestalItems),
                Codec.INT.fieldOf("soul").forGetter(MobRitualRecipe::getSoul),
                Codec.INT.fieldOf("duration").forGetter(MobRitualRecipe::getDuration),
                BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(MobRitualRecipe::getEntityType),
                Codec.INT.fieldOf("count").forGetter(MobRitualRecipe::getCount),
                CompoundTag.CODEC.optionalFieldOf("nbt", new CompoundTag()).forGetter(MobRitualRecipe::getTag),
                BuiltInRegistries.MOB_EFFECT.holderByNameCodec().listOf().optionalFieldOf("effects", new ArrayList<>()).forGetter(MobRitualRecipe::getEffects)
            ).apply(inst, MobRitualRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MobRitualRecipe> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public void encode(RegistryFriendlyByteBuf buf, MobRitualRecipe recipe) {
                ItemStack.STREAM_CODEC.encode(buf, recipe.catalyst);
                ItemStack.LIST_STREAM_CODEC.encode(buf, recipe.pedestalItems);
                ByteBufCodecs.INT.encode(buf, recipe.soul);
                ByteBufCodecs.INT.encode(buf, recipe.duration);
                ByteBufCodecs.holderRegistry(Registries.ENTITY_TYPE).encode(buf, Holder.direct(recipe.entityType));
                ByteBufCodecs.INT.encode(buf, recipe.count);
                ByteBufCodecs.COMPOUND_TAG.encode(buf, recipe.tag);
                ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT).apply(ByteBufCodecs.list()).encode(buf, recipe.effects);
            }

            @Override
            public MobRitualRecipe decode(RegistryFriendlyByteBuf buf) {
                ItemStack catalyst = ItemStack.STREAM_CODEC.decode(buf);
                List<ItemStack> pedestalItems = ItemStack.LIST_STREAM_CODEC.decode(buf);
                int soul = ByteBufCodecs.INT.decode(buf);
                int duration = ByteBufCodecs.INT.decode(buf);
                Holder<EntityType<?>> entityType = ByteBufCodecs.holderRegistry(Registries.ENTITY_TYPE).decode(buf);
                int count = ByteBufCodecs.INT.decode(buf);
                CompoundTag tag = ByteBufCodecs.COMPOUND_TAG.decode(buf);
                List<Holder<MobEffect>> mobEffects = ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT).apply(ByteBufCodecs.list()).decode(buf);
                return new MobRitualRecipe(catalyst, pedestalItems, soul, duration, entityType.value(), count, tag, mobEffects);
            }
        };

        @Override
        public MapCodec<MobRitualRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MobRitualRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder  {
        private MobRitualRecipe recipe;

        private Builder() {
            this.recipe = new MobRitualRecipe(ItemStack.EMPTY, List.of(), 0, 0, EntityType.COW, 1, new CompoundTag(), List.of());
        }

        public static Builder builder(){
            return new Builder();
        }

        public Builder withInput(ItemStack input){
            recipe = new MobRitualRecipe(input, recipe.pedestalItems, recipe.soul, recipe.duration, recipe.entityType, recipe.count, recipe.tag, recipe.effects);
            return this;
        }

        public Builder addPedestalItem(ItemStack output){
            List<ItemStack> items = new ArrayList<>(recipe.pedestalItems);
            items.add(output);

            recipe = new MobRitualRecipe(recipe.catalyst, items, recipe.soul, recipe.duration, recipe.entityType, recipe.count, recipe.tag, recipe.effects);
            return this;
        }

        public Builder withSoul(int soul) {
            recipe = new MobRitualRecipe(recipe.catalyst, recipe.pedestalItems, soul, recipe.duration, recipe.entityType, recipe.count, recipe.tag, recipe.effects);
            return this;
        }

        public Builder withDuration(int duration) {
            recipe = new MobRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, duration, recipe.entityType, recipe.count, recipe.tag, recipe.effects);
            return this;
        }

        public Builder withEntityType(EntityType<?> entityType) {
            recipe = new MobRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, recipe.duration, entityType, recipe.count, recipe.tag, recipe.effects);
            return this;
        }

        public Builder withCount(int count) {
            recipe = new MobRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, recipe.duration, recipe.entityType, count, recipe.tag, recipe.effects);
            return this;
        }

        public Builder withTag(CompoundTag tag) {
            recipe = new MobRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, recipe.duration, recipe.entityType, recipe.count, tag, recipe.effects);
            return this;
        }

        public Builder addEffects(Holder<MobEffect> effect){
            List<Holder<MobEffect>> effects = new ArrayList<>(recipe.effects);
            effects.add(effect);

            recipe = new MobRitualRecipe(recipe.catalyst, recipe.pedestalItems, recipe.soul, recipe.duration, recipe.entityType, recipe.count, recipe.tag, effects);
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
            return recipe.getCatalyst().getItem();
        }

        @Override
        public void save(RecipeOutput recipeOutput, ResourceLocation id) {
            recipeOutput.accept(
                id.withPrefix("ritual/mob/"),
                recipe,
                null
            );
        }
    }
}
