package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.stream.Stream;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Soulmancy.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("items", () ->
        CreativeModeTab.builder()
            .title(Component.translatable(Soulmancy.MODID + ".itemGroup.main"))
            .icon(ModItems.ONYX.get()::getDefaultInstance)
            .displayItems((idp, output) -> {
                Stream<Item> items = ModItems.ITEMS.getEntries().stream().map(DeferredHolder::get);

                items.forEach(output::accept);

                Stream<Block> blocks = ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get);

                blocks.forEach(output::accept);
            })
            .build()
    );
}
