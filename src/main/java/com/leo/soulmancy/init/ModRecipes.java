package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.recipe.AnvilCrushRecipe;
import com.leo.soulmancy.recipe.ItemRitualRecipe;
import com.leo.soulmancy.recipe.MobRitualRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulBurnRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulTransformRecipe;
import com.leo.soulmancy.recipe.manipulator.VesselStrengthenRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Soulmancy.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Soulmancy.MODID);

    public static final Supplier<RecipeType<AnvilCrushRecipe>> ANVIL_CRUSH_TYPE = RECIPE_TYPES.register(
        "anvil_crush",
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "anvil_crush"))
    );

    public static final Supplier<RecipeSerializer<AnvilCrushRecipe>> ANVIL_CRUSH_SERIALIZER = RECIPE_SERIALIZERS.register(
        "anvil_crush",
        AnvilCrushRecipe.Serializer::new
    );

    public static final Supplier<RecipeType<SoulTransformRecipe>> SOUL_TRANSFORM_TYPE = RECIPE_TYPES.register(
        "soul_transform",
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "soul_transform"))
    );

    public static final Supplier<RecipeSerializer<SoulTransformRecipe>> SOUL_TRANSFORM_SERIALIZER = RECIPE_SERIALIZERS.register(
        "soul_transform",
        SoulTransformRecipe.Serializer::new
    );

    public static final Supplier<RecipeType<SoulBurnRecipe>> SOUL_BURN_TYPE = RECIPE_TYPES.register(
        "soul_burn",
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "soul_burn"))
    );

    public static final Supplier<RecipeSerializer<SoulBurnRecipe>> SOUL_BURN_SERIALIZER = RECIPE_SERIALIZERS.register(
        "soul_burn",
        SoulBurnRecipe.Serializer::new
    );

    public static final Supplier<RecipeType<VesselStrengthenRecipe>> VESSEL_STRENGTHEN_TYPE = RECIPE_TYPES.register(
        "vessel_strengthen",
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "vessel_strengthen"))
    );

    public static final Supplier<RecipeSerializer<VesselStrengthenRecipe>> VESSEL_STRENGTHEN_SERIALIZER = RECIPE_SERIALIZERS.register(
        "vessel_strengthen",
        VesselStrengthenRecipe.Serializer::new
    );

    public static final Supplier<RecipeType<MobRitualRecipe>> MOB_RITUAL_TYPE = RECIPE_TYPES.register(
        "mob_ritual",
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "mob_ritual"))
    );

    public static final Supplier<RecipeSerializer<MobRitualRecipe>> MOB_RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register(
        "mob_ritual",
        MobRitualRecipe.Serializer::new
    );

    public static final Supplier<RecipeType<ItemRitualRecipe>> ITEM_RITUAL_TYPE = RECIPE_TYPES.register(
        "item_ritual",
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item_ritual"))
    );

    public static final Supplier<RecipeSerializer<ItemRitualRecipe>> ITEM_RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register(
        "item_ritual",
        ItemRitualRecipe.Serializer::new
    );
}
