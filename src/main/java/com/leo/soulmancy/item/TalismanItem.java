package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TalismanItem extends Item implements BaseAccessory {
    private final Map<Holder<MobEffect>, Integer> effects;
    private final int soulConsumed;
    private final float chunkMultiplier;

    public TalismanItem(Properties properties, Map<Holder<MobEffect>, Integer> effects, int soulConsumed, float chunkMultiplier) {
        super(properties);
        this.effects = effects;
        this.soulConsumed = soulConsumed;
        this.chunkMultiplier = chunkMultiplier;
    }

    //I'm lazy
    public Object[] getDetails(){
        return new Object[]{effects, soulConsumed, chunkMultiplier};
    }

    @Override
    public List<Component> detailedInfo(ItemStack stack) {
        List<Component> toReturn = new ArrayList<>();

        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.soulConsume", soulConsumed, 18).withColor(0xFF9f00fe));
        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.soulChunk", (int) (soulConsumed * chunkMultiplier)).withColor(0xFF3b005e));

        toReturn.add(Component.translatable(Soulmancy.MODID + ".item.talisman.effectList"));

        effects.forEach((e, i) -> {
            String effectName = Component.translatable("effect." + e.getKey().location().getNamespace() + "." + e.getKey().location().getPath()).getString();

            toReturn.add(Component.literal("- ").append(Component.translatable(Soulmancy.MODID + ".item.talisman.effect", effectName, i)));
        });

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
