package com.leo.soulmancy.block;

import com.leo.soulmancy.block.entity.PedestalBE;
import com.leo.soulmancy.block.entity.SoulCanalizerBE;
import com.leo.soulmancy.block.entity.SoulManipulatorBE;
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

public class PedestalBlock extends BaseEntityBlock {
    public PedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PedestalBlock::new);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level.getBlockEntity(pos) != null) {
            ((PedestalBE) level.getBlockEntity(pos)).dropContents();
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide) return ItemInteractionResult.CONSUME;
        if(level.getBlockEntity(pos) == null) return ItemInteractionResult.CONSUME;

        PedestalBE be = (PedestalBE) level.getBlockEntity(pos);

        ItemStack remaining = be.interact(player.getItemInHand(hand));

        if(!remaining.isEmpty()) player.setItemInHand(hand, remaining);

        return ItemInteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PedestalBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
            ShapeUtil.shapeFromDimension(2, 0, 2, 12, 1, 12),
            ShapeUtil.shapeFromDimension(3, 1, 3, 10, 1, 10),
            ShapeUtil.shapeFromDimension(6, 2, 6, 4, 9, 4),
            ShapeUtil.shapeFromDimension(5, 11, 5, 6, 1, 6),
            ShapeUtil.shapeFromDimension(6, 12, 6, 4, 1, 4)
        );
    }
}
