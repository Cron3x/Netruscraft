package de.cron3x.netruscraft.common.blocks.entity;

import de.cron3x.netruscraft.Netruscraft;
import de.cron3x.netruscraft.common.blocks.BlockRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Netruscraft.MODID);

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockRegister.PEDESTAL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CraftingAltarBlockEntity>> CRAFTINGALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("craftingaltar",
            () -> BlockEntityType.Builder.of(CraftingAltarBlockEntity::new, BlockRegister.CRAFTINGALTAR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CraftingObeliskBlockEntity>> CRAFTINGOBELIK_BLOCK_ENTITY = BLOCK_ENTITIES.register("craftingobelisk",
            () -> BlockEntityType.Builder.of(CraftingObeliskBlockEntity::new, BlockRegister.CRAFTINGOBELISK_BLOCK.get()).build(null));
}
