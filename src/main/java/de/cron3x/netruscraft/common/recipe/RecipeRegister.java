package de.cron3x.netruscraft.common.recipe;

import de.cron3x.netruscraft.Netruscraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Netruscraft.MODID);

    public static final RegistryObject<RecipeSerializer<CraftingAltarRecipe>> ALTAR_SERIALIZER = RECIPE_SERIALIZERS.register("altar_infusing", () ->
            CraftingAltarRecipe.Serializer.INSTANCE);
}
