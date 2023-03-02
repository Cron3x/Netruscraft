package de.cron3x.netrus_craft.common.blocks.entity;


import de.cron3x.netrus_craft.common.blocks.crafting_altar_handler.CraftingAltarValidationHandler;
import de.cron3x.netrus_craft.client.particles.ParticleColor;
import de.cron3x.netrus_craft.common.blocks.states.Blockstates;
import de.cron3x.netrus_craft.common.items.ItemRegister;
import de.cron3x.netrus_craft.common.recipe.CraftingAltarRecipe;
import de.cron3x.netrus_craft.common.recipe.CraftingAltarRecipeType;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import de.cron3x.netrus_craft.api.NetrusAPI;

import static de.cron3x.netrus_craft.common.blocks.crafting_altar_handler.CraftingAltarParticleHandler.*;

public class CraftingAltarBlockEntity extends BlockEntity implements Tickable {

    private boolean ignore_ceiling;
    private boolean ignore_time;
    private Boolean day;

    private boolean isCrafting;

    public int time;

    private List<BlockPos> pedestals;

    private final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> inventory);

    public CraftingAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.CRAFTINGALTAR_BLOCK_ENTITY.get(), pos, state);
        this.ignore_ceiling = false;
        this.ignore_time    = false;
        this.day = NetrusAPI.isDay();
    }

    @Override
    public void tick() {
        ++this.time;
        if (level != null && level.isClientSide) {
            if (getBlockState().getValue(Blockstates.ACTIVE) && getBlockState().getValue(BlockStateProperties.DOUBLE_BLOCK_HALF).equals(DoubleBlockHalf.LOWER)){
                if(this.day){
                    //TODO: Implement Spiracle Orb if no recipe is active, I could do and a beam to the middle
                    craftingFlameParticle(getBlockPos(), new ParticleColor(255,255,255),new ParticleColor(255,150,0), 15);
                }
                else{
                    craftingFlameParticle(getBlockPos(), new ParticleColor(100, 100, 100), new ParticleColor(155, 0, 255), 10);
                }
            }

            if ( NetrusAPI.isDay() != null ) {
                if (!ignore_time) {
                    if (this.day != NetrusAPI.isDay()) {
                        this.day = NetrusAPI.isDay();
                        System.out.println("fn: " + NetrusAPI.isDay());
                        System.out.println("var: " + this.day);
                        setChanged();
                    }
                }
            }

            if ( isCrafting ) {
                if (!this.pedestals.isEmpty()) {
                    if (time >= 40){
                        this.time = 0;
                        this.isCrafting = false;
                    }
                } else {
                    if (time >= 20) {
                        this.time = 0;
                        this.isCrafting = false;
                    }
                }
            }
        }
    }

    public void craftItem(CraftingAltarBlockEntity altar){
        isCrafting = true;

        Level level = altar.level;
        SimpleContainer inv = new SimpleContainer(9);

        NonNullList<ItemStack> pedItems = CraftingAltarValidationHandler.getPedestalItems(altar, true);

        ItemStack indicatorItem = getIsDay(altar) ? ItemRegister.RUNE_TIME_DAY.get().getDefaultInstance() : ItemRegister.RUNE_TIME_NIGHT.get().getDefaultInstance();
        inv.setItem(0,indicatorItem);

        for (int i = 1; i < inv.getContainerSize(); i++){
            inv.setItem(i, pedItems.get(i-1));
        }

        Optional<CraftingAltarRecipe> recipe = level.getRecipeManager().getRecipeFor(CraftingAltarRecipeType.INSTANCE, inv, level);

        if (!hasRecipe(altar)) return;

        for (PedestalBlockEntity ped : CraftingAltarValidationHandler.getPedestals(altar)){
            if (ped.getDisplayItem(true).isEmpty()) continue;
            particleCircle(ParticleTypes.ENCHANT, ped.getBlockPos(), 0.4);
            ped.getDisplayItem(false);
        }

        altar.setItem(recipe.get().getResultItem(), false);
        particleCircle(ParticleTypes.FLASH, altar.getBlockPos().above(), 1);
        isCrafting = false;
    }

    public boolean hasRecipe(CraftingAltarBlockEntity altar){
        Level level = altar.level;
        SimpleContainer inv = new SimpleContainer(9);

        NonNullList<ItemStack> pedItems = CraftingAltarValidationHandler.getPedestalItems(altar, true);

        ItemStack indicatorItem = getIsDay(altar) ? ItemRegister.RUNE_TIME_DAY.get().getDefaultInstance() : ItemRegister.RUNE_TIME_NIGHT.get().getDefaultInstance();
        System.out.println("isDAy " + getIsDay(altar));
        inv.setItem(0,indicatorItem);

        for (int i = 1; i < inv.getContainerSize(); i++){
            inv.setItem(i, pedItems.get(i-1));
        }
        Optional<CraftingAltarRecipe> recipe = level.getRecipeManager().getRecipeFor(CraftingAltarRecipeType.INSTANCE, inv, level);

        return recipe.isPresent() ; //&& recipe.get().isDay() == altar.isDay()
    }

    public ItemStack setItem(ItemStack itemStack, boolean simulate){
        IItemHandler h = lazyItemHandler.orElse(null);
        ItemStack returner = h.insertItem(0,itemStack.copy(), simulate);
        if(!simulate) update();
        return returner;
    }
    public ItemStack getDisplayItem(boolean simulate) {
        IItemHandler h = lazyItemHandler.orElse(null);
        ItemStack returner = h.extractItem(0, 1, simulate);
        if(!simulate) update();
        return returner;
    }

    public ItemStack getItems(boolean simulate) {
        IItemHandler h = lazyItemHandler.orElse(null);
        ItemStack returner = h.extractItem(0, h.getStackInSlot(0).getCount(), simulate);
        if(!simulate) update();
        return returner;
    }
    public ItemStack getItems(boolean simulate, int amount) {
        IItemHandler h = lazyItemHandler.orElse(null);
        if (amount > h.getStackInSlot(0).getCount()) amount = h.getStackInSlot(0).getCount();
        ItemStack returner = h.extractItem(0, amount, simulate);
        if(!simulate) update();
        return returner;
    }

    public static boolean getIsDay(CraftingAltarBlockEntity altar){
        //System.out.println("getIsDay: " + altar.day);
        return altar.day;
    }
    public void setIsDay(boolean isDay){
        this.day = isDay;
    }

    public boolean getIgnoreTime(){
        return this.ignore_time;
    }

    public void setIsCrafting(boolean status){
        this.isCrafting = status;
    }
    public boolean getIsCrafting(){
        return this.isCrafting;
    }







    //*========== Network and save Stuff ==========*\\
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.ignore_time = nbt.getBoolean("ignore_time");
        this.ignore_ceiling = nbt.getBoolean("ignore_ceiling");
        this.day = nbt.getBoolean("Day");
        this.isCrafting = nbt.getBoolean("IsCrafting");
        this.time = nbt.getInt("Time");
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        save(nbt);
    }

    public void update() {
        BlockState state = level.getBlockState(this.worldPosition);
        this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        this.setChanged();
    }

    public CompoundTag save(CompoundTag nbt){
        nbt.putBoolean("ignore_time", this.ignore_time);
        nbt.putBoolean("ignore_ceiling", this.ignore_ceiling);
        System.out.println("save::var: " + this.day);
        nbt.putBoolean("Day", this.day);
        nbt.put("Inventory", this.inventory.serializeNBT());
        nbt.putBoolean("IsCrafting", this.isCrafting);
        nbt.putInt("Time", this.time);
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
