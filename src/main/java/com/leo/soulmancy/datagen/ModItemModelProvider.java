package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
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

        //Calls the function 31 times, while keeping the format with 2 digits
        for (int i = 0; i <= 31; i++) {
            simpleItem("compass_" + String.format("%02d", i));
        }

        withExistingParent(ModItems.SOUL_CAPSULE.getId().getPath(),
            ResourceLocation.withDefaultNamespace("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item/soul_capsule0"))
            .texture("layer1", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item/soul_capsule1"));

        withExistingParent(ModItems.CAPSULE_STACK.getId().getPath(),
            ResourceLocation.withDefaultNamespace("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item/capsule_stack0"))
            .texture("layer1", ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "item/capsule_stack1"));
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
