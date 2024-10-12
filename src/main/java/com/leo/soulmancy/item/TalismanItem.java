package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class TalismanItem extends BaseCuriosItem {
    private final Map<Holder<MobEffect>, Integer> effects;
    private final int soulConsumed;
    private final float chunkMultiplier;

    public TalismanItem(Properties properties, Map<Holder<MobEffect>, Integer> effects, int soulConsumed, float chunkMultiplier) {
        super(properties);
        this.effects = effects;
        this.soulConsumed = soulConsumed;
        this.chunkMultiplier = chunkMultiplier;
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

        if(stack.getOrDefault(ModDataComponents.EQUIPPABLE_COOLDOWN, 0) < 18) return;
        stack.set(ModDataComponents.EQUIPPABLE_COOLDOWN, 0);

        ItemStack soulContainerInPlayer = Utils.getSoulContainerInPlayer(sPlayer, soulConsumed);

        if(!soulContainerInPlayer.isEmpty()) {
            int soulInContainer = SoulContainer.getSoul(soulContainerInPlayer)[0];

            if(soulInContainer >= soulConsumed) {
                SoulContainer.removeSoul(soulContainerInPlayer, soulConsumed);
                applyEffects(sPlayer);
            }
            return;
        }

        if(Utils.getSoulInChunk(sPlayer.blockPosition(), sLevel) < (soulConsumed* chunkMultiplier)) return;

        applyEffects(sPlayer);
        Utils.removeSoulToChunk(sPlayer.blockPosition(), (int) (soulConsumed * chunkMultiplier), sLevel);
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

    private void applyEffects(ServerPlayer player){
        effects.forEach((effect, level) -> {
            player.addEffect(
                new MobEffectInstance(
                    effect,
                    20,
                    level
                )
            );
        });
    }
}
