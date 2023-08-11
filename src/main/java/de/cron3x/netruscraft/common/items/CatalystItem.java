package de.cron3x.netruscraft.common.items;

import de.cron3x.netruscraft.common.networking.PackageManager;
import de.cron3x.netruscraft.common.networking.packets.CycleSpellC2SPacket;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public class CatalystItem extends Item {
    public CatalystItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pStack.getCount() != 1) {
            PackageManager.sendToServer(new CycleSpellC2SPacket());
            return false;
        }
        if (pAction != ClickAction.SECONDARY || !( pSlot.allowModification(pPlayer))) {
            PackageManager.sendToServer(new CycleSpellC2SPacket());
            return false;
        }

        if (pOther.isEmpty()) {
            removeOne(pStack).ifPresent(pAccess::set);
        } else {
            int i = add(pStack, pOther);
            if (i > 0) {
                pOther.shrink(i);
            }
        }

        PackageManager.sendToServer(new CycleSpellC2SPacket());
        return true;
    }

    private static Optional<ItemStack> removeOne(ItemStack stack) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        if (!compoundtag.contains("Spells")) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList("Spells", 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    stack.removeTagKey("Spells");
                }
                return Optional.of(itemstack);
            }
        }
    }
    private static int add(ItemStack pStack, ItemStack pOther) {
        if (!pOther.isEmpty() && pOther.getItem().canFitInsideContainerItems()) {
            CompoundTag compoundtag = pStack.getOrCreateTag();
            if (!compoundtag.contains("Spells")) {
                compoundtag.put("Spells", new ListTag());
            }

            int diff = getSpellBufferSize(pStack) - (int) getSpells(pStack).count()-1;
            int n = 1;
            if (n <= diff) {
                ListTag listtag = compoundtag.getList("Spells", 10);
                Optional<CompoundTag> optionalMatchingItem = getMatchingItem(pOther, listtag);
                if (optionalMatchingItem.isPresent()) {
                    return 0;
                } else {
                    ItemStack itemstack1 = pOther.copyWithCount(n);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, (Tag) compoundtag2);
                }
                return n;
            }
        }
        return 0;
    }

    public static Stream<ItemStack> getSpells(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList("Spells", 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    public static int getSpellBufferSize(ItemStack stack){
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        if (stack.getTag().contains("spell_buffer_size")) return stack.getTag().getInt("spell_buffer_size");
        setSpellBufferSize(stack, 1);
        return 1;
    }

    public static int getSpellBufferIndex(ItemStack stack){
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        if (stack.getTag().contains("spell_buffer_index")) return stack.getTag().getInt("spell_buffer_index");
        setSpellBufferIndex(stack, 0);
        return 0;
    }

    public static void setSpellBufferSize(ItemStack stack, int size){
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getTag().putInt("spell_buffer_size", size);
    }

    public static void setSpellBufferIndex(ItemStack stack, int index){
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getTag().putInt("spell_buffer_index", index);
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack stack, ListTag listTag) {
        return (stack.getItem() instanceof CatalystItem) ? Optional.empty() : listTag.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((compoundTag)
                -> ItemStack.isSameItemSameTags(ItemStack.of(compoundTag), stack)).findFirst();
    }

    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getSpells(pStack).forEach(nonnulllist::add);
        return Optional.of(new BundleTooltip(nonnulllist, getSpellBufferSize(pStack)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        castSpell(pLevel, pPlayer, pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void castSpell(Level pLevel, Player pPlayer, InteractionHand pUsedHand){
        ItemStack catalyst;
        if (pPlayer.getMainHandItem().getItem() instanceof CatalystItem) {
            catalyst = pPlayer.getMainHandItem();
        } else if (pPlayer.getOffhandItem().getItem() instanceof CatalystItem) {
            catalyst = pPlayer.getMainHandItem();
        } else return;

        //ItemStack spell = CatalystItem.getSpells(catalyst).toArray(ItemStack[]::new)[CatalystItem.getSpellBufferIndex(catalyst)];

    }
}
