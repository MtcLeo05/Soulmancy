package com.leo.soulmancy.item;

import com.leo.soulmancy.data.PlayerData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

public class SightItem extends BaseCuriosItem {
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
}
