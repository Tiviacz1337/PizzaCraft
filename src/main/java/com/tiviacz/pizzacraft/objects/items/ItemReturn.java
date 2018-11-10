package com.tiviacz.pizzacraft.objects.items;

import com.tiviacz.pizzacraft.init.base.ItemBase;

import net.minecraft.item.ItemStack;

public class ItemReturn extends ItemBase
{
	public ItemReturn(String name)
	{
		super(name);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) 
	{
		return itemstack;
	}

	@Override
	public boolean hasContainerItem(ItemStack itemstack) 
	{
		return true;
	}
}
