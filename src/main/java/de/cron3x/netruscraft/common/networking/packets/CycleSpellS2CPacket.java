package de.cron3x.netruscraft.common.networking.packets;

import de.cron3x.netruscraft.client.huds.SelectedSpellData;
import de.cron3x.netruscraft.common.items.CatalystItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CycleSpellS2CPacket {
    private final int index;
    public CycleSpellS2CPacket(int index){
        this.index = index;
    }

    public CycleSpellS2CPacket(FriendlyByteBuf buf){
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            SelectedSpellData.set(index);
            System.out.println("Got Package on Client ");
        });
        return true;
    }
}
