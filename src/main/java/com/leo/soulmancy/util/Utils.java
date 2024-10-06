package com.leo.soulmancy.util;

import com.leo.soulmancy.data.SoulData;
import com.leo.soulmancy.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

import static com.leo.soulmancy.init.ModAttachmentTypes.SOUL_DATA_ATTACHMENT;

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

    public static void addSoulToChunk(BlockPos pos, int soul, Level level) {
        ChunkAccess chunk = level.getChunk(pos);

        SoulData data = chunk.getData(SOUL_DATA_ATTACHMENT);

        int toAdd = Math.min(soul, data.maxSoulValue() - data.soulValue());

        data = new SoulData(data.soulValue() + toAdd, data.maxSoulValue());
        chunk.setData(SOUL_DATA_ATTACHMENT, data);
    }

    public static void addVesselToChunk(BlockPos pos, int vessel, Level level) {
        ChunkAccess chunk = level.getChunk(pos);

        SoulData data = chunk.getData(SOUL_DATA_ATTACHMENT);
        data = new SoulData(data.soulValue(), data.maxSoulValue() + vessel);
        chunk.setData(SOUL_DATA_ATTACHMENT, data);
    }

    public static Holder<Enchantment> getEnchantmentHolder(RegistryAccess access, ResourceKey<Enchantment> enchantment) {
        var enchantmentRegistry = access.registry(Registries.ENCHANTMENT);
        if (enchantmentRegistry.isPresent()) {
            var ench = enchantmentRegistry.get().getHolder(enchantment);
            if (ench.isPresent()) {
                return ench.get();
            }
        }
        return null;
    }
}
