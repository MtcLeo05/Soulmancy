package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.init.ModRecipes;
import com.leo.soulmancy.recipe.BaseRitualRecipe;
import com.leo.soulmancy.recipe.ItemRitualRecipe;
import com.leo.soulmancy.recipe.MobRitualRecipe;
import com.leo.soulmancy.recipe.ModRecipeInput;
import com.leo.soulmancy.recipe.manipulator.BaseManipulatorRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulBurnRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulTransformRecipe;
import com.leo.soulmancy.recipe.manipulator.VesselStrengthenRecipe;
import com.leo.soulmancy.util.RitualStructure;
import com.leo.soulmancy.util.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RitualPedestalBE extends PedestalBE{

    private boolean showRange = false, hasStructure = false;
    public boolean showParticle = true;

    private int x = 0, y = 0, z = 0;

    private List<BlockPos> cachedPedestals = new ArrayList<>();

    public RitualPedestalBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.RITUAL_PEDESTAL.get(), pos, blockState);
    }

    public void toggleShowRange() {
        this.showRange = !showRange;
        sync();
    }

    public boolean showRange() {
        return showRange;
    }
    public int[] getRange() {
        return new int[]{x, z, y};
    }

    private void updateRange(){
        if(level.isClientSide) return;

        x = z = getHorizontalRange();
        y = getVerticalRange();

        sync();
    }

    public boolean hasStructure() {
        return hasStructure;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putIntArray("range", new int[]{x, z, y});
        tag.putBoolean("showRange", showRange);
        tag.putBoolean("hasStructure", hasStructure);
        tag.putBoolean("showParticle", showParticle);

        CompoundTag pedestals = new CompoundTag();
        for (int i = 0; i < cachedPedestals.size(); i++) {
            pedestals.put("" + i, NbtUtils.writeBlockPos(cachedPedestals.get(i)));
        }

        tag.put("pedestals", pedestals);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        int[] range = tag.getIntArray("range");

        x = range[0];
        z = range[1];
        y = range[2];

        showRange = tag.getBoolean("showRange");
        hasStructure = tag.getBoolean("hasStructure");
        showParticle = tag.getBoolean("showParticle");

        CompoundTag pedestals = tag.getCompound("pedestals");

        for (String key : pedestals.getAllKeys()) {
            cachedPedestals.add(NbtUtils.readBlockPos(pedestals, key).get());
        }
    }

    @Override
    public int getRecipeDuration() {
        return getRecipe() != null? getRecipe().getDuration(): 0;
    }

    public int getProgress(){
        return progress;
    }

    public List<BlockPos> getCachedPedestals() {
        return cachedPedestals;
    }

    @Override
    public void tick(Level level) {
        if (level.isClientSide) return;
        updateRange();

        hasStructure = RitualStructure.hasStructure(getBlockPos(), getLevel());

        if(!hasStructure) return;

        BaseRitualRecipe recipe = getRecipe();
        if(recipe == null) return;
        ServerLevel sLevel = ((ServerLevel) level);

        if(getSoulInChunk().soulValue() < recipe.getSoul()) return;

        List<ItemStack> missingItems = new ArrayList<>(recipe.getPedestalItems());

        for (BlockPos pos : cachedPedestals) {
            PedestalBE be = ((PedestalBE) level.getBlockEntity(pos));

            if(be == null) {
                cachedPedestals.remove(pos);
                continue;
            }

            ItemStack pedestalItem = be.getItem();

            missingItems.removeIf(item -> Utils.isItemStackValid(pedestalItem, item));
        }

        if(!missingItems.isEmpty()) return;

        if(++progress < getRecipeDuration()) return;

        progress = 0;
        removeSoulToChunk(recipe.getSoul());
        itemHandler.getStackInSlot(0).shrink(recipe.getCatalyst().getCount());

        missingItems = recipe.getPedestalItems();

        for (BlockPos pos : cachedPedestals) {
            PedestalBE be = ((PedestalBE) level.getBlockEntity(pos));

            if(be == null) {
                cachedPedestals.remove(pos);
                continue;
            }

            ItemStack pedestalItem = be.getItem();

            for (ItemStack missingItem : missingItems) {
                if(Utils.isItemStackValid(pedestalItem, missingItem)){
                    be.shrinkItem(missingItem.getCount());
                }
            }
        }

        if(recipe instanceof MobRitualRecipe mrr) {
            for (LivingEntity entity : mrr.getEntities(sLevel)) {
                float x, z;

                x = (level.getRandom().nextFloat() * 2) - 4;
                z = (level.getRandom().nextFloat() * 2) - 4;

                entity.teleportTo(getBlockPos().getX() + x, getBlockPos().getY() + 1, getBlockPos().getZ() + z);
                sLevel.addFreshEntity(entity);
            }
        }

        if(recipe instanceof ItemRitualRecipe irr) {
            ItemStack output = irr.getOutput().copy();

            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY() + 1, getBlockPos().getZ(), output);
        }
    }


    private @Nullable BaseRitualRecipe getRecipe(ItemStack input){
        if(level.isClientSide) return null;

        List<RecipeHolder<MobRitualRecipe>> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.MOB_RITUAL_TYPE.get());

        for (RecipeHolder<MobRitualRecipe> recipe : recipes) {
            if(Utils.isItemStackValid(input, recipe.value().getCatalyst())) {
                return recipe.value();
            }
        }

        List<RecipeHolder<ItemRitualRecipe>> itemRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.ITEM_RITUAL_TYPE.get());

        for (RecipeHolder<ItemRitualRecipe> recipe : itemRecipes) {
            if(Utils.isItemStackValid(input, recipe.value().getCatalyst())) {
                return recipe.value();
            }
        }

        return null;
    }

    private @Nullable BaseRitualRecipe getRecipe(){
        ItemStack input = itemHandler.getStackInSlot(0);
        return getRecipe(input);
    }

    @Override
    public ItemStack interact(ItemStack playerItem) {
        cachePedestals();
        return super.interact(playerItem);
    }

    private void cachePedestals(){
        if(level.isClientSide) return;

        BlockPos pos = getBlockPos();

        for (int x = -getHorizontalRange(); x <= getHorizontalRange(); x++) {
            for (int y = -getVerticalRange(); y <= getVerticalRange(); y++) {
                for (int z = -getHorizontalRange(); z <= getHorizontalRange(); z++) {
                    if(x == 0 && y == 0 && z == 0) continue;
                    BlockPos use = pos.north(x).east(z).above(y);

                    if (!(level.getBlockEntity(use) instanceof PedestalBE)) {
                        continue;
                    }

                    if(!cachedPedestals.contains(use)) cachedPedestals.add(use);
                }
            }
        }

        cachedPedestals = cachedPedestals.stream().filter(b -> level.getBlockEntity(b) instanceof PedestalBE).collect(Collectors.toList());
    }

    private int getHorizontalRange(){
        return 3;
    }

    private int getVerticalRange(){
        return 1;
    }
}
