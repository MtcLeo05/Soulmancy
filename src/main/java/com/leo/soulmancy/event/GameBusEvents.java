package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.SoulData;
import com.leo.soulmancy.datagen.ModEnchantments;
import com.leo.soulmancy.util.Utils;
import com.leo.soulmancy.worldgen.biome.ModTerrablender;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.leo.soulmancy.init.ModAttachmentTypes.PLAYER_DATA_ATTACHMENT;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameBusEvents {

    //Only check once in a while to prevent lag
    public static int cd = 0, maxCD = 10;

    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent.Post event){
        if(event.getEntity().level().isClientSide) return;
        if(++cd < maxCD) return;

        cd = 0;
        Player player = event.getEntity();
        BlockPos pos = player.getOnPos();
        Level level = player.level();
        BlockPos cPos = level.getChunkAt(pos).getPos().getMiddleBlockPosition(pos.getY());
        SoulData data = SoulData.getOrCreateData(pos, level, level.getBiome(cPos).getKey());
        PacketDistributor.sendToPlayer((ServerPlayer) player, data);
    }

    @SubscribeEvent
    public static void handleSoulReapingEnchantment(LivingDeathEvent event){
        Entity possiblePlayer = event.getSource().getDirectEntity();
        LivingEntity killedEntity = event.getEntity();

        if(!(possiblePlayer instanceof ServerPlayer sPlayer)) return;

        ItemStack weaponItem = event.getSource().getWeaponItem();

        if(weaponItem == null || weaponItem.isEmpty()) return;

        var enchHolder = Utils.getEnchantmentHolder(sPlayer.serverLevel().registryAccess(), ModEnchantments.SOUL_REAPING);

        if(enchHolder == null) return;

        int level = weaponItem.getEnchantmentLevel(enchHolder);

        int soulsToGive = (int) (killedEntity.getMaxHealth() * 5 * level / 100);
        Utils.addSoulToChunk(possiblePlayer.blockPosition(), soulsToGive, sPlayer.serverLevel());
    }
}
