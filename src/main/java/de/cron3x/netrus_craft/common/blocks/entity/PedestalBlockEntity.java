package de.cron3x.netrus_craft.common.blocks.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PedestalBlockEntity extends BlockEntity {

    private final ItemStackHandler inventory = new ItemStackHandler(1);
    private final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> this.inventory);

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.PEDESTAL.get(), pos, state);
    }


    public ItemStack setItem(ItemStack itemStack, Boolean simulate){
        IItemHandler h = optional.orElse(null);
        ItemStack returner = h.insertItem(0,itemStack.copy(), simulate);
        if(!simulate) update();
        return returner;
    }

    public ItemStack getDisplayItem(Boolean simulate) {
        IItemHandler h = optional.orElse(null);
        ItemStack returner = h.extractItem(0,1,simulate);
        if(!simulate) update();
        return returner;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        save(nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap, null);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }

    public void update() {
        BlockState state = level.getBlockState(this.worldPosition);
        this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        this.setChanged();
    }

    public CompoundTag save(CompoundTag nbt){
        nbt.put("Inventory", this.inventory.serializeNBT());
        return nbt;
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
        //
        this.load(tag);
    }
}
