package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public abstract class BaseCuriosItem extends Item {
    public BaseCuriosItem(Properties properties) {
        super(properties);
    }

    public abstract void onCuriosEquip(SlotContext slotContext, ItemStack prevStack);
    public abstract void onCuriosUnequip(SlotContext slotContext, ItemStack newStack);
    public abstract void onCuriosTick(SlotContext slotContext);

    public abstract List<Component> detailedInfo(ItemStack stack);

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (!Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable(Soulmancy.MODID + ".item.moreInfo"));
            return;
        }

        tooltipComponents.addAll(detailedInfo(stack));
    }
}
