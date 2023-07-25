package de.cron3x.netruscraft.common.networking.packets;

import de.cron3x.netruscraft.common.items.CatalystItem;
import de.cron3x.netruscraft.common.networking.PackageManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CycleSpellC2SPacket {
    public CycleSpellC2SPacket(){

    }

    public CycleSpellC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ServerLevel level = player.serverLevel();

            if (player.getMainHandItem().getItem() instanceof CatalystItem item) {
                countUpSpellIndex(player.getMainHandItem(), player);

            } else if (player.getOffhandItem().getItem() instanceof CatalystItem offHandItem) {
                countUpSpellIndex(player.getOffhandItem(), player);
            }
        });
        return true;
    }

    private void countUpSpellIndex(ItemStack stack, ServerPlayer player){
        int index = CatalystItem.getSpellBufferIndex(stack);

        int spellCount = (int) CatalystItem.getSpells(stack).count() -1;

        if (index < spellCount) ++index;
        else index = 0;

        CatalystItem.setSpellBufferIndex(stack, index);
        PackageManager.sendToPlayer(new CycleSpellS2CPacket(index), player);
        System.out.println("Got Package on Server  >>  CycleSpellS2CPacket ");
    }
}
