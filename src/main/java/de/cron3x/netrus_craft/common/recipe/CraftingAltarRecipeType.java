package de.cron3x.netrus_craft.common.recipe;

import net.minecraft.world.item.crafting.RecipeType;

public class CraftingAltarRecipeType implements RecipeType<CraftingAltarRecipe>{
        private CraftingAltarRecipeType(){ }
        public static final CraftingAltarRecipeType INSTANCE = new CraftingAltarRecipeType();
        public static final String ID = "altar_infusing";
}
