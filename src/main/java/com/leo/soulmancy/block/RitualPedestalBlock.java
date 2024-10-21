package com.leo.soulmancy.block;

import com.leo.soulmancy.block.entity.RitualPedestalBE;
import com.leo.soulmancy.block.entity.SoulCanalizerBE;
import com.leo.soulmancy.util.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RitualPedestalBlock extends PedestalBlock {
    public RitualPedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(RitualPedestalBlock::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(player.isCrouching()){
            if (level.isClientSide) return InteractionResult.SUCCESS_NO_ITEM_USED;
            if (level.getBlockEntity(pos) == null) return InteractionResult.SUCCESS_NO_ITEM_USED;

            RitualPedestalBE be = (RitualPedestalBE) level.getBlockEntity(pos);
            be.toggleShowRange();
            return InteractionResult.CONSUME;
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(player.isCrouching()){
            if (level.isClientSide) return ItemInteractionResult.CONSUME;
            if (level.getBlockEntity(pos) == null) return ItemInteractionResult.CONSUME;

            RitualPedestalBE be = (RitualPedestalBE) level.getBlockEntity(pos);
            be.toggleShowRange();
            return ItemInteractionResult.CONSUME;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RitualPedestalBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide) return null;

        return (RitualPedestalBlock::tick);
    }

    private static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((RitualPedestalBE) t).tick(level);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
            ShapeUtil.shapeFromDimension(2, 0, 2, 12, 1, 12),
            ShapeUtil.shapeFromDimension(3, 1, 3, 10, 1, 10),
            ShapeUtil.shapeFromDimension(6, 2, 6, 4, 9, 4),
            ShapeUtil.shapeFromDimension(4, 11, 4, 8, 1, 8),
            ShapeUtil.shapeFromDimension(5, 12, 5, 6, 1, 6),
            ShapeUtil.shapeFromDimension(7, 13, 7, 2, 0.25f, 2)
        );
    }
}
