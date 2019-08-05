package com.tiviacz.pizzacraft.compat.jei.mortar;

import com.tiviacz.pizzacraft.compat.jei.JEIUtils;
import com.tiviacz.pizzacraft.crafting.mortar.IMortarRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MortarShapelessOreRecipeWrapper implements IRecipeWrapper 
{
    private final IJeiHelpers jeiHelpers;
    private final IMortarRecipe recipe;
    private final NonNullList<Object> inputItems;

    public MortarShapelessOreRecipeWrapper(IJeiHelpers jeiHelpers, IMortarRecipe recipe, NonNullList<Object> inputItems) 
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
        JEIUtils.getMortarIngredients(ingredients, recipe, jeiHelpers, inputItems);
    }
}