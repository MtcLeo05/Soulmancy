package com.leo.soulmancy.compat.accessories;

import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.init.ModItems;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

public class SightAccessory implements Accessory {
    private final boolean clearVision;

    public SightAccessory(boolean clearVision) {
        this.clearVision = clearVision;
    }

    public static void init(){
        AccessoriesAPI.registerAccessory(ModItems.REVEALING_EYE.get(), new SightAccessory(false));
        AccessoriesAPI.registerAccessory(ModItems.SIGHT_LENS.get(), new SightAccessory(true));
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        if(!(reference.entity() instanceof ServerPlayer sPlayer)) return;

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
    public void onUnequip(ItemStack stack, SlotReference reference) {
        if(!(reference.entity() instanceof ServerPlayer sPlayer)) return;

        PlayerData data = sPlayer.getData(PLAYER_DATA_ATTACHMENT);

        data.pureVision = false;
        data.eyeEquipped = false;

        sPlayer.setData(PLAYER_DATA_ATTACHMENT, data);
        PacketDistributor.sendToPlayer(sPlayer, data);
    }

}
