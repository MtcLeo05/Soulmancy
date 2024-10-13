package com.leo.soulmancy.compat.accessories;

import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.item.SustenanceCharmItem;
import com.leo.soulmancy.util.Utils;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class SustenanceCharmAccessory implements Accessory {
    public static void init(){
        AccessoriesAPI.registerAccessory(ModItems.SUSTENANCE_CHARM.get(), new SustenanceCharmAccessory());
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if(!(reference.entity() instanceof ServerPlayer sPlayer)) return;

        ServerLevel sLevel = sPlayer.serverLevel();
        if(stack.isEmpty()) return;

        int cd = stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);
        cd++;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, cd);

        if(!sPlayer.getFoodData().needsFood()) return;

        if(stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0) < (SustenanceCharmItem.isFastMode(stack) ? 5: 20)) return;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);

        ItemStack soulContainerInPlayer = Utils.getSoulContainerInPlayer(sPlayer, SustenanceCharmItem.getConsume(stack));

        if(!soulContainerInPlayer.isEmpty()) {
            int soulInContainer = SoulContainer.getSoul(soulContainerInPlayer)[0];

            if(soulInContainer >= SustenanceCharmItem.getConsume(stack)) {
                SoulContainer.removeSoul(soulContainerInPlayer, SustenanceCharmItem.getConsume(stack));
                sPlayer.getFoodData().eat(1, 1);
            }
            return;
        }

        if(Utils.getSoulInChunk(sPlayer.blockPosition(), sLevel) < SustenanceCharmItem.getConsume(stack) * 4) return;

        Utils.removeSoulToChunk(sPlayer.blockPosition(), SustenanceCharmItem.getConsume(stack) * 4, sLevel);
        sPlayer.getFoodData().eat(1, 1);
    }

}
