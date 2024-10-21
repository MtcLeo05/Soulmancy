package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.block.PedestalBlock;
import com.leo.soulmancy.block.RitualPedestalBlock;
import com.leo.soulmancy.block.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Soulmancy.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulManipulatorBE>> SOUL_MANIPULATOR = BLOCK_ENTITIES.register("soul_manipulator",
        () -> BlockEntityType.Builder.of(
            SoulManipulatorBE::new,
            ModBlocks.SOUL_MANIPULATOR.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulSmelteryBE>> SOUL_SMELTERY = BLOCK_ENTITIES.register("soul_smeltery",
        () -> BlockEntityType.Builder.of(
            SoulSmelteryBE::new,
            ModBlocks.SOUL_SMELTERY.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulCanalizerBE>> SOUL_CANALIZER = BLOCK_ENTITIES.register("soul_canalizer",
        () -> BlockEntityType.Builder.of(
            SoulCanalizerBE::new,
            ModBlocks.SOUL_CANALIZER.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulSacrificerBE>> SOUL_SACRIFICER = BLOCK_ENTITIES.register("soul_sacrificer",
        () -> BlockEntityType.Builder.of(
            SoulSacrificerBE::new,
            ModBlocks.SOUL_SACRIFICER.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalBE>> PEDESTAL = BLOCK_ENTITIES.register("pedestal",
        () -> BlockEntityType.Builder.of(
            PedestalBE::new,
            ModBlocks.PEDESTAL.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RitualPedestalBE>> RITUAL_PEDESTAL = BLOCK_ENTITIES.register("ritual_pedestal",
        () -> BlockEntityType.Builder.of(
            RitualPedestalBE::new,
            ModBlocks.RITUAL_PEDESTAL.get()
        ).build(null)
    );
}
