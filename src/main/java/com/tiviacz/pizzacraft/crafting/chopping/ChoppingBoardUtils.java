package com.tiviacz.pizzacraft.crafting.chopping;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ChoppingBoardUtils 
{
	public static boolean isItemValid(ItemStack stack)
	{
		Item item = stack.getItem();
		
		for(ItemStack itemstack : OreDictionary.getOres("cropTomato"))
		{
			if(item == itemstack.getItem())
			{
				return true;
			}
		}
		
		for(ItemStack itemstack : OreDictionary.getOres("cropCucumber"))
		{
			if(item == itemstack.getItem())
			{
				return true;
			}
		}
		
		for(ItemStack itemstack : OreDictionary.getOres("cropOnion"))
		{
			if(item == itemstack.getItem())
			{
				return true;
			}
		}
		
		if(item == Items.PORKCHOP)
		{
			return true;
		}

		return false;
	}
}