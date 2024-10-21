package com.leo.soulmancy.util;

import com.leo.soulmancy.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RitualStructure {

    private static final Block l = ModBlocks.STRIPPED_EBONY_LOG.get(), p = ModBlocks.EBONY_PLANKS.get(), c = ModBlocks.CONDENSED_SOUL.get(), o = ModBlocks.ONYX_BLOCK.get(), s = ModBlocks.EBONY_STAIRS.get();
    private static final Block[][] structure = new Block[][] {
        {
            Blocks.AIR, s, s, s, s, s, Blocks.AIR
        },
        {
            s, s, p, l, p, s, s
        },
        {
            s, p, c, l, c, p, s
        },
        {
            s, l, l, o, l, l, s
        },
        {
            s, p, c, l, c, p, s
        },
        {
            s, s, p, l, p, s, s
        },
        {
            Blocks.AIR, s, s, s, s, s, Blocks.AIR
        }
    };

    public static boolean hasStructure(BlockPos pos, Level level) {
        BlockPos start = pos.below().north(-3).east(-3);

        for (int row = 0; row < structure.length; row++) {
            for (int column = 0; column < structure[row].length; column++) {
                BlockPos toUse = start.north(column).east(row);

                if(!level.getBlockState(toUse).is(structure[row][column])) return false;
            }
        }

        return true;
    }

}
