package de.cron3x.netrus_craft.common.blocks.crafting_altar_handler;

import de.cron3x.netrus_craft.common.blocks.BlockRegister;
import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingAltarBlockEntity;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingObeliskBlockEntity;
import de.cron3x.netrus_craft.common.blocks.entity.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CraftingAltarValidationHandler {

    public enum RUNE {
        NONE(Blocks.POLISHED_BLACKSTONE),
        IGNORE_TIME(BlockRegister.TIME_RUNE.get()),
        IGNORE_CEILING(BlockRegister.CEILING_RUNE.get()),
        ;

        public Block block;

        RUNE(@NotNull Block block) {
            this.block = block;
        }

        public static Optional<RUNE> containsRune(Block block){
            for (RUNE i : RUNE.values()){
                if (i.block == block) return Optional.of(i);
            }
            return Optional.of(NONE);
        }
    }

    private static int centerX;
    private static int centerY;
    private static int centerZ;
    private static Level level;


    public static boolean isValidAltar(CraftingAltarBlockEntity altar){
        CraftingAltarValidationHandler.centerX = altar.getBlockPos().getX();
        CraftingAltarValidationHandler.centerY = altar.getBlockPos().getY();
        CraftingAltarValidationHandler.centerZ = altar.getBlockPos().getZ();
        CraftingAltarValidationHandler.level = altar.getLevel();

        /* ====> PEDESTALS <==== */
        boolean pXP = isWantedEntityBlock(centerX+3, centerY, centerZ, BlockEntityRegister.PEDESTAL.get());
        boolean pXN = isWantedEntityBlock(centerX-3, centerY, centerZ, BlockEntityRegister.PEDESTAL.get());
        boolean pZP = isWantedEntityBlock(centerX, centerY, centerZ+3, BlockEntityRegister.PEDESTAL.get());
        boolean pZN = isWantedEntityBlock(centerX, centerY, centerZ-3, BlockEntityRegister.PEDESTAL.get());
        boolean pXPZP = isWantedEntityBlock(centerX+2, centerY, centerZ+2, BlockEntityRegister.PEDESTAL.get());
        boolean pXPZN = isWantedEntityBlock(centerX+2, centerY, centerZ-2, BlockEntityRegister.PEDESTAL.get());
        boolean pXNZP = isWantedEntityBlock(centerX-2, centerY, centerZ+2, BlockEntityRegister.PEDESTAL.get());
        boolean pXNZN = isWantedEntityBlock(centerX-2, centerY, centerZ-2, BlockEntityRegister.PEDESTAL.get());

        /* ====> Obelisk <==== */
        boolean oXPZP = isWantedEntityBlock(centerX+4, centerY+1, centerZ+4, BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get());
        boolean oXPZN = isWantedEntityBlock(centerX+4, centerY+1, centerZ-4, BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get());
        boolean oXNZP = isWantedEntityBlock(centerX-4, centerY+1, centerZ+4, BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get());
        boolean oXNZN = isWantedEntityBlock(centerX-4, centerY+1, centerZ-4, BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get());

        /* ===> Rune <==== */
        boolean rXPZP = isRune(centerX+4, centerY, centerZ+4);
        boolean rXPZN = isRune(centerX+4, centerY, centerZ-4);
        boolean rXNZP = isRune(centerX-4, centerY, centerZ+4);
        boolean rXNZN = isRune(centerX-4, centerY, centerZ-4);

        boolean res = pXP && pXN && pZP && pZN && pXPZP && pXPZN && pXNZP && pXNZN && oXPZP && oXPZN && oXNZP && oXNZN;

        return res;
    }

    private static boolean isWantedEntityBlock(int x, int y, int z, BlockEntityType<?> blockEntity){
        BlockPos pos = new BlockPos(x,y,z);
        if (level.getBlockState(pos).hasBlockEntity()) {
            if (level.getBlockEntity(pos).getType().equals(blockEntity)) {
                return true;
            }
        }
        return false;
    }
    private static boolean isRune(int x, int y, int z) {
        RUNE rune = getUsedRune(new BlockPos(x,y,z));

        return rune == null ? false : true;
    }

    public static RUNE getUsedRune(BlockPos pos){
        return RUNE.containsRune(level.getBlockState(pos).getBlock()).get();
    }

    public static NonNullList<ItemStack> getPedestalItems(CraftingAltarBlockEntity altar, boolean simulate) {
        Level level = altar.getLevel();
        int centerX = altar.getBlockPos().getX();
        int centerY = altar.getBlockPos().getY();
        int centerZ = altar.getBlockPos().getZ();

        NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

        ItemStack iXP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX + 3, centerY, centerZ))).getDisplayItem(simulate);
        ItemStack iXN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX - 3, centerY, centerZ))).getDisplayItem(simulate);
        ItemStack iZP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX, centerY, centerZ + 3))).getDisplayItem(simulate);
        ItemStack iZN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX, centerY, centerZ - 3))).getDisplayItem(simulate);
        ItemStack iXPZP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX + 2, centerY, centerZ + 2))).getDisplayItem(simulate);
        ItemStack iXPZN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX + 2, centerY, centerZ - 2))).getDisplayItem(simulate);
        ItemStack iXNZP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX - 2, centerY, centerZ + 2))).getDisplayItem(simulate);
        ItemStack iXNZN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX - 2, centerY, centerZ - 2))).getDisplayItem(simulate);

        items.set(0, iXP);
        items.set(1, iXN);
        items.set(2, iZP);
        items.set(3, iZN);
        items.set(4, iXPZP);
        items.set(5, iXPZN);
        items.set(6, iXNZP);
        items.set(7, iXNZN);

        return items;
    }

    public static List<PedestalBlockEntity> getPedestals(CraftingAltarBlockEntity altar) {
        Level level = altar.getLevel();
        int centerX = altar.getBlockPos().getX();
        int centerY = altar.getBlockPos().getY();
        int centerZ = altar.getBlockPos().getZ();

        List<PedestalBlockEntity> pedestals = Arrays.asList(new PedestalBlockEntity[8]);

        PedestalBlockEntity iXP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX + 3, centerY, centerZ)));
        PedestalBlockEntity iXN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX - 3, centerY, centerZ)));
        PedestalBlockEntity iZP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX, centerY, centerZ + 3)));
        PedestalBlockEntity iZN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX, centerY, centerZ - 3)));
        PedestalBlockEntity iXPZP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX + 2, centerY, centerZ + 2)));
        PedestalBlockEntity iXPZN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX + 2, centerY, centerZ - 2)));
        PedestalBlockEntity iXNZP = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX - 2, centerY, centerZ + 2)));
        PedestalBlockEntity iXNZN = ((PedestalBlockEntity) level.getBlockEntity(new BlockPos(centerX - 2, centerY, centerZ - 2)));

        pedestals.set(0, iXP);
        pedestals.set(1, iXN);
        pedestals.set(2, iZP);
        pedestals.set(3, iZN);
        pedestals.set(4, iXPZP);
        pedestals.set(5, iXPZN);
        pedestals.set(6, iXNZP);
        pedestals.set(7, iXNZN);

        return pedestals;
    }
}
