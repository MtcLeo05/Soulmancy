package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.item.ModTiers;
import com.leo.soulmancy.item.OccultCompass;
import com.leo.soulmancy.item.SoulContainer;
import com.leo.soulmancy.item.SoulScythe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

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
        () -> new Item(
            new Item.Properties()
                .stacksTo(1)
        )
    );

    public static final DeferredHolder<Item, Item> SIGHT_LENS = ITEMS.register("sight_lens",
        () -> new Item(
            new Item.Properties()
                .stacksTo(1)
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
}
