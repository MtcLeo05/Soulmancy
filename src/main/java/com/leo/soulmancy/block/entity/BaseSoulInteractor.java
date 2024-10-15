package com.leo.soulmancy.block.entity;

import com.leo.soulmancy.data.SoulData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import static com.leo.soulmancy.init.ModAttachmentTypes.SOUL_DATA_ATTACHMENT;

public abstract class BaseSoulInteractor extends BlockEntity{
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case 0 -> progress;
                case 1 -> getSoulInChunk().soulValue();
                case 2 -> getSoulInChunk().maxSoulValue();
                case 3 -> getRecipeDuration();
                default -> -1;
            };
        }

        @Override
        public void set(int i, int i1) {

        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    protected ItemStackHandler itemHandler;
    protected int progress = 0;

    public BaseSoulInteractor(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(tag.contains("inventory")) {
            itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        }
        progress = tag.getInt("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if(itemHandler != null) {
            CompoundTag inventory = itemHandler.serializeNBT(registries);
            tag.put("inventory", inventory);
        }
        tag.putInt("progress", progress);
    }

    protected SoulData getSoulInChunk(){
        return getChunk().getData(SOUL_DATA_ATTACHMENT);
    }

    protected ChunkAccess getChunk(){
        return level.getChunk(getBlockPos());
    }

    public void sync(){
        setChanged();

        if(level != null){
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public ItemStackHandler getInventory() {
        return itemHandler;
    }

    protected abstract int getRecipeDuration();

    public abstract void dropContents();

    public void addSoulToChunk(int soul){
        SoulData sData = getSoulInChunk();

        int soulValue = sData.soulValue() + soul;
        soulValue = Math.clamp(soulValue, 0, sData.maxSoulValue());

        sData = new SoulData(soulValue, sData.maxSoulValue());
        getChunk().setData(SOUL_DATA_ATTACHMENT, sData);
    }

    public void addMaxSoulToChunk(int soul){
        SoulData sData = getSoulInChunk();

        sData = new SoulData(sData.soulValue(), sData.maxSoulValue() + soul);
        getChunk().setData(SOUL_DATA_ATTACHMENT, sData);
    }

    public void removeSoulToChunk(int soul){
        SoulData sData = getSoulInChunk();

        int soulValue = sData.soulValue() - soul;
        soulValue = Math.clamp(soulValue, 0, sData.maxSoulValue());

        sData = new SoulData(soulValue, sData.maxSoulValue());
        getChunk().setData(SOUL_DATA_ATTACHMENT, sData);
    }

    public void setSoulInChunk(int soul){
        SoulData sData = getSoulInChunk();
        soul = Math.clamp(soul, 0, sData.maxSoulValue());
        sData = new SoulData(soul, sData.maxSoulValue());
        getChunk().setData(SOUL_DATA_ATTACHMENT, sData);
    }

    public boolean doesChunkHaveSoul(int soul) {
        SoulData sData = getSoulInChunk();
        return sData.soulValue() >= soul;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    public abstract void tick(Level level);
}
