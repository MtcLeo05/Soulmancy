package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.block.SoulSmelteryBlock;
import com.leo.soulmancy.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {

    ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Soulmancy.MODID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        List<Block> toIgnore = new ArrayList<>();
        toIgnore.add(ModBlocks.EBONY_LOG.get());
        toIgnore.add(ModBlocks.EYED_EBONY_LOG.get());
        toIgnore.add(ModBlocks.EBONY_FENCE.get());
        toIgnore.add(ModBlocks.EBONY_BUTTON.get());
        toIgnore.add(ModBlocks.EYED_EBONY_LOG.get());
        toIgnore.add(ModBlocks.EBONY_DOOR.get());
        toIgnore.add(ModBlocks.EBONY_TRAPDOOR.get());

        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
            if(toIgnore.contains(entry.get())) continue;

            simpleBlockItem(entry.get(), model(entry));
        }

        logBlock(ModBlocks.STRIPPED_EBONY_LOG.get());

        woodBlock(ModBlocks.EBONY_WOOD.get(), ModBlocks.EBONY_LOG.get());
        woodBlock(ModBlocks.STRIPPED_EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_LOG.get());

        horizontalBlock(ModBlocks.SOUL_MANIPULATOR.get(), model(ModBlocks.SOUL_MANIPULATOR));

        simpleBlock(ModBlocks.EBONY_LEAVES.get(), model(ModBlocks.EBONY_LEAVES));
        saplingBlock(ModBlocks.EBONY_SAPLING);

        simpleBlock(ModBlocks.EBONY_PLANKS.get(), cubeAll(ModBlocks.EBONY_PLANKS.get()));

        stairsBlock(ModBlocks.EBONY_STAIRS.get(), texture(ModBlocks.EBONY_PLANKS));
        slabBlock(ModBlocks.EBONY_SLAB.get(), texture(ModBlocks.EBONY_PLANKS), texture(ModBlocks.EBONY_PLANKS));

        fenceBlock(ModBlocks.EBONY_FENCE.get(), texture(ModBlocks.EBONY_PLANKS));
        fenceGateBlock(ModBlocks.EBONY_FENCE_GATE.get(), texture(ModBlocks.EBONY_PLANKS));
        buttonBlock(ModBlocks.EBONY_BUTTON.get(), texture(ModBlocks.EBONY_PLANKS));
        pressurePlateBlock(ModBlocks.EBONY_PRESSURE_PLATE.get(), texture(ModBlocks.EBONY_PLANKS));

        simpleBlock(ModBlocks.ONYX_ORE.get(), cubeAll(ModBlocks.ONYX_ORE.get()));
        simpleBlock(ModBlocks.DEEPSLATE_ONYX_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_ONYX_ORE.get()));
        simpleBlock(ModBlocks.SOUL_STONE.get(), model(ModBlocks.SOUL_STONE));
        simpleBlock(ModBlocks.CONDENSED_SOUL.get(), cubeAll(ModBlocks.CONDENSED_SOUL.get()));

        simpleBlock(ModBlocks.ARTIFICIAL_ONYX_BLOCK.get(), cubeAll(ModBlocks.ARTIFICIAL_ONYX_BLOCK.get()));
        simpleBlock(ModBlocks.ONYX_BLOCK.get(), cubeAll(ModBlocks.ONYX_BLOCK.get()));

        simpleBlock(ModBlocks.SOUL_CANALIZER.get(), model(ModBlocks.SOUL_CANALIZER));
        simpleBlock(ModBlocks.SOUL_SACRIFICER.get(), model(ModBlocks.SOUL_SACRIFICER));

        doorBlock(ModBlocks.EBONY_DOOR.get(), modLoc("block/ebony_door_bottom"), modLoc("block/ebony_door_top"));
        trapdoorBlockWithRenderType(ModBlocks.EBONY_TRAPDOOR.get(), modLoc("block/ebony_trapdoor"), true, "cutout");

        soulSmeltery();
    }

    private void soulSmeltery(){
        this.getVariantBuilder(ModBlocks.SOUL_SMELTERY.get()).forAllStates(
            (state) -> {
                boolean isLit = state.getValue(SoulSmelteryBlock.LIT);

                StringBuilder path = new StringBuilder("block/");

                path.append("soul_smeltery");

                if(isLit) path.append("_on");

                ResourceLocation model = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, path.toString());

                return ConfiguredModel.builder()
                    .modelFile(model(model))
                    .rotationY(((int) state.getValue(SoulSmelteryBlock.FACING).toYRot()) % 360)
                    .build();
            }
        );
    }

    private void saplingBlock(DeferredHolder<Block, ? extends Block> block){
        simpleBlock(block.get(),
            models().cross(
                key(block.get()),
                blockTexture(block.get())
            ).renderType("cutout")
        );
    }

    private void woodBlock(RotatedPillarBlock block, Block original){
        axisBlock(block, blockTexture(original), blockTexture(original));
    }

    private static ResourceLocation texture(DeferredHolder<Block, ? extends Block> block) {
        return texture(block.getId().getPath());
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "block/" + name);
    }

    private static ModelFile model(DeferredHolder<Block, ? extends Block> block) {
        return model(texture(block));
    }

    private static ModelFile model(ResourceLocation model) {
        return new ModelFile.UncheckedModelFile(model);
    }

    private static String key(Block block){
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
