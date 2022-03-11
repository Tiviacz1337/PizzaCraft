package com.tiviacz.pizzacraft.compat.jei;

import net.minecraft.item.crafting.Ingredient;

public class PizzaIngredientsType
{
    public final Ingredient tagName;
    public final int maxSize;

    public PizzaIngredientsType(Ingredient tagName, int maxSize)
    {
        this.tagName = tagName;
        this.maxSize = maxSize;
    }
}