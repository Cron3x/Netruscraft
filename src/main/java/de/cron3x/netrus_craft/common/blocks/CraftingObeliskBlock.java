package de.cron3x.netrus_craft.common.blocks;

import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static de.cron3x.netrus_craft.api.NetrusAPI.preventCreativeDropFromBottomPart;

public class CraftingObeliskBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE_LOWER = makeShapeLower();
    private static final VoxelShape SHAPE_UPPER = makeShapeUpper();

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public CraftingObeliskBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF).equals(DoubleBlockHalf.LOWER)) return SHAPE_LOWER;
        return SHAPE_UPPER;
    }

    public static VoxelShape makeShapeLower(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.9375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 1, 0.375), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeUpper(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 0.96875, 0.3125, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.03125, 0.96875, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 0.03125, 0.375, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 0.625, 0.375, 0.3125, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.3125, 0.625, 0.375, 0.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09375, 0.625, 0.625, 0.375, 0.9375, 0.90625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.15625, 0.9375, 0.625, 0.375, 1, 0.84375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.15625, 0.9375, 0.15625, 0.375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09375, 0.625, 0.09375, 0.375, 0.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.3125, 0.0625, 0.375, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.3125, 0.0625, 0.9375, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.09375, 0.90625, 0.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.9375, 0.15625, 0.84375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.9375, 0.625, 0.84375, 1, 0.84375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.625, 0.90625, 0.9375, 0.90625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.3125, 0.625, 0.9375, 0.625, 0.9375), BooleanOp.OR);

        return shape;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
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

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }
}