package de.cron3x.netrus_craft.common.blocks;

import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netrus_craft.common.blocks.states.Blockstates;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CraftingObeliskBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = makeShape();

    public CraftingObeliskBlock(Properties properties)
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
        return BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.9375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 1, 0.625, 0.375, 1.3125, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 1.3125, 0.625, 0.375, 1.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09375, 1.625, 0.625, 0.375, 1.9375, 0.90625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1.625, 0.625, 0.90625, 1.9375, 0.90625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1, 0.625, 0.96875, 1.3125, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1.3125, 0.625, 0.9375, 1.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1.625, 0.09375, 0.90625, 1.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1, 0.03125, 0.96875, 1.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1.3125, 0.0625, 0.9375, 1.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09375, 1.625, 0.09375, 0.375, 1.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 1, 0.03125, 0.375, 1.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 1.3125, 0.0625, 0.375, 1.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1.9375, 0.15625, 0.84375, 2, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.15625, 1.9375, 0.625, 0.375, 2, 0.84375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.15625, 1.9375, 0.15625, 0.375, 2, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 1.9375, 0.625, 0.84375, 2, 0.84375), BooleanOp.OR);

        return shape;
    }


    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}