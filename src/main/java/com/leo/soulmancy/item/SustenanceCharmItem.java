package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.util.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SustenanceCharmItem extends BaseCuriosItem {
    public SustenanceCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onCuriosEquip(SlotContext slotContext, ItemStack prevStack) {}

    @Override
    public void onCuriosUnequip(SlotContext slotContext, ItemStack newStack) {}

    @Override
    public void onCuriosTick(SlotContext slotContext) {
        if(slotContext.cosmetic()) return;
        if(!(slotContext.entity() instanceof ServerPlayer sPlayer)) return;

        ServerLevel sLevel = sPlayer.serverLevel();
        AtomicReference<ItemStack> curios = new AtomicReference<>(ItemStack.EMPTY);
        CuriosApi.getCuriosInventory(sPlayer).ifPresent(i -> {
            i.findCurio(slotContext.identifier(), slotContext.index()).ifPresent(c -> curios.set(c.stack()));
        });

        ItemStack stack = curios.get();
        if(stack.isEmpty()) return;

        int cd = stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);
        cd++;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, cd);

        if(!sPlayer.getFoodData().needsFood()) return;

        if(stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0) < (isFastMode(stack) ? 5: 20)) return;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);

        ItemStack soulContainerInPlayer = Utils.getSoulContainerInPlayer(sPlayer, getConsume(stack));

        if(!soulContainerInPlayer.isEmpty()) {
            int soulInContainer = SoulContainer.getSoul(soulContainerInPlayer)[0];

            if(soulInContainer >= getConsume(stack)) {
                SoulContainer.removeSoul(soulContainerInPlayer, getConsume(stack));
                sPlayer.getFoodData().eat(1, 1);
            }
            return;
        }

        if(Utils.getSoulInChunk(sPlayer.blockPosition(), sLevel) < getConsume(stack) * 4) return;

        Utils.removeSoulToChunk(sPlayer.blockPosition(), getConsume(stack) * 4, sLevel);
        sPlayer.getFoodData().eat(1, 1);
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

    private int getConsume(ItemStack stack){
        return isFastMode(stack) ? 4: 1;
    }

    private boolean isFastMode(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.GENERIC_MODE, false);
    }
}
