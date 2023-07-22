package de.cron3x.netrus_craft.common.blocks;

import de.cron3x.netrus_craft.api.NetrusAPI;
import de.cron3x.netrus_craft.common.blocks.crafting_altar_handler.CraftingAltarParticleHandler;
import de.cron3x.netrus_craft.common.blocks.crafting_altar_handler.CraftingAltarValidationHandler;
import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingAltarBlockEntity;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingObeliskBlockEntity;
import de.cron3x.netrus_craft.common.blocks.states.Blockstates;
import de.cron3x.netrus_craft.common.items.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;

import static de.cron3x.netrus_craft.api.NetrusAPI.preventCreativeDropFromBottomPart;

public class CraftingAltarBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE_ACTIVE = makeShapeActive();
    private static final VoxelShape SHAPE_INACTIVE = makeShapeInactive();
    private static final VoxelShape SHAPE_UPPER = Shapes.join(Shapes.empty(), Shapes.box(0, 0, 0, 1, 1.5, 1), BooleanOp.OR);


    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;


    public CraftingAltarBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(Blockstates.ACTIVE, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(Blockstates.ACTIVE, HALF);
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
            if (altar.getBlockState().getValue(BlockStateProperties.DOUBLE_BLOCK_HALF).equals(DoubleBlockHalf.UPPER)){
                altar = (CraftingAltarBlockEntity) level.getBlockEntity(altar.getBlockPos().below());
            }

            System.out.println("altar.getShowItem(): " + altar.getShowItem());

            if (player.getMainHandItem().getItem() == ItemRegister.GLASS_WAND.get()) {
                if (CraftingAltarValidationHandler.isValidAltar(altar)){
                    if (!altar.getShowItem()){
                        if (altar.hasRecipe(altar)) {
                            altar.setChanged();
                            boolean ret = altar.prepareCrafting(altar);
                            System.out.println("altar prepCraft :: " + ret);
                        } else System.out.println("!altar.hasRecipe(altar)");
                    } else System.out.println("not empty: " + altar.getDisplayItem(true).getItem());
                }
            }
            else if (player.getMainHandItem().isEmpty() && altar.getShowItem()) {
                ItemStack oItem = altar.getDisplayItem(false);

                altar.setItem(ItemStack.EMPTY, false);

                ItemEntity itementity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), oItem);
                level.addFreshEntity(itementity);

                altar.update();
            }
            return InteractionResult.SUCCESS;
        }
        else {
            if (player.getMainHandItem().getItem() == ItemRegister.GLASS_WAND.get()) {
                if (CraftingAltarValidationHandler.isValidAltar(altar)){
                    state = state.setValue(Blockstates.ACTIVE, true);
                        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(Blockstates.ACTIVE, true), 3);
                    level.setBlock(pos, state, 3);

                    BlockPos obeliskXPZP = new BlockPos(pos.getX()+4, pos.getY()+1, pos.getZ()+4);
                    BlockPos obeliskXPZN = new BlockPos(pos.getX()+4, pos.getY()+1, pos.getZ()-4);
                    BlockPos obeliskXNZP = new BlockPos(pos.getX()-4, pos.getY()+1, pos.getZ()+4);
                    BlockPos obeliskXNZN = new BlockPos(pos.getX()-4, pos.getY()+1, pos.getZ()-4);

                    CraftingObeliskBlockEntity eObeliskXPZP = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXPZP));
                    CraftingObeliskBlockEntity eObeliskXPZN = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXPZN));
                    CraftingObeliskBlockEntity eObeliskXNZP = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXNZP));
                    CraftingObeliskBlockEntity eObeliskXNZN = ((CraftingObeliskBlockEntity) level.getBlockEntity(obeliskXNZN));

                    eObeliskXPZP.setAltarPos(pos);
                    eObeliskXPZN.setAltarPos(pos);
                    eObeliskXNZP.setAltarPos(pos);
                    eObeliskXNZN.setAltarPos(pos);

                    level.setBlock(obeliskXPZP, level.getBlockState(obeliskXPZP), 2);
                    level.setBlock(obeliskXPZN, level.getBlockState(obeliskXPZN), 2);
                    level.setBlock(obeliskXNZP, level.getBlockState(obeliskXNZP), 2);
                    level.setBlock(obeliskXNZN, level.getBlockState(obeliskXNZN), 2);

                    level.setBlockEntity(eObeliskXPZP);
                    level.setBlockEntity(eObeliskXPZN);
                    level.setBlockEntity(eObeliskXNZP);
                    level.setBlockEntity(eObeliskXNZN);

                    eObeliskXPZP.update();
                    eObeliskXPZN.update();
                    eObeliskXNZP.update();
                    eObeliskXNZN.update();

                    altar.update();

                    CraftingAltarParticleHandler.validAltar(pos);
                }
                else {
                    CraftingAltarParticleHandler.invalidAltar(pos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF).equals(DoubleBlockHalf.UPPER)) return SHAPE_UPPER;
        return state.getValue(Blockstates.ACTIVE) ? SHAPE_ACTIVE: SHAPE_INACTIVE;
    }
    public static VoxelShape makeShapeInactive(){
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
    public static VoxelShape makeShapeActive(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 1, 1), BooleanOp.OR);
        return shape;
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
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