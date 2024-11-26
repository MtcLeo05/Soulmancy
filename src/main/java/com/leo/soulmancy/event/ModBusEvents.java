package com.leo.soulmancy.event;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.compat.accessories.SightAccessory;
import com.leo.soulmancy.compat.accessories.SoulmancersRobeAccessory;
import com.leo.soulmancy.compat.accessories.SustenanceCharmAccessory;
import com.leo.soulmancy.compat.accessories.TalismanAccessory;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.worldgen.biome.ModTerrablender;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ModBlockEntities.SOUL_MANIPULATOR.get(),
            ((o, direction) -> o.getInventory())
        );

        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ModBlockEntities.SOUL_SMELTERY.get(),
            ((o, direction) -> o.getInventory())
        );
    }

    @SubscribeEvent
    public static void registerAccessories(FMLCommonSetupEvent ignored){
        SightAccessory.init();
        SoulmancersRobeAccessory.init();
        SustenanceCharmAccessory.init();
        TalismanAccessory.init();
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ModTerrablender.registerBiomes();
    }
}
