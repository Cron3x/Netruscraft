package de.cron3x.netruscraft;

import com.mojang.logging.LogUtils;
import de.cron3x.netruscraft.client.particles.ParticleRegister;
import de.cron3x.netruscraft.common.blocks.BlockRegister;
import de.cron3x.netruscraft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netruscraft.common.creativemodetab.TabRegister;
import de.cron3x.netruscraft.common.items.ItemRegister;
import de.cron3x.netruscraft.common.networking.PackageManager;
import de.cron3x.netruscraft.common.recipe.RecipeRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Netruscraft.MODID)
public class Netruscraft {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "netruscraft";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Netruscraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::commonSetup);

        registerDeferredRegs(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void registerDeferredRegs(IEventBus modEventBus){
        TabRegister.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(modEventBus);
        BlockRegister.BLOCKS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        BlockEntityRegister.BLOCK_ENTITIES.register(modEventBus);
        ParticleRegister.PARTICLES.register(modEventBus);
        RecipeRegister.RECIPE_SERIALIZERS.register(modEventBus);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TabRegister.MAIN_TAB.get()) {
            event.accept(ItemRegister.PEDESTAL_BLOCK_ITEM);
            event.accept(ItemRegister.CRAFTINGALTAR_BLOCK_ITEM);
            event.accept(ItemRegister.CRAFTINGOBELISK_BLOCK_ITEM);
            event.accept(ItemRegister.CEILING_RUNE_BLOCK_ITEM);
            event.accept(ItemRegister.TIME_RUNE_BLOCK_ITEM);
            event.accept(ItemRegister.GLASS_WAND);
            event.accept(ItemRegister.OPAL_ITEM);
            event.accept(ItemRegister.RUNE_TIME_DAY);
            event.accept(ItemRegister.RUNE_TIME_EVENING);
            event.accept(ItemRegister.RUNE_TIME_MORNING);
            event.accept(ItemRegister.RUNE_TIME_NIGHT);
            event.accept(ItemRegister.RUNE_BLANK);
            event.accept(ItemRegister.WHETSTONE);
            event.accept(ItemRegister.WOODEN_CATALYST);
            event.accept(ItemRegister.CATALYST_ADVANCE_RUNE_SIZE);
        }
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PackageManager.register();
        });
    }
}
