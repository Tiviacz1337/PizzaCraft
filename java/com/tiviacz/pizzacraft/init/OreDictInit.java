package com.tiviacz.pizzacraft.init;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictInit 
{
	
	public static void registerOres()
	{
		OreDictionary.registerOre("toolBakeware", ModItems.bakeware);
		OreDictionary.registerOre("toolMortarandpestle", ModItems.mortar_and_pestle);
		
		OreDictionary.registerOre("foodDough", ModItems.pizza_dough);
		OreDictionary.registerOre("foodCheese", ModItems.cheese);
		OreDictionary.registerOre("foodFlour", ModItems.flour);
		
		OreDictionary.registerOre("listAllmushroom", Item.getItemById(39));
		OreDictionary.registerOre("listAllmushroom", Item.getItemById(40));
		
		OreDictionary.registerOre("cropOlive", ModItems.olive);
		OreDictionary.registerOre("seedOnion", ModItems.seed_onion);
		OreDictionary.registerOre("cropOnion", ModItems.onion);		
		OreDictionary.registerOre("seedBellpepper", ModItems.seed_pepper);
		OreDictionary.registerOre("cropBellpepper", ModItems.pepper);	
		OreDictionary.registerOre("seedPineapple", ModItems.seed_pineapple);
		OreDictionary.registerOre("cropPineapple", ModItems.pineapple);	
		OreDictionary.registerOre("seedTomato", ModItems.seed_tomato);
		OreDictionary.registerOre("cropTomato", ModItems.tomato);
		OreDictionary.registerOre("seedCucumber", ModItems.seed_cucumber);
		OreDictionary.registerOre("cropCucumber", ModItems.cucumber);
		OreDictionary.registerOre("seedCorn", ModItems.seed_corn);
		OreDictionary.registerOre("cropCorn", ModItems.corn);
		OreDictionary.registerOre("seedBroccoli", ModItems.seed_broccoli);
		OreDictionary.registerOre("cropBroccoli", ModItems.broccoli);
	}
}
