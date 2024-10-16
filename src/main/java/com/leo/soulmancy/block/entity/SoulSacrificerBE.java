package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.config.MachineryConfigs;
import com.leo.soulmancy.init.ModBlockEntities;
import com.leo.soulmancy.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SoulSacrificerBE extends BaseSoulInteractor{

    private boolean showRange = false, mode = false;

    private int x = 0, y = 0, z = 0;

    public SoulSacrificerBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_SACRIFICER.get(), pos, blockState);
    }

    @Override
    protected int getRecipeDuration() {
        return MachineryConfigs.SOUL_SACRIFICER_SPEED.get();
    }

    @Override
    public void dropContents() {}

    private int getHorizontalRange(){
        return MachineryConfigs.SOUL_SACRIFICER_X.get();
    }

    private int getVerticalRange(){
        return MachineryConfigs.SOUL_SACRIFICER_Y.get();
    }

    private int getCost() {
        return MachineryConfigs.SOUL_SACRIFICER_CONSUME.get();
    }

    @Override
    public void tick(Level level) {
        if(!(level instanceof ServerLevel sLevel)) return;
        if(++progress < getRecipeDuration()) return;

        updateRange();
        BlockPos pos = this.getBlockPos();
        AABB range = new AABB(pos.getX() - x, pos.getY() - y, pos.getZ() - z, pos.getX() + x, pos.getY() + y, pos.getZ() + z);
        if (sLevel.registryAccess().lookup(Registries.ENTITY_TYPE).isEmpty()) return;

        List<LivingEntity> entitiesInRange = sLevel.getEntitiesOfClass(LivingEntity.class, range);

        if(Utils.getSoulInChunk(pos, sLevel) < getCost()) return;

        for (LivingEntity entity : entitiesInRange) {
            String id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
            if(MachineryConfigs.SOUL_SACRIFICER_ENTITIES.get().contains(id)) continue;

            Utils.removeSoulToChunk(pos, getCost(), sLevel);

            if(!mode) {
                entity.kill();
                continue;
            }

            float maxHealth = entity.getMaxHealth();
            entity.discard();

            if(sLevel.random.nextFloat() <= MachineryConfigs.SOUL_SACRIFICER_VESSEL_CHANCE.get()) {
                Utils.addVesselToChunk(
                    pos,
                    (int) (maxHealth * MachineryConfigs.SOUL_SACRIFICER_VESSEL_AMOUNT.get()),
                    sLevel
                );
            } else {
                Utils.addSoulToChunk(
                    pos,
                    (int) (maxHealth * MachineryConfigs.SOUL_SACRIFICER_PRODUCE.get()),
                    sLevel
                );
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

    public void toggleMode() {
        this.mode = !mode;
        sync();
    }

    public boolean showRange() {
        return showRange;
    }

    public boolean mode() {return mode;}

    public int[] getRange() {
        return new int[]{x, z, y};
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putIntArray("range", new int[]{x, z, y});
        tag.putBoolean("showRange", showRange);
        tag.putBoolean("mode", mode);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        int[] range = tag.getIntArray("range");

        x = range[0];
        z = range[1];
        y = range[2];

        showRange = tag.getBoolean("showRange");
        mode = tag.getBoolean("mode");
    }
}
