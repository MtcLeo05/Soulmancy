package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.BaseCuriosItem;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
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
                    if(!(stack.getItem() instanceof BaseCuriosItem bci)) return;

                    bci.onCuriosEquip(slotContext, prevStack);
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    if(slotContext.cosmetic()) return;
                    if(!(slotContext.entity() instanceof ServerPlayer)) return;
                    if(!(stack.getItem() instanceof BaseCuriosItem bci)) return;

                    bci.onCuriosUnequip(slotContext, newStack);
                }

                @Override
                public void curioTick(SlotContext slotContext) {
                    if(slotContext.cosmetic()) return;
                    if(!(slotContext.entity() instanceof ServerPlayer)) return;

                    if(!(stack.getItem() instanceof BaseCuriosItem bci)) return;

                    bci.onCuriosTick(slotContext);
                }
            },
            ModItems.REVEALING_EYE.get(),
            ModItems.SIGHT_LENS.get(),
            ModItems.MINERS_TALISMAN1.get(),
            ModItems.MINERS_TALISMAN2.get(),
            ModItems.MINERS_TALISMAN3.get(),
            ModItems.FIGHTERS_TALISMAN1.get(),
            ModItems.FIGHTERS_TALISMAN2.get(),
            ModItems.FIGHTERS_TALISMAN3.get(),
            ModItems.RUNNERS_TALISMAN1.get(),
            ModItems.RUNNERS_TALISMAN2.get()
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
