package com.leo.soulmancy.util;

import com.leo.soulmancy.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Utils {

    public static boolean isPosInsideForest(BlockPos pos, Level level){
        return level.getBiome(pos).is(ModBiomes.EBONY_FOREST);
    }

    public static boolean isItemStackValid(ItemStack potentialInput, ItemStack recipeStack) {
        boolean sameItemSameComponents = ItemStack.isSameItemSameComponents(potentialInput, recipeStack);
        boolean isCountEnough = potentialInput.getCount() >= recipeStack.getCount();

        return sameItemSameComponents && isCountEnough;
    }

    public static boolean canInsertItem(ItemStack potentialInput, ItemStack otherStack) {
        if(potentialInput.isEmpty() || otherStack.isEmpty()) return true;

        boolean sameItemSameComponents = ItemStack.isSameItemSameComponents(potentialInput, otherStack);
        boolean isCountValid = potentialInput.getCount() + otherStack.getCount() <= otherStack.getMaxStackSize();

        return sameItemSameComponents && isCountValid;
    }

    public static int colorGradient(float value, int lightColor, int darkColor) {
        if (value <= 0) return 0x00FFFFFF;

        // Clamp value between 0 and 1
        value = Math.max(0, Math.min(1, value));

        int red = (int)(((darkColor >> 16) & 0xFF) * value + ((lightColor >> 16) & 0xFF) * (1 - value));
        int green = (int)(((darkColor >> 8) & 0xFF) * value + ((lightColor >> 8) & 0xFF) * (1 - value));
        int blue = (int)((darkColor & 0xFF) * value + (lightColor & 0xFF) * (1 - value));

        return (0xFF << 24) | (red << 16) | (green << 8) | blue;
    }
}
