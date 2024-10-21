package com.leo.soulmancy.util;

import com.leo.soulmancy.data.SoulData;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.worldgen.biome.ModBiomes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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

    public static boolean isMouseHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static int colorGradient(float value, int lightColor, int darkColor) {
        if (value <= 0) return 0x00FFFFFF;

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

    public static void removeSoulToChunk(BlockPos pos, int soul, Level level) {
        ChunkAccess chunk = level.getChunk(pos);

        SoulData sData = chunk.getData(SOUL_DATA_ATTACHMENT);

        int soulValue = sData.soulValue() - soul;
        soulValue = Math.clamp(soulValue, 0, sData.maxSoulValue());

        sData = new SoulData(soulValue, sData.maxSoulValue());
        chunk.setData(SOUL_DATA_ATTACHMENT, sData);
    }

    public static int getSoulInChunk(BlockPos pos, Level level) {
        ChunkAccess chunk = level.getChunk(pos);

        SoulData data = chunk.getData(SOUL_DATA_ATTACHMENT);
        return data.soulValue();
    }

    public static ItemStack getSoulContainerInPlayer(ServerPlayer sPlayer){
        return Utils.getSoulContainerInPlayer(sPlayer, 0);
    }

    public static ItemStack getNonFullSoulContainerInPlayer(ServerPlayer sPlayer, int min){
        Inventory inventory = sPlayer.getInventory();

        for (ItemStack item : inventory.items) {
            if(item.getItem() instanceof SoulContainer) {
                if(SoulContainer.getSoul(item)[0] + min <= SoulContainer.getSoul(item)[1]) return item;
            }
        }

        return ItemStack.EMPTY;
    }

    public static ItemStack getSoulContainerInPlayer(ServerPlayer sPlayer, int minSoul){
        Inventory inventory = sPlayer.getInventory();

        for (ItemStack item : inventory.items) {
            if(item.getItem() instanceof SoulContainer) {
                if(SoulContainer.getSoul(item)[0] >= minSoul) return item;
            }
        }

        return ItemStack.EMPTY;
    }

    public static void addVesselToChunk(BlockPos pos, int vessel, Level level) {
        ChunkAccess chunk = level.getChunk(pos);

        SoulData data = chunk.getData(SOUL_DATA_ATTACHMENT);
        data = new SoulData(data.soulValue(), data.maxSoulValue() + vessel);
        chunk.setData(SOUL_DATA_ATTACHMENT, data);
    }

    public static void renderSpinningItem(PoseStack poseStack,
                                          float x, float y, float z, float scale, int speed,
                                          Level level,
                                          ItemStack item,
                                          int light,
                                          int overlay,
                                          MultiBufferSource buffer){
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(level.getGameTime() * speed));

        itemRenderer.renderStatic(
            item,
            ItemDisplayContext.FIXED,
            light,
            overlay,
            poseStack,
            buffer,
            level,
            1
        );

        poseStack.popPose();
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

    public static boolean isItemStackValid(ItemStack input, NonNullList<Ingredient> ingredients) {
        boolean toRet = false;

        for (Ingredient ingredient : ingredients) {
            if(ingredient.test(input)) {
                toRet = true;
                break;
            }
        }

        return toRet;
    }
}
