package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.block.SoulSmelteryBlock;
import com.leo.soulmancy.config.ServerConfig;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.menu.SoulSmelteryMenu;
import com.leo.soulmancy.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulSmelteryBE extends BaseSoulInteractorMenuProvider {

    private int remainingFuel = 0;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case 0 -> progress;
                case 1 -> getSoulInChunk().soulValue();
                case 2 -> getSoulInChunk().maxSoulValue();
                case 3 -> getRecipeDuration();
                case 4 -> remainingFuel;
                default -> -1;
            };
        }

        @Override
        public void set(int i, int i1) {

        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    public SoulSmelteryBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_SMELTERY.get(), pos, blockState);

        itemHandler = new ItemStackHandler(2){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                SoulSmelteryBE.this.sync();
            }
        };
    }

    @Override
    protected int getRecipeDuration() {
        return getRecipe() != null? (int) (getRecipe().getCookingTime() * ServerConfig.soulFurnaceSpeed): 40;
    }

    @Override
    public void tick(Level level) {
        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(SoulSmelteryBlock.LIT, false));

        if(!shouldIncreaseProgress()) return;

        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(SoulSmelteryBlock.LIT, true));

        if(--remainingFuel <= 0) {
            remainingFuel = 202;
            removeSoulToChunk(ServerConfig.soulFurnaceConsume);
        }

        if(++progress < getRecipeDuration()) return;
        progress = 0;

        handleRecipe();
    }

    private boolean shouldIncreaseProgress() {
        boolean enoughSoul = doesChunkHaveSoul(2);
        boolean hasRecipe = getRecipe() != null;

        boolean canInsertOutput = getOutput() != null && Utils.canInsertItem(getOutput(), itemHandler.getStackInSlot(1));

        return enoughSoul && hasRecipe && canInsertOutput;
    }

    private void handleRecipe(){
        if(!(level instanceof ServerLevel sLevel)) return;

        ItemStack cacheItem = itemHandler.getStackInSlot(0);
        AbstractCookingRecipe cacheRecipe = getRecipe(cacheItem);

        if(cacheRecipe == null) return;

        itemHandler.getStackInSlot(0).shrink(1);
        itemHandler.insertItem(1, cacheRecipe.getResultItem(sLevel.registryAccess()).copy(), false);
    }

    private @Nullable ItemStack getOutput(){
        if(getRecipe() == null) return null;
        if(!(level instanceof ServerLevel sLevel)) return null;

        return getRecipe().getResultItem(sLevel.registryAccess());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block."+ Soulmancy.MODID + "soul_smeltery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SoulSmelteryMenu(i, inventory, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("remainingFuel", remainingFuel);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        remainingFuel = tag.getInt("remainingFuel");
    }

    private @Nullable SmeltingRecipe getRecipe(ItemStack input){
        if(level.isClientSide) return null;

        RecipeType<? extends SmeltingRecipe> recipesToGet = RecipeType.SMELTING;

        List<? extends RecipeHolder<? extends SmeltingRecipe>> potentialRecipes = level.getRecipeManager().getAllRecipesFor(recipesToGet);

        for (RecipeHolder<? extends SmeltingRecipe> recipe : potentialRecipes) {
            if(Utils.isItemStackValid(input, recipe.value().getIngredients())) {
                return recipe.value();
            }
        }

        return null;
    }

    private @Nullable SmeltingRecipe getRecipe(){
        ItemStack input = itemHandler.getStackInSlot(0);
        return getRecipe(input);
    }
}
