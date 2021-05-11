package com.tiviacz.pizzacraft.compat.jei;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class DoughRecipeType
{
    public final Block dough;
    public final ItemStack rollingPin;

    public DoughRecipeType(Block dough, ItemStack rollingPin)
    {
        this.dough = dough;
        this.rollingPin = rollingPin;
    }

    public Block getDough()
    {
        return dough;
    }

    public ItemStack getRollingPin()
    {
        return rollingPin;
    }
}
