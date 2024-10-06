package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> SOUL_REAPING = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "soul_reaping"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemHolderGetter = context.lookup(Registries.ITEM);

        context.register(
            SOUL_REAPING,
            Enchantment.enchantment(
                Enchantment.definition(
                    itemHolderGetter.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                    5,
                    3,
                    Enchantment.dynamicCost(5, 8),
                    Enchantment.dynamicCost(55, 8),
                    2,
                    EquipmentSlotGroup.MAINHAND
                )
            ).build(SOUL_REAPING.location())
        );
    }
}
