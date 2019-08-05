package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemReturn extends ItemBase
{	
	public ItemReturn(String name)
	{
		super(name);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) 
	{
		if(itemstack.getItem() == ModItems.OLIVE_OIL)
		{
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		return new ItemStack(this);
	}

	@Override
	public boolean hasContainerItem(ItemStack itemstack) 
	{
		return true;
	}
}