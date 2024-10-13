package com.leo.soulmancy.compat.accessories;

import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.item.TalismanItem;
import com.leo.soulmancy.util.Utils;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class TalismanAccessory implements Accessory {
    private final Map<Holder<MobEffect>, Integer> effects;
    private final int soulConsumed;
    private final float chunkMultiplier;

    public TalismanAccessory(Object[] details) {
        this.effects = (Map<Holder<MobEffect>, Integer>) details[0];
        this.soulConsumed = (int) details[1];
        this.chunkMultiplier = (float) details[2];
    }

    public static void addTalisman(TalismanItem item){
        AccessoriesAPI.registerAccessory(item, new TalismanAccessory(item.getDetails()));
    }

    public static void init(){
        addTalisman(ModItems.MINERS_TALISMAN1.get());
        addTalisman(ModItems.MINERS_TALISMAN2.get());
        addTalisman(ModItems.MINERS_TALISMAN3.get());

        addTalisman(ModItems.FIGHTERS_TALISMAN1.get());
        addTalisman(ModItems.FIGHTERS_TALISMAN2.get());
        addTalisman(ModItems.FIGHTERS_TALISMAN3.get());

        addTalisman(ModItems.RUNNERS_TALISMAN1.get());
        addTalisman(ModItems.RUNNERS_TALISMAN2.get());
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if(!(reference.entity() instanceof ServerPlayer sPlayer)) return;
        ServerLevel sLevel = sPlayer.serverLevel();
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
