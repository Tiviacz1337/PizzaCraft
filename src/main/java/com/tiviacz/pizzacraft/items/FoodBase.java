package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.item.ItemFood;

public class FoodBase extends ItemFood implements IHasModel
{
	public FoodBase(String name, int amount, float saturation, boolean isWolfFood) 
	{
		super(amount, saturation, isWolfFood);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
}