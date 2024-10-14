package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.config.AccessoriesConfigs;
import com.leo.soulmancy.init.ModArmorMaterials;
import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.util.Utils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (!Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable(Soulmancy.MODID + ".item.moreInfo"));
            return;
        }

        tooltipComponents.addAll(detailedInfo(stack));
    }

    public static void commonTick(ServerPlayer sPlayer, ItemStack stack, boolean isCurios) {
        int cd = stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);
        cd++;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, cd);

        int duration = isCurios ? AccessoriesConfigs.ROBE_SPEED_A.get(): AccessoriesConfigs.ROBE_SPEED.get();

        if(stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0) < duration) return;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);

        int soul = isCurios ? AccessoriesConfigs.ROBE_PRODUCE_A.get(): AccessoriesConfigs.ROBE_PRODUCE.get();

        ItemStack soulContainerInPlayer = Utils.getNonFullSoulContainerInPlayer(sPlayer, soul);

        if(!soulContainerInPlayer.isEmpty()) SoulContainer.addSoul(soulContainerInPlayer, soul);
    }
}
