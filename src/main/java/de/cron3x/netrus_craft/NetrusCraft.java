package de.cron3x.netrus_craft;

import com.mojang.logging.LogUtils;
import de.cron3x.netrus_craft.client.particles.ParticleRegister;
import de.cron3x.netrus_craft.common.blocks.BlockRegister;
import de.cron3x.netrus_craft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netrus_craft.common.creativemodetab.NetrusTabs;
import de.cron3x.netrus_craft.common.items.ItemRegister;
import de.cron3x.netrus_craft.client.renderers.CraftingObeliskRenderer;
import de.cron3x.netrus_craft.client.renderers.PedestalRenderer;
import de.cron3x.netrus_craft.client.renderers.CraftingAltarRenderer;
import de.cron3x.netrus_craft.common.recipe.RecipeRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

/*
*   TODO: Patchouli
*   TODO: Make Obelisk shoot beam at center.
*   TODO: Add light to tip of Obelisk.
*
*/

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NetrusCraft.MODID)
public class NetrusCraft
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "netruscraft";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public NetrusCraft()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        BlockRegister.BLOCKS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        BlockEntityRegister.BLOCK_ENTITIES.register(modEventBus);
        ParticleRegister.PARTICLES.register(modEventBus);
        RecipeRegister.RECIPE_SERIALIZERS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == NetrusTabs.MAIN_TAB) {

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
        }
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityRegister.PEDESTAL.get(), PedestalRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityRegister.CRAFTINGALTAR_BLOCK_ENTITY.get(), CraftingAltarRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get(), CraftingObeliskRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
