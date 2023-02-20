package de.cron3x.netrus_craft.common.blocks;

import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netrus_craft.common.blocks.entity.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PedestalBlock extends BaseEntityBlock { //implements SimpleWaterloggedBlock

    public static final VoxelShape SHAPE = makeShape();

    public PedestalBlock(Properties builder)
    {
        super(builder);
        builder.noOcclusion();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegister.PEDESTAL.get().create(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide ) return InteractionResult.SUCCESS;
        if (!(level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal)) return InteractionResult.PASS;

        ItemStack pItem = player.getMainHandItem();

        ItemStack oItem = pedestal.getDisplayItem(false);

        pedestal.setItem(pItem.split(1), false);

        ItemEntity itementity = new ItemEntity(level, pos.getX()+0.5, pos.getY()+1.2, pos.getZ()+0.5, oItem);
        double mx =  (player.getX()-0.5 - pos.getX())/10;
        double my =  (player.getY() - pos.getY())/10;
        double mz =  (player.getZ()-0.5 - pos.getZ())/10;
        itementity.lerpMotion(mx,my,mz);
        level.addFreshEntity(itementity);

        pedestal.setChanged();

        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.1875, 0.8125, 0.6875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.125, 0.875, 1, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.25, 0.125, 0.875, 0.5625, 0.875), BooleanOp.OR);

        return shape;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        super.attack(state, level, pos, player);

        var pig = new Pig(EntityType.PIG, level);
        pig.setPos(pos.getX(), pos.getY() + 1.5D,
                pos.getZ() + 0.5D);
        level.addFreshEntity(pig);

    }

    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        if(p_60515_.getBlock() != p_60518_.getBlock()) {
            if (level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal){
                ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), pedestal.getDisplayItem(false));
                level.addFreshEntity(itementity);
            }
            super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
        }
    }
}