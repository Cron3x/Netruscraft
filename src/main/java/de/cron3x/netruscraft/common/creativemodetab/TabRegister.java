package de.cron3x.netruscraft.common.creativemodetab;

import de.cron3x.netruscraft.NetrusCraft;
import de.cron3x.netruscraft.common.items.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NetrusCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER =DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NetrusCraft.MODID);
    public static RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("netrus_tab", () -> CreativeModeTab.builder().title(Component.translatable("item_group." + NetrusCraft.MODID + ".netrus_tab"))
            .icon(() -> ItemRegister.CRAFTINGALTAR_BLOCK_ITEM.get().getDefaultInstance()).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
    }
}
