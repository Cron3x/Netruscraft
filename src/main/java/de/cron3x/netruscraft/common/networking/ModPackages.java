package de.cron3x.netruscraft.common.networking;

import de.cron3x.netruscraft.NetrusCraft;
import de.cron3x.netruscraft.common.networking.packets.CycleSpellC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackages {
    private static SimpleChannel INSTANCE;

    private static int packageId = 0;
    private static int id() {
        return ++packageId; //maybe use packageId++
    }

    public static void register(){
        SimpleChannel instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(NetrusCraft.MODID, "packages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = instance;

        instance.messageBuilder(CycleSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CycleSpellC2SPacket::new)
                .encoder(CycleSpellC2SPacket::toBytes)
                .consumerMainThread(CycleSpellC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);

    }
    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer serverPlayer) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
    }
}
