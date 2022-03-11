package com.tiviacz.pizzacraft.compat.jei;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class ChoppingRecipeCategory implements IRecipeCategory<ChoppingRecipe>
{
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "chopping");

    private final IDrawable background;
    private final IDrawable icon;
    private final String title;

    public ChoppingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/chopping_recipe.png"), -14, -5, 95, 31);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.OAK_CHOPPING_BOARD.get()));
        title = I18n.get("recipecategory." + PizzaCraft.MODID + ".chopping");
    }

    @Override
    public ResourceLocation getUid()
    {
        return ID;
    }

    @Override
    public Class<? extends ChoppingRecipe> getRecipeClass()
    {
        return ChoppingRecipe.class;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(ChoppingRecipe choppingRecipe, IIngredients iIngredients)
    {
        List<Ingredient> ingredients = choppingRecipe.getIngredients();
        iIngredients.setInputIngredients(ingredients);
        iIngredients.setOutput(VanillaTypes.ITEM, choppingRecipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ChoppingRecipe choppingRecipe, IIngredients iIngredients)
    {
        IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();

        stacks.init(0, true, 14, 6);
        stacks.set(0, Arrays.asList(choppingRecipe.getInput().getItems()));

        stacks.init(1, true, 63, 6);
        stacks.set(1, choppingRecipe.getResultItem());
    }
}