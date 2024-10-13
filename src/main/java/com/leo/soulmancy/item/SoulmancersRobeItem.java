package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModArmorMaterials;
import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SoulmancersRobeItem extends ArmorItem implements BaseAccessory {
    public SoulmancersRobeItem(Properties properties) {
        super(ModArmorMaterials.SOULMANCER_ROBE_MATERIAL, Type.CHESTPLATE, properties.durability(Type.CHESTPLATE.getDurability(15)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(!(entity instanceof ServerPlayer sPlayer)) return;

        ItemStack armor = sPlayer.getInventory().getArmor(2);

        if(armor.isEmpty() || !(armor.getItem() instanceof ArmorItem aItem)) return;

        if(!aItem.getMaterial().is(ModArmorMaterials.SOULMANCER_ROBE_MATERIAL.getKey())) return;

        SoulmancersRobeItem.commonTick(sPlayer, armor, false);
    }


    @Override
    public List<Component> detailedInfo(ItemStack stack) {
        return List.of(
            Component.translatable(Soulmancy.MODID + ".item.robeFlavor"),
            Component.translatable(Soulmancy.MODID + ".item.robeEffect"),
            Component.empty(),
            Component.translatable(Soulmancy.MODID + ".item.robeEffect2")
        );
    }

    public static void commonTick(ServerPlayer sPlayer, ItemStack stack, boolean isCurios) {
        int cd = stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);
        cd++;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, cd);

        if(stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0) < (isCurios ? 20: 200)) return;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);

        ItemStack soulContainerInPlayer = Utils.getNonFullSoulContainerInPlayer(sPlayer, isCurios ? 1: 12);

        if(!soulContainerInPlayer.isEmpty()) SoulContainer.addSoul(soulContainerInPlayer, isCurios ? 1: 12);
    }
}
