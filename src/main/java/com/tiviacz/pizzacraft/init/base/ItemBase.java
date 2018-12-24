package com.tiviacz.pizzacraft.init.base;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		
		ModItems.ITEMS.add(this);
	}
}