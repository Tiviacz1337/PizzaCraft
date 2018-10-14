package com.tiviacz.pizzacraft.objects.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemReturn extends Item implements IHasModel
{
	public ItemReturn(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		this.setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() 
	{
		PizzaCraft.proxy.registerItemRenderer(this, 0, "inventory");		
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
