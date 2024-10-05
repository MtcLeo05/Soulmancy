package com.leo.soulmancy.worldgen.tree;

import com.google.common.collect.ImmutableList;
import com.leo.soulmancy.block.EbonyLogBlock;
import com.leo.soulmancy.block.EyedEbonyLogBlock;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModTrees;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class EbonyTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<EbonyTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
        instance -> trunkPlacerParts(instance).apply(instance, EbonyTrunkPlacer::new)
    );

    public EbonyTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrees.EBONY_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        boolean canPlaceEye = true;

        for (int i = 0; i < freeTreeHeight; i++) {
            //Change the variant based on the height
            int variant = i + random.nextInt(1, 4);
            //Mod 3 to make it [0, 2]
            variant %= 3;
            //+ 1 to make it [1, 3], the desired value
            variant++;

            if(canPlaceEye && random.nextFloat() <= 0.35f && i >= 2){
                Direction dir = Direction.getRandom(random);
                while(dir == Direction.UP || dir == Direction.DOWN){
                    dir = Direction.getRandom(random);
                }

                canPlaceEye = false;
                blockSetter.accept(pos.above(i),
                    ModBlocks.EYED_EBONY_LOG.get()
                        .defaultBlockState()
                        .setValue(HorizontalDirectionalBlock.FACING, dir)
                        .setValue(EyedEbonyLogBlock.VARIANT, variant)
                );
                continue;
            }

            blockSetter.accept(pos.above(i),
                config.trunkProvider.getState(random, pos)
                    .setValue(EbonyLogBlock.VARIANT, variant)
            );
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }
}
