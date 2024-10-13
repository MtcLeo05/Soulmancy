package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.compat.accessories.SightAccessory;
import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.init.ModItems;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

public class SightItem extends Item implements BaseAccessory {
    private final boolean clearVision;

    public SightItem(Properties properties, boolean clearVision) {
        super(properties);
        this.clearVision = clearVision;
    }

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
