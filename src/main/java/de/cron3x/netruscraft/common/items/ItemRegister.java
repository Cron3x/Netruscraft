package de.cron3x.netruscraft.common.items;

import de.cron3x.netruscraft.NetrusCraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static de.cron3x.netruscraft.common.blocks.BlockRegister.*;

public final class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NetrusCraft.MODID);

    public static final RegistryObject<Item> PEDESTAL_BLOCK_ITEM = ITEMS.register("pedestal", () -> new BlockItem(PEDESTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRAFTINGALTAR_BLOCK_ITEM = ITEMS.register("craftingaltar", () -> new BlockItem(CRAFTINGALTAR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRAFTINGOBELISK_BLOCK_ITEM = ITEMS.register("craftingobelisk", () -> new BlockItem(CRAFTINGOBELISK_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> TIME_RUNE_BLOCK_ITEM = ITEMS.register("rune_time", () -> new BlockItem(TIME_RUNE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CEILING_RUNE_BLOCK_ITEM = ITEMS.register("rune_ceiling", () -> new BlockItem(CEILING_RUNE.get(), new Item.Properties()));

    public static final RegistryObject<Item> GLASS_WAND = ITEMS.register("glass_wand", () -> new GlassWantItem((new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> OPAL_ITEM = ITEMS.register("opal", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUNE_BLANK = ITEMS.register("rune_blank", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_TIME_DAY = ITEMS.register("rune_time_day", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_TIME_NIGHT = ITEMS.register("rune_time_night", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_TIME_EVENING = ITEMS.register("rune_time_evening", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUNE_TIME_MORNING = ITEMS.register("rune_time_morning", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WHETSTONE = ITEMS.register("whetstone", () -> new WhetstoneItem((new Item.Properties()).stacksTo(1)));

    public static final RegistryObject<Item> WOODEN_CATALYST = ITEMS.register("wooden_catalyst", () -> new WoodenCatalystItem((new Item.Properties()).stacksTo(1)));

    }