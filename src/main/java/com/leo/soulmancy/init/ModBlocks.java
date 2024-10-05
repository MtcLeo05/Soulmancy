package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Soulmancy.MODID);

    public static DeferredHolder<Block, RotatedPillarBlock> STRIPPED_EBONY_LOG = registerBlock("stripped_ebony_log",
        () -> new RotatedPillarBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        )
    );

    public static DeferredHolder<Block, RotatedPillarBlock> STRIPPED_EBONY_WOOD = registerBlock("stripped_ebony_wood",
        () -> new RotatedPillarBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        )
    );

    public static DeferredHolder<Block, RotatedPillarBlock> EBONY_LOG = registerBlock("ebony_log",
        () -> new EbonyLogBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava(),
            ModBlocks.STRIPPED_EBONY_LOG.get()
        )
    );

    public static DeferredHolder<Block, HorizontalFacingBlock> EYED_EBONY_LOG = registerBlock("eyed_ebony_log",
        () -> new EyedEbonyLogBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        )
    );

    public static DeferredHolder<Block, RotatedPillarBlock> EBONY_WOOD = registerBlock("ebony_wood",
        () -> new RotatedStrippableBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava(),
            ModBlocks.STRIPPED_EBONY_WOOD.get()
        )
    );

    public static DeferredHolder<Block, Block> EBONY_LEAVES = registerBlock("ebony_leaves",
        () -> new LeavesBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn((state, level, pos, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor((state, level, pos) -> false)
        )
    );

    public static DeferredHolder<Block, SaplingBlock> EBONY_SAPLING = registerBlock("ebony_sapling",
        () -> new SaplingBlock(
            ModTrees.EBONY_TREE_GROWER,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
        )
    );

    public static DeferredHolder<Block, Block> EBONY_PLANKS = registerBlock("ebony_planks",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, StairBlock> EBONY_STAIRS = registerBlock("ebony_stairs",
        () -> new StairBlock(
            EBONY_PLANKS.get().defaultBlockState(),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, SlabBlock> EBONY_SLAB = registerBlock("ebony_slab",
        () -> new SlabBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, FenceBlock> EBONY_FENCE = registerBlock("ebony_fence",
        () -> new FenceBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static final BlockSetType EBONY_SET_TYPE = BlockSetType.register(new BlockSetType("ebony"));
    public static final WoodType EBONY_WOOD_TYPE = new WoodType("ebony", EBONY_SET_TYPE);

    public static DeferredHolder<Block, FenceGateBlock> EBONY_FENCE_GATE = registerBlock("ebony_fence_gate",
        () -> new FenceGateBlock(
            EBONY_WOOD_TYPE,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, ButtonBlock> EBONY_BUTTON = registerBlock("ebony_button",
        () -> new ButtonBlock(
            EBONY_SET_TYPE,
            30,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, PressurePlateBlock> EBONY_PRESSURE_PLATE = registerBlock("ebony_pressure_plate",
        () -> new PressurePlateBlock(
            EBONY_SET_TYPE,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, DoorBlock> EBONY_DOOR = registerBlock("ebony_door",
        () -> new DoorBlock(
            EBONY_SET_TYPE,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, TrapDoorBlock> EBONY_TRAPDOOR = registerBlock("ebony_trapdoor",
        () -> new TrapDoorBlock(
            EBONY_SET_TYPE,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.WOOD)
                .ignitedByLava()
                .strength(0.2F)
        )
    );

    public static DeferredHolder<Block, Block> ONYX_ORE = registerBlock("onyx_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(4, 6),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .sound(SoundType.STONE)
                .strength(3f)
                .requiresCorrectToolForDrops()
        )
    );

    public static DeferredHolder<Block, Block> DEEPSLATE_ONYX_ORE = registerBlock("deepslate_onyx_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(4, 6),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.DEEPSLATE)
                .sound(SoundType.DEEPSLATE)
                .strength(3f)
                .requiresCorrectToolForDrops()
        )
    );

    public static DeferredHolder<Block, Block> SOUL_STONE = registerBlock("soul_stone",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.DEEPSLATE)
                .sound(SoundType.DEEPSLATE)
                .strength(4f)
                .requiresCorrectToolForDrops()
        )
    );

    public static DeferredHolder<Block, Block> CONDENSED_SOUL = registerBlock("condensed_soul",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PURPLE)
                .sound(SoundType.AMETHYST)
                .strength(6f)
                .requiresCorrectToolForDrops()
        )
    );

    public static DeferredHolder<Block, SoulManipulatorBlock> SOUL_MANIPULATOR = registerBlock("soul_manipulator",
        () -> new SoulManipulatorBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.DEEPSLATE)
                .sound(SoundType.DEEPSLATE)
                .strength(3f)
                .requiresCorrectToolForDrops()
        )
    );

    public static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
