package de.cron3x.netrus_craft.common.items.infusion_whetstones;

import de.cron3x.netrus_craft.common.items.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RawWhetstone extends Item {
    public RawWhetstone(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction clickAction, Player player) {
        System.out.println("overrideStackedOnOther");
        if (self.getCount() != 1 || clickAction != ClickAction.SECONDARY) {
            return false;
        } else if (slot.getItem().getCount() != 1) {
            return false;
        }
        else {
            ItemStack carrier = slot.getItem();
            CompoundTag nbt = carrier.getTag();
            if (nbt == null) {
                nbt = new CompoundTag();
            }
            CompoundTag wetstone = nbt.getCompound("whetstone");
            String id = wetstone.getString("id");
            int level = wetstone.getInt("lvl");

            //Works More or less maby construct a new nbt Compound and safe the level only on the whetstone

            carrier.addTagElement("whetstone", );

            ItemStack newItem = switch (id) {
                case "fire" -> new ItemStack(ItemRegister.WHETSTONE_FIRE.get());
                case "holy" -> new ItemStack(ItemRegister.WHETSTONE_HOLY.get());
                case "rot" -> new ItemStack(ItemRegister.WHETSTONE_ROT.get());
                case "bleed" -> new ItemStack(ItemRegister.WHETSTONE_BLEED.get());
                case "lightning" -> new ItemStack(ItemRegister.WHETSTONE_LIGHTNING.get());
                case "poison" -> new ItemStack(ItemRegister.WHETSTONE_POISON.get());
                case "raw" -> new ItemStack(ItemRegister.WHETSTONE_RAW.get());
                default -> self;
            };

            self = newItem;
            self.setTag(nbt);

            System.out.println(self+" | "+ nbt.getAsString());

            return true;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("item.netruscraft.whetstone_raw.description").withStyle(ChatFormatting.GRAY));
    }
}
