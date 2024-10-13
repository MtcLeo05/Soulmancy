package com.leo.soulmancy.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface BaseAccessory {
    List<Component> detailedInfo(ItemStack stack);
}
