package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
import com.leo.soulmancy.item.TalismanItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Soulmancy.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        saplingItem(ModBlocks.EBONY_SAPLING);

        simpleItem(ModItems.ONYX);
        simpleItem(ModItems.ARTIFICIAL_ONYX);
        simpleItem(ModItems.REVEALING_EYE);
        simpleItem(ModItems.SIGHT_LENS);

        fenceItem(ModBlocks.EBONY_FENCE, ModBlocks.EBONY_PLANKS);
        buttonItem(ModBlocks.EBONY_BUTTON, ModBlocks.EBONY_PLANKS);

        simpleBlockItem(ModBlocks.EBONY_DOOR);

        trapdoorItem(ModBlocks.EBONY_TRAPDOOR);

        simpleItem2Textures(ModItems.SOUL_CAPSULE,"item/soul_capsule0","item/soul_capsule1");
        simpleItem2Textures(ModItems.CAPSULE_STACK,"item/capsule_stack0","item/capsule_stack1");

        simpleItem2Textures("miners_talisman1_item", "item/miners_talisman", "item/tlevel_1");
        simpleItem2Textures("miners_talisman2_item", "item/miners_talisman", "item/tlevel_2");
        simpleItem2Textures("miners_talisman3_item", "item/miners_talisman", "item/tlevel_3");

        simpleItem2Textures("fighters_talisman1_item", "item/fighters_talisman", "item/tlevel_1");
        simpleItem2Textures("fighters_talisman2_item", "item/fighters_talisman", "item/tlevel_2");
        simpleItem2Textures("fighters_talisman3_item", "item/fighters_talisman", "item/tlevel_3");

        simpleItem2Textures("runners_talisman1_item", "item/runners_talisman", "item/tlevel_1");
        simpleItem2Textures("runners_talisman2_item", "item/runners_talisman", "item/tlevel_2");

        for (int i = 0; i <= 31; i++) {
            simpleItem("compass_" + String.format("%02d", i));
        }
    }

    private void simpleItem2Textures(DeferredHolder<Item, ? extends Item> item, String texture1, String texture2) {
        simpleItem2Textures(item.getId().getPath(), texture1, texture2);
    }

    private void simpleItem2Textures(String name, String texture1, String texture2) {
        withExistingParent(name,
            ResourceLocation.withDefaultNamespace("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, texture1))
            .texture("layer1", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, texture2));
    }

    private void simpleBlockItem(DeferredHolder<Block, ? extends Block> item) {
        withExistingParent(item.getId().getPath(),
            ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
            ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item/" + item.getId().getPath()));
    }

    public void trapdoorItem(DeferredHolder<Block, ? extends Block> block) {
        this.withExistingParent(key(block.get()),
            modLoc("block/" + key(block.get()) + "_bottom"));
    }

    public void fenceItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> baseBlock) {
        this.withExistingParent(key(block.get()), mcLoc("block/fence_inventory"))
            .texture("texture",  ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "block/" + key(baseBlock.get())));
    }

    public void buttonItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> baseBlock) {
        this.withExistingParent(key(block.get()), mcLoc("block/button_inventory"))
            .texture("texture",  ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "block/" + key(baseBlock.get())));
    }

    private void saplingItem(DeferredHolder<Block, ? extends Block> block){
        withExistingParent(block.getId().getPath(),
            ResourceLocation.withDefaultNamespace("item/generated")
        ).texture("layer0", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "block/" + block.getId().getPath()));
    }

    private static String key(Block block){
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private void simpleItem(DeferredHolder<Item, ? extends Item> item) {
        simpleItem(item.getId().getPath());
    }

    private void simpleItem(String name) {
        withExistingParent(name,
            ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
            ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item/" + name));
    }
}
