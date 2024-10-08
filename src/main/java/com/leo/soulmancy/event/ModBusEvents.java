package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

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
                    if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

                    PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);
                    data.eyeEquipped = true;
                    sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
                    PacketDistributor.sendToPlayer(sPlayer, data);
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    if(slotContext.cosmetic()) return;
                    if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

                    PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);
                    data.eyeEquipped = false;
                    sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
                    PacketDistributor.sendToPlayer(sPlayer, data);
                }
            },
            ModItems.REVEALING_EYE.get()
        );

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
                        if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

                        PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);
                        data.pureVision = true;
                        sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
                        PacketDistributor.sendToPlayer(sPlayer, data);
                    }

                    @Override
                    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                        if(slotContext.cosmetic()) return;
                        if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

                        PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);
                        data.pureVision = false;
                        sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
                        PacketDistributor.sendToPlayer(sPlayer, data);
                    }
                },
            ModItems.SIGHT_LENS.get()
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
