package com.tiviacz.pizzacraft.objects.item;

import com.tiviacz.pizzacraft.init.base.ItemBase;

import net.minecraft.item.ItemStack;

public class ItemKnife extends ItemBase
{
	public ItemKnife(String name) 
	{
		super(name);
		
		setMaxStackSize(1);
		setMaxDamage(24);
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemstack) 
	{
		ItemStack stack = itemstack.copy();
		stack.setItemDamage(stack.getItemDamage() + 1);
		stack.setCount(1);
		return stack;
	}

	@Override
	public boolean hasContainerItem(ItemStack itemstack) 
	{
		return true;
	}
}