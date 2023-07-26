package de.cron3x.netruscraft.datagen;

import de.cron3x.netruscraft.NetrusCraft;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetrusCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorRegisterer {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new ModItemModelProvider(packOutput, NetrusCraft.MODID, existingFileHelper));
    }
}
