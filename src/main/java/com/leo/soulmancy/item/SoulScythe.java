package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.config.ServerConfig;
import com.leo.soulmancy.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class SoulScythe extends SwordItem {
    public SoulScythe(Properties properties) {
        super(ModTiers.SCYTHE_TIER, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
        if(!entity.isDeadOrDying()) return super.hurtEnemy(stack, entity, attacker);

        if(entity instanceof ServerPlayer sPlayer) {
            double vessel = sPlayer.getMaxHealth() + (sPlayer.getMaxHealth() * ServerConfig.vesselFromKill / 100);

            Utils.addVesselToChunk(entity.blockPosition(), (int) vessel, sPlayer.serverLevel());
            return true;
        }

        double soul = entity.getMaxHealth() + (entity.getMaxHealth() * ServerConfig.soulFromKill / 100);

        Utils.addSoulToChunk(entity.blockPosition(), (int) soul, entity.level());
        return true;
    }

    public static @NotNull ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float speed) {
        return ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(
                Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "scythe_range"),
                    1.5d,
                    AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
            )
            .build();
    }
}
