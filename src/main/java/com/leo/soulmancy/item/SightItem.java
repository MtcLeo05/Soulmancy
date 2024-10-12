package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.PlayerData;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

public class SightItem extends Item implements BaseCurios {
    private final boolean clearVision;

    public SightItem(Properties properties, boolean clearVision) {
        super(properties);
        this.clearVision = clearVision;
    }


    @Override
    public void onCuriosEquip(SlotContext slotContext, ItemStack prevStack) {
        if(slotContext.cosmetic()) return;
        if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

        PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);

        if(clearVision) {
            data.pureVision = true;
        } else {
            data.eyeEquipped = true;
        }

        sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
        PacketDistributor.sendToPlayer(sPlayer, data);
    }

    @Override
    public void onCuriosUnequip(SlotContext slotContext, ItemStack newStack) {
        if(slotContext.cosmetic()) return;
        if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

        PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);

        data.pureVision = false;
        data.eyeEquipped = false;

        sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
        PacketDistributor.sendToPlayer(sPlayer, data);
    }

    @Override
    public void onCuriosTick(SlotContext slotContext) {}

    @Override
    public List<Component> detailedInfo(ItemStack stack) {
        List<Component> toReturn = new ArrayList<>();
        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.sight." + (clearVision ? "pure": "impure")));
        return toReturn;
    }

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
