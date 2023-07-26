package de.cron3x.netruscraft.api.anvil_crafting;

import de.cron3x.netruscraft.NetrusCraft;
import de.cron3x.netruscraft.common.items.CatalystItem;
import de.cron3x.netruscraft.common.items.ItemRegister;
import de.cron3x.netruscraft.common.items.catalyst_runes.CatalystAdvanceRune;
import de.cron3x.netruscraft.common.items.catalyst_runes.SizeIncreaseRune;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

public class CustomAnvilCrafting {

    @Mod.EventBusSubscriber(modid = NetrusCraft.MODID, value = Dist.CLIENT)
    public static class AnvilEvents{
        @SubscribeEvent
        public static void onAnvilChange(AnvilUpdateEvent event){
            if (event.getPlayer().level() instanceof ServerLevel serverLevel) {
                ItemStack leftItem = event.getLeft();
                ItemStack rightItem = event.getRight();

                if ((leftItem.getItem() instanceof CatalystItem) && (rightItem.getItem() instanceof CatalystAdvanceRune)) {
                    System.out.println("CatalystItem + CatalystAdvanceRune");
                    Optional<ItemStack> resOpt = handleAdvanceRuneRecipe(leftItem, rightItem);
                    if (resOpt.isEmpty()) {
                        System.out.println("resOpt is empty");
                        return;
                    }
                    event.setCost(5);
                    event.setMaterialCost(5);
                    event.setOutput(resOpt.get());
                } else if ((leftItem.getItem() instanceof CatalystItem) && (rightItem.getItem() instanceof CatalystItem)){
                    System.out.println("CatalystAdvanceRune + CatalystAdvanceRune");
                }
            }
        }

        @SubscribeEvent
        public static void onAnvilRepair(AnvilRepairEvent event){
            System.out.println("Anvil Repair Fired");
        }
    }

    private static Optional<ItemStack> handleAdvanceRuneRecipe(ItemStack leftItem, ItemStack rightItem) {
        if (rightItem.getItem() == ItemRegister.CATALYST_ADVANCE_RUNE_SIZE.get()){
            int runeBufSize = SizeIncreaseRune.getSize(rightItem);
            int catalystBufSize = CatalystItem.getSpellBufferSize(leftItem);

            ItemStack result = leftItem.copy();
            CatalystItem.setSpellBufferSize(result, runeBufSize+catalystBufSize);
            return Optional.of(result);
        }
        return Optional.empty();
    }
}

