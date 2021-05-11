package com.tiviacz.pizzacraft.compat.jei;

import net.minecraft.item.ItemStack;

public class CrushingRecipeType
{
    public final ItemStack input;
    public final int count;

    public final ItemStack output;

    public CrushingRecipeType(ItemStack input, int count, ItemStack output)
    {
        this.input = input;
        this.count = count;
        this.output = output;
    }

    public ItemStack getInput()
    {
        return input;
    }

    public int getCount()
    {
        return count;
    }

    public ItemStack getOutput()
    {
        return output;
    }
}
