package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.BaseCurios;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        List<Item> curios = new ArrayList<>();
        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
            if(entry.get() instanceof BaseCurios) curios.add(entry.get());
        }
        event.registerItem(
            CuriosCapability.ITEM,
            (stack, context) ->
            new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                @Override
                public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                    if(slotContext.cosmetic()) return;
                    if(!(slotContext.entity() instanceof ServerPlayer)) return;
                    if(!(stack.getItem() instanceof BaseCurios bci)) return;

                    bci.onCuriosEquip(slotContext, prevStack);
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    if(slotContext.cosmetic()) return;
                    if(!(slotContext.entity() instanceof ServerPlayer)) return;
                    if(!(stack.getItem() instanceof BaseCurios bci)) return;

                    bci.onCuriosUnequip(slotContext, newStack);
                }

                @Override
                public void curioTick(SlotContext slotContext) {
                    if(slotContext.cosmetic()) return;
                    if(!(slotContext.entity() instanceof ServerPlayer)) return;

                    if(!(stack.getItem() instanceof BaseCurios bci)) return;

                    bci.onCuriosTick(slotContext);
                }
            },
            curios.toArray(new Item[]{})
        );

        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ModBlockEntities.SOUL_MANIPULATOR.get(),
            ((o, direction) -> o.getInventory())
        );

        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ModBlockEntities.SOUL_SMELTERY.get(),
            ((o, direction) -> o.getInventory())
        );
    }

}
