package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Soulmancy.MODID);

    public static final DeferredHolder<Item, Item> ONYX = ITEMS.register("onyx",
        () -> new Item(
            new Item.Properties()
        )
    );

    public static final DeferredHolder<Item, Item> ARTIFICIAL_ONYX = ITEMS.register("artificial_onyx",
        () -> new Item(
            new Item.Properties()
        )
    );

    public static final DeferredHolder<Item, Item> OCCULT_COMPASS = ITEMS.register("occult_compass",
        () -> new OccultCompass(
            new Item.Properties()
                .stacksTo(1)
        )
    );

    public static final DeferredHolder<Item, Item> REVEALING_EYE = ITEMS.register("revealing_eye",
        () -> new SightItem(
            new Item.Properties()
                .stacksTo(1),
            false
        )
    );

    public static final DeferredHolder<Item, Item> SIGHT_LENS = ITEMS.register("sight_lens",
        () -> new SightItem(
            new Item.Properties()
                .stacksTo(1),
            true
        )
    );

    public static final DeferredHolder<Item, Item> SOUL_CAPSULE = ITEMS.register("soul_capsule",
        () -> new SoulContainer(
            new Item.Properties()
                .stacksTo(1),
            25
        )
    );

    public static final DeferredHolder<Item, Item> CAPSULE_STACK = ITEMS.register("capsule_stack",
        () -> new SoulContainer(
            new Item.Properties()
                .stacksTo(1),
            125
        )
    );

    public static final DeferredHolder<Item, Item> SOUL_SCYTHE = ITEMS.register("soul_scythe",
        () -> new SoulScythe(
            new Item.Properties()
                .stacksTo(1)
                .attributes(SoulScythe.createAttributes(ModTiers.SCYTHE_TIER, 1f, 0))
        )
    );

    public static final DeferredHolder<Item, TalismanItem> MINERS_TALISMAN1 = ITEMS.register("miners_talisman1",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.DIG_SPEED, 1),
            5,
            2.5f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> MINERS_TALISMAN2 = ITEMS.register("miners_talisman2",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.DIG_SPEED, 2),
            10,
            2.5f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> MINERS_TALISMAN3 = ITEMS.register("miners_talisman3",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.DIG_SPEED, 3),
            15,
            2.5f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> FIGHTERS_TALISMAN1 = ITEMS.register("fighters_talisman1",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.DAMAGE_BOOST, 1),
            8,
            3f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> FIGHTERS_TALISMAN2 = ITEMS.register("fighters_talisman2",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.DAMAGE_BOOST, 2),
            16,
            3f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> FIGHTERS_TALISMAN3 = ITEMS.register("fighters_talisman3",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.DAMAGE_BOOST, 3, MobEffects.DAMAGE_RESISTANCE, 1),
            24,
            3f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> RUNNERS_TALISMAN1 = ITEMS.register("runners_talisman1",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.MOVEMENT_SPEED, 2, MobEffects.REGENERATION, 1),
            12,
            2f
        )
    );

    public static final DeferredHolder<Item, TalismanItem> RUNNERS_TALISMAN2 = ITEMS.register("runners_talisman2",
        () -> new TalismanItem(
            new Item.Properties().stacksTo(1),
            Map.of(MobEffects.MOVEMENT_SPEED, 4, MobEffects.REGENERATION, 2),
            24,
            2f
        )
    );

    public static final DeferredHolder<Item, SustenanceCharmItem> SUSTENANCE_CHARM = ITEMS.register("sustenance_charm",
        () -> new SustenanceCharmItem(
            new Item.Properties().stacksTo(1)
        )
    );

    public static final DeferredHolder<Item, SoulmancersRobeItem> SOULMANCERS_ROBE = ITEMS.register("soulmancers_robe",
        () -> new SoulmancersRobeItem(
            new Item.Properties().stacksTo(1)
        )
    );

}
