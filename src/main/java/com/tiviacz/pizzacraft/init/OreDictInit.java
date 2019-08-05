package com.tiviacz.pizzacraft.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictInit 
{
	public static void registerOres()
	{
		OreDictionary.registerOre("logWood", ModBlocks.OLIVE_LOG);
		OreDictionary.registerOre("treeLeaves", ModBlocks.OLIVE_LEAVES);
		OreDictionary.registerOre("treeSapling", ModBlocks.OLIVE_SAPLING);
		OreDictionary.registerOre("plankWood", ModBlocks.OLIVE_PLANKS);
		
		OreDictionary.registerOre("toolBakeware", ModBlocks.BAKEWARE);
		OreDictionary.registerOre("toolMortarandpestle", ModBlocks.MORTAR_AND_PESTLE);
		OreDictionary.registerOre("toolChoppingboard", ModBlocks.CHOPPING_BOARD);
		OreDictionary.registerOre("toolKnife", new ItemStack(ModItems.KNIFE, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("foodOliveoil", ModItems.OLIVE_OIL);
		
		OreDictionary.registerOre("listAllmeatraw", ModItems.HAM);
		OreDictionary.registerOre("listAllporkraw", ModItems.HAM);
		
		OreDictionary.registerOre("sliceTomato", ModItems.TOMATO_SLICE);
		OreDictionary.registerOre("sliceCucumber", ModItems.CUCUMBER_SLICE);
		OreDictionary.registerOre("sliceOnion", ModItems.ONION_SLICE);
		
		OreDictionary.registerOre("foodPizzaDough", ModItems.PIZZA_DOUGH);
		OreDictionary.registerOre("foodDough", ModBlocks.DOUGH);
		OreDictionary.registerOre("foodCheese", ModItems.CHEESE);
		OreDictionary.registerOre("foodFlour", ModItems.FLOUR);
		OreDictionary.registerOre("foodFlour", ModItems.FLOUR_CORN);
		
		OreDictionary.registerOre("listAllmushroom", Item.getItemById(39));
		OreDictionary.registerOre("listAllmushroom", Item.getItemById(40));
		
		OreDictionary.registerOre("cropOlive", ModItems.OLIVE);
		OreDictionary.registerOre("cropOliveblack", ModItems.BLACK_OLIVE);
		OreDictionary.registerOre("seedOnion", ModItems.SEED_ONION);
		OreDictionary.registerOre("cropOnion", ModItems.ONION);		
		OreDictionary.registerOre("seedBellpepper", ModItems.SEED_PEPPER);
		OreDictionary.registerOre("cropBellpepper", ModItems.PEPPER);	
		OreDictionary.registerOre("seedPineapple", ModItems.SEED_PINEAPPLE);
		OreDictionary.registerOre("cropPineapple", ModItems.PINEAPPLE);	
		OreDictionary.registerOre("seedTomato", ModItems.SEED_TOMATO);
		OreDictionary.registerOre("cropTomato", ModItems.TOMATO);
		OreDictionary.registerOre("seedCucumber", ModItems.SEED_CUCUMBER);
		OreDictionary.registerOre("cropCucumber", ModItems.CUCUMBER);
		OreDictionary.registerOre("seedCorn", ModItems.SEED_CORN);
		OreDictionary.registerOre("cropCorn", ModItems.CORN);
		OreDictionary.registerOre("seedBroccoli", ModItems.SEED_BROCCOLI);
		OreDictionary.registerOre("cropBroccoli", ModItems.BROCCOLI);
	}
}