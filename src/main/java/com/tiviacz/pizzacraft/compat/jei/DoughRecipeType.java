package com.tiviacz.pizzacraft.compat.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

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
