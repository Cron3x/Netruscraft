package de.cron3x.netruscraft.client.integration.jei;

import de.cron3x.netruscraft.NetrusCraft;
import de.cron3x.netruscraft.common.items.ItemRegister;
import de.cron3x.netruscraft.common.recipe.CraftingAltarRecipe;
import de.cron3x.netruscraft.common.recipe.CraftingAltarRecipeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEINetrusCraftPlugin implements IModPlugin {
    public static RecipeType<CraftingAltarRecipe> INFUSION_CRAFTING_TYPE = new RecipeType<>(CraftingAltarRecipeCategory.UID, CraftingAltarRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(NetrusCraft.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CraftingAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<CraftingAltarRecipe> recipesInfusionCrafting = manager.getAllRecipesFor(CraftingAltarRecipeType.INSTANCE);
        registration.addRecipes(INFUSION_CRAFTING_TYPE, recipesInfusionCrafting);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ItemRegister.CRAFTINGALTAR_BLOCK_ITEM.get().getDefaultInstance(), INFUSION_CRAFTING_TYPE);
    }
}
