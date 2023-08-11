package de.cron3x.netruscraft.client.events;

import de.cron3x.netruscraft.Netruscraft;
import de.cron3x.netruscraft.client.huds.SelectedSpellHUDOverlay;
import de.cron3x.netruscraft.client.renderers.CraftingAltarRenderer;
import de.cron3x.netruscraft.client.renderers.CraftingObeliskRenderer;
import de.cron3x.netruscraft.client.renderers.PedestalRenderer;
import de.cron3x.netruscraft.common.blocks.entity.BlockEntityRegister;
import de.cron3x.netruscraft.common.items.ItemRegister;
import de.cron3x.netruscraft.common.items.WhetstoneItem;
import de.cron3x.netruscraft.common.networking.PackageManager;
import de.cron3x.netruscraft.common.networking.packets.CycleSpellC2SPacket;
import de.cron3x.netruscraft.common.utils.KeyBinding;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Netruscraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityRegister.PEDESTAL.get(), PedestalRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityRegister.CRAFTINGALTAR_BLOCK_ENTITY.get(), CraftingAltarRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityRegister.CRAFTINGOBELIK_BLOCK_ENTITY.get(), CraftingObeliskRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(ItemRegister.WHETSTONE.get(), new ResourceLocation(Netruscraft.MODID,"whetstone_id"), (itemStack, clientLevel , livingEntity, id) -> WhetstoneItem.getTypeTexture(itemStack));
            });
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.KEY_SWITCH_SPELL);
        }

        @SubscribeEvent
        public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("selected_spell", SelectedSpellHUDOverlay.HUD_SPELL_SELECTION);
        }
    }

    @Mod.EventBusSubscriber(modid = Netruscraft.MODID, value = Dist.CLIENT)
    public static class ClentEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if (KeyBinding.KEY_SWITCH_SPELL.consumeClick()) {
                PackageManager.sendToServer(new CycleSpellC2SPacket());
            }
        }
    }
}
