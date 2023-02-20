package de.cron3x.netrus_craft.common.creativemodetab;

import de.cron3x.netrus_craft.NetrusCraft;
import de.cron3x.netrus_craft.common.items.ItemRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetrusCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetrusTabs {
    public static CreativeModeTab MAIN_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        MAIN_TAB = event.registerCreativeModeTab(new ResourceLocation(NetrusCraft.MODID, "netrus_tab"), builder -> {
            builder
                    .title(Component.translatable("item_group." + NetrusCraft.MODID + ".netrus_tab"))
                    .icon(() -> ItemRegister.PEDESTAL_BLOCK_ITEM.get().getDefaultInstance());
        });
    }
}
