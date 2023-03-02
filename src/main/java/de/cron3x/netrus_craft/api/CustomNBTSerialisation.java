package de.cron3x.netrus_craft.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomNBTSerialisation extends CompoundTag {

    // TODO: Save Result Item too, so can craft
    public byte[] ItemStackToBytes(ItemStack itemStack){

        var itemType = itemStack.getItem();
        var itemCount = itemStack.getCount();
        var itemDamage = itemStack.getDamageValue();
        var itemName = itemStack.getDisplayName();

        JsonObject itemObj = new JsonObject();
        System.out.println(itemType);
        itemObj.addProperty("type", itemType.getDescriptionId());
        itemObj.addProperty("count", itemCount);
        itemObj.addProperty("damage", itemDamage);
        System.out.println(itemName);
        itemObj.addProperty("name", itemName.toString());

        return itemObj.toString().getBytes();
    }

    public ItemStack bytesToItemStack(byte[] bytes){
        String json = new String(bytes, StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        jsonObject.get("name");
        jsonObject.get("damage").getAsInt();

        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(jsonObject.get("type").getAsString())), jsonObject.get("count").getAsInt());
    }

    public byte[] pedestalsToBytes(List<BlockPos> pedestals) {

        if (pedestals == null){
            System.out.println("pedestals are null");
            return null;
        }

        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i <= pedestals.toArray().length-1; ++i){
            JsonObject innerObject = new JsonObject();
            innerObject.addProperty("x", pedestals.get(i).getX());
            innerObject.addProperty("y", pedestals.get(i).getY());
            innerObject.addProperty("z", pedestals.get(i).getZ());
            jsonObject.add(String.valueOf(i), innerObject);
        }

        System.out.println(jsonObject.toString());

        return jsonObject.toString().getBytes();
    }

    public List<BlockPos> BytesToPedestals(byte[] bytes) {
        List<BlockPos> pedestals = new ArrayList<>();

        if (bytes == null || Arrays.toString(bytes).equals("[]")) return pedestals;

        String json = new String(bytes, StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        jsonObject.asMap().forEach((k, o) ->{
            JsonObject jo = jsonObject.get(k).getAsJsonObject();
            pedestals.add(new BlockPos(o.getAsJsonObject().get("x").getAsInt(), o.getAsJsonObject().get("y").getAsInt(), o.getAsJsonObject().get("z").getAsInt()));
        });
        return pedestals;
    }
}
