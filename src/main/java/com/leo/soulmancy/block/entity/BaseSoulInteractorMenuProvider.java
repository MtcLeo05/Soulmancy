package com.leo.soulmancy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

import static com.leo.soulmancy.init.ModAttachmentTypes.SOUL_DATA_ATTACHMENT;

public abstract class BaseSoulInteractorMenuProvider extends BaseSoulInteractor implements MenuProvider {
    public BaseSoulInteractorMenuProvider(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
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
}
