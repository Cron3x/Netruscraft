package de.cron3x.netruscraft.common.blocks;

import de.cron3x.netruscraft.NetrusCraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NetrusCraft.MODID);

    public static final RegistryObject<PedestalBlock> PEDESTAL_BLOCK = BLOCKS.register("pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));
    public static final RegistryObject<CraftingAltarBlock> CRAFTINGALTAR_BLOCK = BLOCKS.register("craftingaltar", () -> new CraftingAltarBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));
    public static final RegistryObject<CraftingObeliskBlock> CRAFTINGOBELISK_BLOCK = BLOCKS.register("craftingobelisk", () -> new CraftingObeliskBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));

    public static final RegistryObject<Block> TIME_RUNE = BLOCKS.register("rune_time", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));
    public static final RegistryObject<Block> CEILING_RUNE = BLOCKS.register("rune_ceiling", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));

}
