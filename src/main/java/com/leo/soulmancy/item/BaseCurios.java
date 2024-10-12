package com.leo.soulmancy.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public interface BaseCurios {
    void onCuriosEquip(SlotContext slotContext, ItemStack prevStack);
    void onCuriosUnequip(SlotContext slotContext, ItemStack newStack);
    void onCuriosTick(SlotContext slotContext);

    List<Component> detailedInfo(ItemStack stack);
}
