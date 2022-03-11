package com.tiviacz.pizzacraft.common;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class PizzaCraftItemGroup extends ItemGroup
{
    public static final ItemGroup PIZZACRAFT = new PizzaCraftItemGroup(ItemGroup.TABS.length, PizzaCraft.MODID);

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
