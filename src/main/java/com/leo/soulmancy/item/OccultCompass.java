package com.leo.soulmancy.item;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModDataComponents;
import com.leo.soulmancy.worldgen.biome.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class OccultCompass extends Item {
    public OccultCompass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if (level instanceof ServerLevel sLevel) {
            Pair<BlockPos, Holder<Biome>> closestBiome = sLevel.findClosestBiome3d(
                (biomeP) -> biomeP.is(ModBiomes.EBONY_FOREST),
                player.blockPosition(),
                6400, 32, 64
            );

            if(closestBiome != null) {
                player.getItemInHand(useHand).set(ModDataComponents.BIOME_POS, closestBiome.getFirst());
            } else {
                player.displayClientMessage(
                    Component.translatable(Soulmancy.MODID + ".item.noBiomeFound").withColor(0xAA0000),
                    true
                );
            }
        }

        return super.use(level, player, useHand);
    }
}
