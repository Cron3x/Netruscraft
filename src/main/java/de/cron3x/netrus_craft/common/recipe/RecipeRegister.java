package de.cron3x.netrus_craft.common.recipe;

import de.cron3x.netrus_craft.NetrusCraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NetrusCraft.MODID);

    public static final RegistryObject<RecipeSerializer<CraftingAltarRecipe>> ALTAR_SERIALIZER = RECIPE_SERIALIZERS.register("altar_infusing", () ->
            CraftingAltarRecipe.Serializer.INSTANCE);
}
