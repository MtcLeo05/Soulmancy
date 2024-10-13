package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.init.ModRecipes;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.menu.SoulManipulatorMenu;
import com.leo.soulmancy.recipe.manipulator.BaseManipulatorRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulBurnRecipe;
import com.leo.soulmancy.recipe.manipulator.SoulTransformRecipe;
import com.leo.soulmancy.recipe.manipulator.VesselStrengthenRecipe;
import com.leo.soulmancy.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulManipulatorBE extends BaseSoulInteractorMenuProvider {
    public SoulManipulatorBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_MANIPULATOR.get(), pos, blockState);
        itemHandler = new ItemStackHandler(2){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                SoulManipulatorBE.this.sync();
            }
        };
    }


    public void tick(Level level){
        if(!(level instanceof ServerLevel)) return;

        if(itemHandler.getStackInSlot(0).isEmpty() && !(itemHandler.getStackInSlot(1).getItem() instanceof SoulContainer)) {
            progress = 0;
            return;
        }
        if(shouldIncreaseProgress()) progress++;

        if(progress < getRecipeDuration()) return;

        if(canHandleTransformationRecipe()) handleTransformationRecipe();

        if(canHandleSoulBurnRecipe()) handleSoulBurnRecipe();

        if(canHandleVesselStrengthen()) handleVesselStrengthenRecipe();

        handleSoulContainerItem();
    }

    private void handleSoulBurnRecipe() {
        ItemStack cacheItem = itemHandler.getStackInSlot(0);
        BaseManipulatorRecipe cacheRecipe = getRecipe(cacheItem);

        if(cacheRecipe == null) {
            progress = 0;
            return;
        }

        itemHandler.getStackInSlot(0).shrink(cacheRecipe.getInputStack().getCount());

        if(cacheRecipe.getOutputStack().isPresent()) itemHandler.insertItem(1, cacheRecipe.getOutputStack().get().copy(), false);

        addSoulToChunk(cacheRecipe.getSoul());
        progress = 0;
    }

    private void handleTransformationRecipe() {
        ItemStack cacheItem = itemHandler.getStackInSlot(0);
        BaseManipulatorRecipe cacheRecipe = getRecipe(cacheItem);

        if(cacheRecipe == null) {
            progress = 0;
            return;
        }

        itemHandler.getStackInSlot(0).shrink(cacheRecipe.getInputStack().getCount());
        if(cacheRecipe.getOutputStack().isPresent()) itemHandler.insertItem(1, cacheRecipe.getOutputStack().get().copy(), false);

        removeSoulToChunk(cacheRecipe.getSoul());
        progress = 0;
    }

    private void handleVesselStrengthenRecipe() {
        ItemStack cacheItem = itemHandler.getStackInSlot(0);
        BaseManipulatorRecipe cacheRecipe = getRecipe(cacheItem);

        if(cacheRecipe == null) {
            progress = 0;
            return;
        }

        itemHandler.getStackInSlot(0).shrink(cacheRecipe.getInputStack().getCount());
        if(cacheRecipe.getOutputStack().isPresent()) itemHandler.insertItem(1, cacheRecipe.getOutputStack().get().copy(), false);

        addMaxSoulToChunk(cacheRecipe.getSoul());
        progress = 0;
    }

    private boolean canHandleTransformationRecipe(){
        boolean recipeExists = getRecipe() != null;

        if(!recipeExists) return false;
        if(!(getRecipe() instanceof SoulTransformRecipe)) return false;

        boolean canInsertItemInOutput = getRecipe().getOutputStack().isEmpty() || Utils.canInsertItem(getRecipe().getOutputStack().get(), itemHandler.getStackInSlot(1));
        boolean hasMana = getSoulInChunk().soulValue() >= getRecipe().getSoul();

        return canInsertItemInOutput && hasMana;
    }

    private boolean canHandleSoulBurnRecipe(){
        boolean recipeExists = getRecipe() != null;

        if(!recipeExists) return false;
        if(!(getRecipe() instanceof SoulBurnRecipe)) return false;

        return getSoulInChunk().soulValue() + getRecipe().getSoul() <= getSoulInChunk().maxSoulValue();
    }

    private boolean canHandleVesselStrengthen(){
        boolean recipeExists = getRecipe() != null;

        if(!recipeExists) return false;
        if(!(getRecipe() instanceof VesselStrengthenRecipe)) return false;

        return getRecipe().getOutputStack().isEmpty() || Utils.canInsertItem(getRecipe().getOutputStack().get(), itemHandler.getStackInSlot(1));
    }

    private boolean shouldIncreaseProgress(){
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack output = itemHandler.getStackInSlot(1);

        boolean canExtract = getSoulInChunk().soulValue() < getSoulInChunk().maxSoulValue() && SoulContainer.getSoul(input)[0] > 0;
        boolean canInsert = getSoulInChunk().soulValue() > 0 && SoulContainer.getSoul(output)[0] < SoulContainer.getSoul(output)[1];

        return canExtract || canInsert || canHandleTransformationRecipe() || canHandleSoulBurnRecipe() || canHandleVesselStrengthen();
    }

    private void handleSoulContainerItem(){
        ItemStack inputStack = itemHandler.getStackInSlot(0);
        if(inputStack.getItem() instanceof SoulContainer) {
            handleSoulContainerRecipe(inputStack, true);
            return;
        }

        ItemStack outputStack = itemHandler.getStackInSlot(1);
        if(outputStack.getItem() instanceof SoulContainer) {
            handleSoulContainerRecipe(outputStack, false);
        }

    }

    private void handleSoulContainerRecipe(ItemStack stack, boolean extractFromContainer){
        int[] itemData = SoulContainer.getSoul(stack);
        if(itemData[0] == -1 || itemData[1] == -1) return;

        int itemSoul = itemData[0], itemMaxSoul = itemData[1];

        int maxTransfer;

        progress = 0;
        sync();

        if (extractFromContainer) {
            maxTransfer = getSoulInChunk().maxSoulValue() - getSoulInChunk().soulValue();
            maxTransfer = Math.min(maxTransfer, itemSoul);

            int toAdd = maxTransfer;
            SoulContainer.removeSoul(stack, maxTransfer);
            addSoulToChunk(toAdd);
            return;
        }

        maxTransfer = getSoulInChunk().soulValue();
        maxTransfer = Math.min(maxTransfer, itemMaxSoul - itemSoul);

        int chunkRemove = maxTransfer;

        SoulContainer.addSoul(stack, maxTransfer);
        removeSoulToChunk(chunkRemove);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block."+ Soulmancy.MODID + "soul_manipulator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SoulManipulatorMenu(i, inventory, this, data);
    }

    @Override
    protected int getRecipeDuration() {
        return getRecipe() != null ? getRecipe().getDuration(): 10;
    }

    private @Nullable BaseManipulatorRecipe getRecipe(ItemStack input){
        if(level.isClientSide) return null;

        List<RecipeHolder<SoulTransformRecipe>> soulTransform = level.getRecipeManager().getAllRecipesFor(ModRecipes.SOUL_TRANSFORM_TYPE.get());

        for (RecipeHolder<SoulTransformRecipe> recipe : soulTransform) {
            if(Utils.isItemStackValid(input, recipe.value().getInputStack())) {
                return recipe.value();
            }
        }

        List<RecipeHolder<SoulBurnRecipe>> soulBurn = level.getRecipeManager().getAllRecipesFor(ModRecipes.SOUL_BURN_TYPE.get());

        for (RecipeHolder<SoulBurnRecipe> recipe : soulBurn) {
            if(Utils.isItemStackValid(input, recipe.value().getInputStack())) {
                return recipe.value();
            }
        }

        List<RecipeHolder<VesselStrengthenRecipe>> vesselStrengthen = level.getRecipeManager().getAllRecipesFor(ModRecipes.VESSEL_STRENGTHEN_TYPE.get());

        for (RecipeHolder<VesselStrengthenRecipe> recipe : vesselStrengthen) {
            if(Utils.isItemStackValid(input, recipe.value().getInputStack())) {
                return recipe.value();
            }
        }

        return null;
    }

    private @Nullable BaseManipulatorRecipe getRecipe(){
        ItemStack input = itemHandler.getStackInSlot(0);
        return getRecipe(input);
    }
}
