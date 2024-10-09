package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.config.ServerConfig;
import com.leo.soulmancy.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class SoulCanalizerBE extends BaseSoulInteractor{

    private boolean showRange = false;

    private int x = 0, y = 0,z = 0;

    public SoulCanalizerBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_CANALIZER.get(), pos, blockState);
    }

    @Override
    protected int getRecipeDuration() {
        return ServerConfig.soulCanalizerSpeed;
    }

    @Override
    public void dropContents() {}

    private int getHorizontalRange(){
        return ServerConfig.soulCanalizerX;
    }

    private int getVerticalRange(){
        return ServerConfig.soulCanalizerY;
    }

    @Override
    public void tick(Level level) {
        if(!(level instanceof ServerLevel sLevel)) return;
        if(++progress < getRecipeDuration()) return;

        updateRange();

        BlockPos pos = getBlockPos();

        //Cannot use level.getBlockStates(AABB) since I need the pos for some logic
        for (int x = -getHorizontalRange(); x <= getHorizontalRange(); x++) {
            for (int z = -getHorizontalRange(); z <= getHorizontalRange(); z++) {
                for (int y = -getVerticalRange(); y <= getVerticalRange(); y++) {
                    if(x == 0 && y == 0 && z == 0) continue;

                    BlockPos toUse = pos.north(x).above(y).east(z);

                    BlockState state = level.getBlockState(toUse);

                    if(!doesChunkHaveSoul(getSoulToConsume(state))) continue;

                    boolean canContinue = canContinue(level, state, toUse);

                    if (!canContinue) continue;

                    if(state.is(Blocks.AMETHYST_BLOCK)) {
                        double chance = level.random.nextDouble();
                        if(ServerConfig.soulCanalizerConvertChance > 0 && chance < ServerConfig.soulCanalizerConvertChance) {
                            level.setBlockAndUpdate(toUse, Blocks.BUDDING_AMETHYST.defaultBlockState());
                            removeSoulToChunk(getSoulToConsume(state));
                        }

                        continue;
                    }

                    state.randomTick(sLevel, toUse, sLevel.random);
                    removeSoulToChunk(getSoulToConsume(state));
                }
            }
        }
    }

    private void updateRange(){
        if(level.isClientSide) return;

        x = z = getHorizontalRange();
        y = getVerticalRange();

        sync();
    }

    public void toggleShowRange() {
        this.showRange = !showRange;
        sync();
    }

    public boolean showRange() {
        return showRange;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putIntArray("range", new int[]{x, z, y});
        tag.putBoolean("showRange", showRange);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        int[] range = tag.getIntArray("range");

        x = range[0];
        z = range[1];
        y = range[2];

        showRange = tag.getBoolean("showRange");
    }

    private boolean canContinue(Level level, BlockState state, BlockPos pos){
        Block block = state.getBlock();

        if(block instanceof CropBlock cb) {
            return !cb.isMaxAge(state) && CropBlock.hasSufficientLight(level, pos);
        }

        if(block instanceof CactusBlock || block instanceof SugarCaneBlock) {
            return level.isEmptyBlock(pos.above()) && !level.getBlockState(pos.below(2)).is(block);
        }

        return block instanceof AmethystBlock;
    }

    private int getSoulToConsume(BlockState state){
        Block block = state.getBlock();
        boolean isCrop = block instanceof CropBlock || block instanceof CactusBlock || block instanceof SugarCaneBlock;
        boolean isAmethyst = block instanceof BuddingAmethystBlock;

        if(state.is(Blocks.AMETHYST_BLOCK)) return ServerConfig.soulCanalizerConvertSoul;

        if(!isCrop && !isAmethyst) return 0;

        return isCrop? ServerConfig.soulCanalizerCropSoul: ServerConfig.soulCanalizerAmethystSoul;
    }

    public int[] getRange() {
        return new int[]{x, z, y};
    }
}
