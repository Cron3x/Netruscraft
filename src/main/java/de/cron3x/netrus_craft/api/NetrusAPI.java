package de.cron3x.netrus_craft.api;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class NetrusAPI {
    public static Boolean isDay(){
        Level world =  Minecraft.getInstance().level;
        if (world == null) return null;
        return world.getDayTime() < 12400;
    }
}
