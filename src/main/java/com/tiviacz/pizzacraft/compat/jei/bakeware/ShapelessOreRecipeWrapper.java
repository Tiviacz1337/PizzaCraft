package com.tiviacz.pizzacraft.compat.jei.bakeware;

import com.tiviacz.pizzacraft.compat.jei.JEIUtils;
import com.tiviacz.pizzacraft.crafting.bakeware.IBakewareRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ShapelessOreRecipeWrapper implements IRecipeWrapper 
{
    private final IJeiHelpers jeiHelpers;
    private final IBakewareRecipe recipe;
    private final NonNullList<Object> inputItems;

    public ShapelessOreRecipeWrapper(IJeiHelpers jeiHelpers, IBakewareRecipe recipe, NonNullList<Object> inputItems) 
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
        this.inputItems = inputItems;
        inputItems.stream().filter(itemStack -> itemStack instanceof ItemStack).filter(itemStack
            -> !((ItemStack) itemStack).isEmpty() && ((ItemStack) itemStack).getCount() != 1
        ).forEach(itemStack
            -> ((ItemStack) itemStack).setCount(1)
        );
    }

    @Override
    public void getIngredients(IIngredients ingredients) 
    {
        JEIUtils.getBakewareIngredients(ingredients, recipe, jeiHelpers, inputItems);
    }
}