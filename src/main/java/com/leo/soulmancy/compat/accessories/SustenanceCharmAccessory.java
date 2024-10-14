package com.leo.soulmancy.compat.accessories;

import com.leo.soulmancy.config.AccessoriesConfigs;
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

        if(stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0) < SustenanceCharmItem.getCooldown(stack)) return;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);

        int soul = SustenanceCharmItem.getConsume(stack);

        ItemStack soulContainerInPlayer = Utils.getSoulContainerInPlayer(sPlayer, soul);

        int food = AccessoriesConfigs.SUSTENANCE_FOOD.get(), saturation = AccessoriesConfigs.SUSTENANCE_SATURATION.get();

        if(!soulContainerInPlayer.isEmpty()) {
            int soulInContainer = SoulContainer.getSoul(soulContainerInPlayer)[0];

            if(soulInContainer >= soul) {
                SoulContainer.removeSoul(soulContainerInPlayer, soul);
                sPlayer.getFoodData().eat(food, saturation);
            }
            return;
        }

        if(Utils.getSoulInChunk(sPlayer.blockPosition(), sLevel) < soul * 4) return;

        Utils.removeSoulToChunk(sPlayer.blockPosition(), soul * 4, sLevel);
        sPlayer.getFoodData().eat(food, saturation);
    }

}
