package com.leo.soulmancy.item;

import com.leo.soulmancy.init.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {

    public static final Tier SCYTHE_TIER = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 905, 7.0F, 2.5F, 12, () -> Ingredient.of(ModItems.ONYX.get()));

}
