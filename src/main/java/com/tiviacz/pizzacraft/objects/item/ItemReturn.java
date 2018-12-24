package com.tiviacz.pizzacraft.objects.item;

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
		return new ItemStack(this);
	}

	@Override
	public boolean hasContainerItem(ItemStack itemstack) 
	{
		return true;
	}
}