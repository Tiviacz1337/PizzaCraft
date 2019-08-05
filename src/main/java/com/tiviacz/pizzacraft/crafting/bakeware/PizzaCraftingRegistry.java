package com.tiviacz.pizzacraft.crafting.bakeware;

import net.minecraft.item.ItemStack;

public class PizzaCraftingRegistry 
{
    public static void addShapelessRecipe(ItemStack output, Object... params) 
    {
        PizzaCraftingManager.getPizzaCraftingInstance().addShapelessRecipe(output, params);
    }

    public static void addRecipe(IBakewareRecipe recipe)
    {
    	PizzaCraftingManager.getPizzaCraftingInstance().getRecipeList().add(recipe);
    }

    public static void removeRecipe(IBakewareRecipe recipe) 
    {
        PizzaCraftingManager.getPizzaCraftingInstance().getRecipeList().remove(recipe);
    }
}