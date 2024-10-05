package com.leo.soulmancy.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class EbonyLogBlock extends RotatedStrippableBlock {
    public static IntegerProperty VARIANT = IntegerProperty.create("variant", 1, 3);

    public EbonyLogBlock(Properties properties, Block stripBlock) {
        super(properties, stripBlock);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int variant = context.getLevel().random.nextInt(1, 4);

        return super.getStateForPlacement(context).setValue(VARIANT, variant);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }
}
