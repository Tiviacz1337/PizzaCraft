package com.tiviacz.pizzacraft.util;

import java.util.ArrayList;
import java.util.List;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class Reference 
{
	public static final int GUI_BAKEWARE = 0;
	public static final int GUI_PIZZA_BAG = 1;
	
	public static final int SHANKS_MARGHERITA = 2;
	public static final int SHANKS_FUNGHI = 2;
	public static final int SHANKS_PROSCIUTTO = 2;
	public static final int SHANKS_MEAT = 3;
	public static final int SHANKS_CLASSIC = 2;
	public static final int SHANKS_CAPRICIOSA = 3;
	public static final int SHANKS_HAWAIIAN = 2;
	public static final int SHANKS_TOSCANA = 3;
	public static final int SHANKS_RUSTICA = 3;
	public static final int SHANKS_VEGETARIAN = 3;
	public static final int SHANKS_POMPEA = 3;
	
	public static final float SATURATION = 1.2F;
	
	public static List<Item> getValidBagItems()
	{
		List<Item> items = new ArrayList<Item>();
		
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_0));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_1));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_2));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_3));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_4));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_5));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_6));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_7));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_8));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_9));
		items.add(Item.getItemFromBlock(ModBlocks.PIZZA_BOX_10));
		
		return items;
	}
	
	public static List<Item> getIngredientsList(TileEntityBakeware tile)
	{
		List<Item> ingredients = new ArrayList<Item>();
		
		for(int i = 0; i < tile.getSizeInventory(); i++)
		{
			ItemStack stack = tile.getStackInSlot(i);
			
			if(stack.getItem() instanceof ItemFood)
			{
				ingredients.add(stack.getItem());
			}
		}
		
		ingredients.add(Item.getItemById(39));
		ingredients.add(Item.getItemById(40));
		
		return ingredients;
	}
}