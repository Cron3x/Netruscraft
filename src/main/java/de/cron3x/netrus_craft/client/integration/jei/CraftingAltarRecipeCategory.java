package de.cron3x.netrus_craft.client.integration.jei;

import de.cron3x.netrus_craft.NetrusCraft;
import de.cron3x.netrus_craft.common.blocks.BlockRegister;
import de.cron3x.netrus_craft.common.recipe.CraftingAltarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CraftingAltarRecipeCategory implements IRecipeCategory<CraftingAltarRecipe> {

    public static final  ResourceLocation UID = new ResourceLocation(NetrusCraft.MODID, "crafting_infusion");
    private static final ResourceLocation TEXTURE = new ResourceLocation(NetrusCraft.MODID, "textures/gui/infusion_crafting_altar_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    private final IGuiHelper helper;

    public CraftingAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 169, 100);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegister.CRAFTINGALTAR_BLOCK.get()));
        this.helper = helper;
    }

    @Override
    public RecipeType<CraftingAltarRecipe> getRecipeType() {
        return JEINetrusCraftPlugin.INFUSION_CRAFTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Infusion Crafting Altar");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CraftingAltarRecipe recipe, IFocusGroup focuses) {
        IDrawable dayTime = recipe.isDay()
                ? helper.createDrawable(TEXTURE, 181, 33, 25, 25) : helper.createDrawable(TEXTURE, 181, 7, 25, 25);;

        builder.addSlot(RecipeIngredientRole.INPUT,49,16).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT,76,3).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,103,16).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT,116,43).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,103,70).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT,76,83).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT,49,70).addIngredients(recipe.getIngredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT,36,43).addIngredients(recipe.getIngredients().get(3));

        builder.addSlot(RecipeIngredientRole.OUTPUT,76,43).addItemStack(recipe.getResultItem()).setBackground(dayTime, -70,-35);
    }
}
