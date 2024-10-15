package com.leo.soulmancy.block;

import com.leo.soulmancy.block.entity.SoulSacrificerBE;
import com.leo.soulmancy.util.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class SoulSacrificerBlock extends BaseEntityBlock {
    public SoulSacrificerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(SoulSacrificerBlock::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) != null) {
            if(player.isCrouching()) {
                ((SoulSacrificerBE) level.getBlockEntity(pos)).toggleMode();
            } else {
                ((SoulSacrificerBE) level.getBlockEntity(pos)).toggleShowRange();
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SoulSacrificerBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide) return null;

        return (SoulSacrificerBlock::tick);
    }

    private static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((SoulSacrificerBE) t).tick(level);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
            ShapeUtil.shapeFromDimension(0, 14, 0, 16, 2, 16),
            ShapeUtil.shapeFromDimension(2, 2, 2, 12, 12, 12),
            ShapeUtil.shapeFromDimension(0, 0, 0, 16, 2, 16)
        );
    }
}
