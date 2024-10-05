package com.leo.soulmancy.item;

import com.leo.soulmancy.data.SoulData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

import static com.leo.soulmancy.init.ModDataComponents.SOUL_DATA;

public class SoulContainer extends Item {
    public SoulContainer(Properties properties, int maxSoul) {
        super(properties.component(SOUL_DATA, new SoulData(0, maxSoul)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        int[] soul = SoulContainer.getSoul(stack);

        tooltipComponents.add(
            Component.literal(soul[0] + "/" + soul[1])
        );
    }

    public static int setSoul(ItemStack stack, int soul){
        SoulData data = stack.get(SOUL_DATA);
        if(data == null) return -1;

        int toSet = Math.min(soul, data.maxSoulValue());
        data = new SoulData(toSet, data.maxSoulValue());

        stack.set(SOUL_DATA, data);
        return soul - toSet;
    }

    /**
     * @return The amount not inserted
     */
    public static int addSoul(ItemStack stack, int soul){
        SoulData data = stack.get(SOUL_DATA);
        if(data == null) return -1;

        int toSet = Math.min(data.soulValue() + soul, data.maxSoulValue());
        data = new SoulData(toSet, data.maxSoulValue());

        stack.set(SOUL_DATA, data);
        return soul - toSet;
    }

    /**
     * @return the amount not removed
     */
    public static int removeSoul(ItemStack stack, int soul){
        SoulData data = stack.get(SOUL_DATA);
        if(data == null) return -1;

        int potSet = data.soulValue() - soul;

        data = new SoulData(Math.max(potSet, 0), data.maxSoulValue());

        stack.set(SOUL_DATA, data);
        return Math.abs(potSet);
    }

    public static int[] getSoul(ItemStack stack){
        SoulData data = stack.get(SOUL_DATA);
        if(data == null) return new int[]{-1, -1};

        return new int[]{data.soulValue(), data.maxSoulValue()};
    }
}
