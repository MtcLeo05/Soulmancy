package com.leo.soulmancy.block;

import com.leo.soulmancy.block.entity.SoulManipulatorBE;
import com.leo.soulmancy.util.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SoulManipulatorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SoulManipulatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(SoulManipulatorBlock::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (player instanceof ServerPlayer sPlayer) {
            sPlayer.openMenu(state.getMenuProvider(level, pos), p -> p.writeBlockPos(pos));
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SoulManipulatorBE(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide) return null;

        return (SoulManipulatorBlock::tick);
    }

    private static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((SoulManipulatorBE) t).tick(level);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }


    private final VoxelShape shape = Shapes.or(
        ShapeUtil.shapeFromDimension(2, 0, 2, 12, 1, 12),
        ShapeUtil.shapeFromDimension(3, 1, 3, 0, 1, 10),
        ShapeUtil.shapeFromDimension(6, 2, 6, 4, 9, 4),
        ShapeUtil.shapeFromDimension(5, 11, 5, 6, 2, 6),
        ShapeUtil.shapeFromDimension(4, 13, 4, 8, 1, 8),
        ShapeUtil.shapeFromDimension(12, 13, 6, 3, 1, 4),
        ShapeUtil.shapeFromDimension(1, 13, 6, 3, 1, 4)
    );

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);

        return switch (facing) {
            case SOUTH -> ShapeUtil.rotateTwice(shape);
            case WEST -> ShapeUtil.counterClockwise(shape);
            case EAST -> ShapeUtil.clockwise(shape);
            default -> shape;
        };
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
