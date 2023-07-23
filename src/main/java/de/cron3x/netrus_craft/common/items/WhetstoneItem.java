package de.cron3x.netrus_craft.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class WhetstoneItem extends Item {
    public WhetstoneItem(Properties properties) {
        super(properties);
    }

    public static float getTypeTexture(ItemStack stack){
        if (stack.getTag() == null) {
            return 0.0F;
        }

        String id = stack.getTag().getString("id");
        return switch (id) {
            case "fire"      -> 1.0F;
            case "poison"    -> 2.0F;
            case "bleed"     -> 3.0F;
            case "rot"       -> 4.0F;
            case "holy"      -> 5.0F;
            case "lightning" -> 6.0F;
            default          -> 0.0F;
        };
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction clickAction, Player player) {
        System.out.println("overrideStackedOnOther");
        if (self.getCount() != 1 || clickAction != ClickAction.SECONDARY) {
            return false;
        } else if (slot.getItem().getCount() != 1) {
            return false;
        }

        int selfLevel = self.getTag() == null ? 0 : self.getTag().getInt("lvl");
        String selfId = self.getTag() == null ? "raw" : self.getTag().getString("id");
        CompoundTag selfNBT = self.getTag();

        ItemStack carrier = slot.getItem();
        CompoundTag carrierNBT = carrier.getTag();

        CompoundTag whetstoneTag;
        String carrierId;
        int carrierLevel;

        whetstoneTag = carrierNBT.getCompound("whetstone");
        System.out.println("whetstoneTag: " + whetstoneTag);
        carrierId = whetstoneTag.getString("id");
        carrierId = carrierId.equals("") ? "raw" : carrierId;
        carrierLevel = whetstoneTag.getInt("lvl");


        List<Component> hoverText = List.of(Component.empty());

        TooltipFlag enable_featureflag = carrier.getItem().isEnabled(player.level().enabledFeatures()) ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL;
        System.out.println("enable_featureflag >> " + enable_featureflag);

        carrier.getItem().appendHoverText(carrier, player.level(), hoverText, enable_featureflag);

        // Save new NBT on Tool
        CompoundTag newCarrierNBT = new CompoundTag();
        newCarrierNBT.putString("id", selfId);
        newCarrierNBT.putInt("lvl", selfLevel);
        carrierNBT.put("whetstone", newCarrierNBT);
        carrier.setTag(carrierNBT);

        // Save new NBT on Whetstone
        selfNBT.putString("id", carrierId);
        selfNBT.putInt("lvl", carrierLevel);

        self.setTag(selfNBT);

        return true;
    }

    //TODO: Add color Codes, for more custom colors
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag flag) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) components.add(Component.translatable("item.netruscraft.whetstone.none_nbt").withStyle(ChatFormatting.GRAY));
        else {
            components = whetstoneHoverTextComponentSwitcher(components,stack);
        }
    }
    public List<Component> whetstoneHoverTextComponentSwitcher(List<Component> components, ItemStack stack){
        CompoundTag nbt = stack.getTag();
        switch (stack.getTag().getString("id")) {
            case "fire" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.fire.description").withStyle(ChatFormatting.GOLD));
            case "rot" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.rot.description").withStyle(ChatFormatting.DARK_RED));
            case "bleed" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.bleed.description").withStyle(ChatFormatting.RED));
            case "lightning" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.lightning.description").withStyle(ChatFormatting.AQUA));
            case "holy" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.holy.description").withStyle(ChatFormatting.YELLOW));
            case "poison" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.poison.description").withStyle(ChatFormatting.GREEN));
            case "raw" ->
                    components.add(Component.translatable("item.netruscraft.whetstone.raw.description").withStyle(ChatFormatting.GRAY));
            default ->
                    components.add(Component.translatable("item.netruscraft.whetstone.none_nbt").withStyle(ChatFormatting.RED));
        }
        components.add(Component.translatable("item.netruscraft.whetstone.level").withStyle(ChatFormatting.GRAY).append(""+nbt.getInt("lvl")));

        return components;
    }
}
