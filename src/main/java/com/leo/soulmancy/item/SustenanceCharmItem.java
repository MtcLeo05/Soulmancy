package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.config.AccessoriesConfigs;
import com.leo.soulmancy.init.ModDataComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SustenanceCharmItem extends Item implements BaseAccessory {
    public SustenanceCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        boolean fastMode = itemInHand.getOrDefault(ModDataComponents.GENERIC_MODE, false);

        itemInHand.set(ModDataComponents.GENERIC_MODE, !fastMode);
        itemInHand.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, !fastMode);

        return super.use(level, player, usedHand);
    }

    @Override
    public List<Component> detailedInfo(ItemStack stack) {
        List<Component> toReturn = new ArrayList<>();

        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.soulConsume", getConsume(stack), isFastMode(stack) ? 5: 20).withColor(0xFF9f00fe));
        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.soulChunk", getConsume(stack) * 4).withColor(0xFF3b005e));

        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.rClick").withColor(0xFFDD0000));

        toReturn.add(Component.empty());

        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.sustenance"));
        return toReturn;
    }

    public static int getConsume(ItemStack stack){
        return isFastMode(stack) ? AccessoriesConfigs.SUSTENANCE_FAST_CONSUME.get() : AccessoriesConfigs.SUSTENANCE_SLOW_CONSUME.get();
    }

    public static int getCooldown(ItemStack stack){
        return isFastMode(stack)? AccessoriesConfigs.SUSTENANCE_FAST_SPEED.get() : AccessoriesConfigs.SUSTENANCE_SLOW_SPEED.get();
    }

    public static boolean isFastMode(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.GENERIC_MODE, false);
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
