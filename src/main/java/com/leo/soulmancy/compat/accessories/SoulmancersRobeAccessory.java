package com.leo.soulmancy.compat.accessories;

import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.SoulmancersRobeItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class SoulmancersRobeAccessory implements Accessory {
    public static void init(){
        AccessoriesAPI.registerAccessory(ModItems.SOULMANCERS_ROBE.get(), new SoulmancersRobeAccessory());
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if(!(reference.entity() instanceof ServerPlayer sPlayer)) return;
        if(stack.isEmpty()) return;

        SoulmancersRobeItem.commonTick(sPlayer, stack, true);
    }

}
