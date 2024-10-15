package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, Soulmancy.MODID);

    public static final Holder<ArmorMaterial> SOULMANCER_ROBE_MATERIAL = ARMOR_MATERIALS.register(
        "soulmancers_robe",
        () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 0);
                map.put(ArmorItem.Type.LEGGINGS, 0);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 0);
                map.put(ArmorItem.Type.BODY, 0);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.of(ModItems.CRYSTALLIZED_SOUL.get()),
            List.of(
                new ArmorMaterial.Layer(
                    ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "soulmancers_robe")
                )
            ),
            0,
            0
        )
    );
}
