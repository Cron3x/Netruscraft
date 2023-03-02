package de.cron3x.netrus_craft.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.cron3x.netrus_craft.NetrusCraft;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingAltarBlockEntity;
import de.cron3x.netrus_craft.common.items.ItemRegister;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

/*==================================================
 * Todo: fix 'recipe not pressent'
 *  Maybe I have to save the container first?
 *==================================================*/

public class CraftingAltarRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final boolean isDay;

    public CraftingAltarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, boolean isDay) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.isDay = isDay;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();

        boolean isLevelDay = pContainer.getItem(0).getItem() == ItemRegister.RUNE_TIME_DAY.get();

        System.out.println(pContainer.getItem(0));

        for(int j = 1; j < pContainer.getContainerSize(); ++j) {
            ItemStack itemstack = pContainer.getItem(j);
            if (!itemstack.isEmpty()) {
                inputs.add(itemstack);
            } else {
                inputs.add(ItemStack.EMPTY);
            }
        }

        System.out.println(isLevelDay);

        boolean isSameTime = (isLevelDay == isDay());

        return net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.recipeItems) != null && isSameTime;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public boolean isDay() {
        return isDay;
    }

    @Override
    public net.minecraft.world.item.crafting.RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CraftingAltarRecipeType.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<CraftingAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static ResourceLocation ID = new ResourceLocation(NetrusCraft.MODID, "altar_infusing");

        @Override
        public CraftingAltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            String dayString = GsonHelper.getAsString(pSerializedRecipe, "time");
            boolean isDay = dayString.equalsIgnoreCase("day");

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(8, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); ++i ) {
                if (i >= ingredients.size()) break;
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CraftingAltarRecipe(pRecipeId, output, inputs, isDay);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CraftingAltarRecipe pRecipe) {
            pBuffer.writeBoolean(pRecipe.isDay);

            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.recipeItems ){
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
        }

        @Override
        public @Nullable CraftingAltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            boolean isDay = pBuffer.readBoolean();

            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i <inputs.size(); ++i ) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new CraftingAltarRecipe(pRecipeId, output, inputs, isDay);
        }
    }
}
