package de.cron3x.netrus_craft.common.blocks;

import de.cron3x.netrus_craft.api.NetrusAPI;
import de.cron3x.netrus_craft.api.block.CraftingAltarHandler;
import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingAltarBlockEntity;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingObeliskBlockEntity;
import de.cron3x.netrus_craft.common.blocks.states.Blockstates;
import de.cron3x.netrus_craft.common.items.ItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class CraftingAltarBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE_ACTIVE = makeShapeActive();
    private static final VoxelShape SHAPE_INACTIVE = makeShapeInactive();

    public CraftingAltarBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(Blockstates.ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(Blockstates.ACTIVE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegister.CRAFTINGALTAR_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide ) return InteractionResult.SUCCESS;
        if (!(level.getBlockEntity(pos) instanceof CraftingAltarBlockEntity altar)) return InteractionResult.SUCCESS;
        Boolean isActive = altar.getBlockState().getValue(Blockstates.ACTIVE);
        if (isActive) {
            if (player.getMainHandItem().getItem() == ItemRegister.GLASS_WAND.get()) {
                if (CraftingAltarHandler.isValidAltar(altar)){
                    if (!altar.getIgnoreTime()) {
                        if (altar.getIsDay(altar) != NetrusAPI.isDay()) {
                            altar.setIsDay(NetrusAPI.isDay());
                            altar.setChanged();
                        }
                    }
                    if (altar.getDisplayItem(true).isEmpty()){
                        if (altar.hasRecipe(altar)) {
                            altar.setChanged();
                            altar.craftItem(altar);
                        }
                    }
                }
            }
            else if (player.getMainHandItem().isEmpty()) {
                ItemStack oItem = altar.getDisplayItem(false);

                altar.setItem(ItemStack.EMPTY, false);

                ItemEntity itementity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), oItem);
                level.addFreshEntity(itementity);

                altar.setChanged();
            }
            return InteractionResult.SUCCESS;
        }
        else {
            if (player.getMainHandItem().getItem() == ItemRegister.GLASS_WAND.get()) {
                if (CraftingAltarHandler.isValidAltar(altar)){
                    state = state.setValue(Blockstates.ACTIVE, true);

                    level.setBlock(pos, state, 3);

                    BlockPos obeliskXPZP = new BlockPos(pos.getX()+4, pos.getY()+1, pos.getZ()+4);
                    BlockPos obeliskXPZN = new BlockPos(pos.getX()+4, pos.getY()+1, pos.getZ()-4);
                    BlockPos obeliskXNZP = new BlockPos(pos.getX()-4, pos.getY()+1, pos.getZ()+4);
                    BlockPos obeliskXNZN = new BlockPos(pos.getX()-4, pos.getY()+1, pos.getZ()-4);
                    level.setBlock(obeliskXPZP, level.getBlockState(obeliskXPZP).setValue(Blockstates.ACTIVE, true), 3);
                    level.setBlock(obeliskXPZN, level.getBlockState(obeliskXPZN).setValue(Blockstates.ACTIVE, true), 3);
                    level.setBlock(obeliskXNZP, level.getBlockState(obeliskXNZP).setValue(Blockstates.ACTIVE, true), 3);
                    level.setBlock(obeliskXNZN, level.getBlockState(obeliskXNZN).setValue(Blockstates.ACTIVE, true), 3);

                    CraftingObeliskBlockEntity eObeliskXPZP = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXPZP));
                    CraftingObeliskBlockEntity eObeliskXPZN = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXPZN));
                    CraftingObeliskBlockEntity eObeliskXNZP = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXNZP));
                    CraftingObeliskBlockEntity eObeliskXNZN = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXNZN));
                    eObeliskXPZP.setAltarPos(pos);
                    eObeliskXPZN.setAltarPos(pos);
                    eObeliskXNZP.setAltarPos(pos);
                    eObeliskXNZN.setAltarPos(pos);
                    level.setBlockEntity(eObeliskXPZP);
                    level.setBlockEntity(eObeliskXPZN);
                    level.setBlockEntity(eObeliskXNZP);
                    level.setBlockEntity(eObeliskXNZN);
                    eObeliskXPZP.setChanged();
                    eObeliskXPZN.setChanged();
                    eObeliskXNZP.setChanged();
                    eObeliskXNZN.setChanged();

                    altar.setChanged();

                    Minecraft.getInstance().level.addParticle(ParticleTypes.FLASH,pos.getX()+.5,pos.getY()+1.5,pos.getZ()+.5, 0, 0,0);
                    Minecraft.getInstance().level.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY()+3,pos.getZ(), 0, 0,0);
                    Minecraft.getInstance().level.addParticle(ParticleTypes.END_ROD,pos.getX()+1,pos.getY()+2,pos.getZ()+1, 0, 0,0);
                    Minecraft.getInstance().level.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY()+1,pos.getZ()-1, 0, 0,0);
                    Minecraft.getInstance().level.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY()+1,pos.getZ()+1, 0, 0,0);
                }
                else {
                    Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(255,0,0), 255), pos.getX()+.5,pos.getY()+1.5,pos.getZ()+.5, 1, 1,1);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(Blockstates.ACTIVE) ? SHAPE_ACTIVE: SHAPE_INACTIVE;
    }
    public static VoxelShape makeShapeActive(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.8125, 0.0625, 0.9375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0.0625, 0.5625, 0.8125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0.8125, 0.5625, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.25, 0.4375, 0.9375, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.25, 0.4375, 0.1875, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.6875, 0.3125, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.3125, 0.8125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.25, 0.1875, 0.8125, 0.8125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.25, 0.6875, 0.8125, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.375, 0.3125, 0.6875, 0.75, 0.6875), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeShapeInactive(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 1, 1), BooleanOp.OR);
        return shape;
    }


    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return ($0, $1, $2, blockEntity) -> {
            if (blockEntity instanceof CraftingAltarBlockEntity altar) {
                altar.tick();
            }
        };
    }
}