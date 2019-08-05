package com.tiviacz.pizzacraft.crafting.mortar;

import net.minecraft.item.ItemStack;

public class MortarRecipeRegistry 
{
    public static void addShapelessRecipe(ItemStack output, int duration, Object... params) 
    {
        MortarRecipeManager.getMortarManagerInstance().addShapelessRecipe(output, duration, params);
    }

    public static void addRecipe(IMortarRecipe recipe)
    {
    	MortarRecipeManager.getMortarManagerInstance().getRecipeList().add(recipe);
    }

    public static void removeRecipe(IMortarRecipe recipe) 
    {
    	MortarRecipeManager.getMortarManagerInstance().getRecipeList().remove(recipe);
    }
}