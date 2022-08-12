package com.tiviacz.pizzacraft.common;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class PizzaCraftItemGroup extends CreativeModeTab
{
    public static final CreativeModeTab PIZZACRAFT = new PizzaCraftItemGroup(CreativeModeTab.TABS.length, PizzaCraft.MODID);

    private PizzaCraftItemGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ModBlocks.PIZZA.get());
    }
}
