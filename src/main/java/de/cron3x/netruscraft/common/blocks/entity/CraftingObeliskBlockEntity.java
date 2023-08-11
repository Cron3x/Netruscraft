package de.cron3x.netruscraft.common.blocks.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CraftingObeliskBlockEntity extends BlockEntity {

    private BlockPos altarPos;

    public CraftingObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get(), pos, state);
        this.altarPos = BlockPos.ZERO;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.altarPos = BlockPos.of(nbt.getLong("AltarLocation"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        save(nbt);
    }
    public CompoundTag save(CompoundTag nbt){
        if (this.altarPos != null) nbt.putLong("AltarLocation",  this.altarPos.asLong());
        return nbt;
    }

    public void setAltarPos(BlockPos pos) {
        this.altarPos = pos;
    }
    public BlockPos getAltarPos() {
        return this.altarPos;
    }



    public void update() {
        BlockState state = level.getBlockState(this.worldPosition);
        this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        this.setChanged();
    }

    @Override
    public CompoundTag getUpdateTag() {

        return save(new CompoundTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        //
        super.onDataPacket(net,pkt);
        BlockState state = this.level.getBlockState(this.worldPosition);
        this.handleUpdateTag(pkt.getTag());
        this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

}
