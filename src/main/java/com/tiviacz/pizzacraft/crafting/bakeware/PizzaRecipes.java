package com.tiviacz.pizzacraft.crafting.bakeware;

import com.tiviacz.pizzacraft.init.ModBlocks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PizzaRecipes 
{
	public void addRecipes(PizzaCraftingManager manager) 
	{
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_0, 1), "foodPizzaDough", "foodCheese"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_1, 1), "foodPizzaDough", "foodCheese", "listAllmushroom"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_2, 1), "foodPizzaDough", "foodCheese", "listAllporkraw"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_3, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "sliceOnion", new ItemStack(Items.BEEF), new ItemStack(Items.CHICKEN)));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_4, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "listAllmushroom"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_5, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "listAllmushroom", "cropOlive", "cropBellpepper"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_6, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "cropPineapple"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_7, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "listAllmushroom", "sliceTomato", "sliceCucumber"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_8, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "listAllmushroom", "cropCorn", "cropBellpepper"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_9, 1), "foodPizzaDough", "foodCheese", "cropBroccoli", "sliceTomato", "cropCorn", "cropBellpepper"));
		manager.addRecipe(new BaseShapelessOreRecipe(new ItemStack(ModBlocks.RAW_PIZZA_10, 1), "foodPizzaDough", "foodCheese", "listAllporkraw", "listAllmushroom", "sliceOnion", "cropBellpepper", "cropOliveblack"));
	}
}