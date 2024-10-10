package com.leo.soulmancy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public abstract class BaseCuriosItem extends Item {
    public BaseCuriosItem(Properties properties) {
        super(properties);
    }

    public abstract void onCuriosEquip(SlotContext slotContext, ItemStack prevStack);
    public abstract void onCuriosUnequip(SlotContext slotContext, ItemStack newStack);

    public abstract void onCuriosTick(SlotContext slotContext);
}
