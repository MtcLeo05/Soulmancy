package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class PedestalBE extends BaseSoulInteractor{
    public PedestalBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PEDESTAL.get(), pos, blockState);
        itemHandler = new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                PedestalBE.this.sync();
            }
        };
    }

    public PedestalBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        itemHandler = new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                PedestalBE.this.sync();
            }
        };
    }

    @Override
    protected int getRecipeDuration() {
        return 0;
    }

    @Override
    public void dropContents() {
        if(itemHandler == null) return;

        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.add(itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(getLevel(), getBlockPos(), new SimpleContainer(items.toArray(new ItemStack[]{})));
    }

    public ItemStack getItem(){
        return itemHandler.getStackInSlot(0);
    }

    public void shrinkItem(int count){
        ItemStack stackInSlot = itemHandler.getStackInSlot(0);
        itemHandler.setStackInSlot(0, stackInSlot.copyWithCount(stackInSlot.getCount() - count));
    }

    @Override
    public void tick(Level level) {}

    public ItemStack interact(ItemStack playerItem) {
        ItemStack currentItem = itemHandler.getStackInSlot(0);

        if(currentItem.isEmpty()){
            if(!playerItem.isEmpty()){
                itemHandler.setStackInSlot(0, playerItem.copyAndClear());
            }
            return ItemStack.EMPTY;
        } else {
            if(currentItem.is(playerItem.getItem())){
                int cCount = currentItem.getCount(), pCount = playerItem.getCount();
                itemHandler.insertItem(0, playerItem, false);

                return playerItem.copyWithCount((cCount + pCount) - playerItem.getMaxStackSize());
            } else {
                itemHandler.setStackInSlot(0, playerItem.copyAndClear());

                return currentItem.copy();
            }
        }
    }
}
