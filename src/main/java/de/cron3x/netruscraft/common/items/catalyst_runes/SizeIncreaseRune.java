package de.cron3x.netruscraft.common.items.catalyst_runes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class SizeIncreaseRune extends CatalystAdvanceRune {
    public SizeIncreaseRune(Properties pProperties) {
        super(pProperties);
    }

    public static int getSize(ItemStack item){
        if (!item.hasTag()) setSize(item, 1);
        return item.getTag().getInt("spell_buffer_size");
    }

    public static void setSize(ItemStack item, int value){
        if (!item.hasTag()) item.setTag(new CompoundTag());
        item.getTag().putInt("spell_buffer_size", value);
    }
}