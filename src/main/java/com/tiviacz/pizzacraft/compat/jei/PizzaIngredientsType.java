package com.tiviacz.pizzacraft.compat.jei;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

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