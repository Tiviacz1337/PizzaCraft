package com.tiviacz.pizzacraft.crafting.mortar;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MortarRecipes 
{
	public void addRecipes(MortarRecipeManager manager) 
	{
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.FLOUR_CORN), 4, "cropCorn"));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.FLOUR), 4, new ItemStack(Items.WHEAT)));
		
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.OLIVE_OIL), 4, "cropOlive", new ItemStack(Items.GLASS_BOTTLE)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.OLIVE_OIL), 4, "cropOliveblack", new ItemStack(Items.GLASS_BOTTLE)));
		
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.PAPER_MASS, 3), 4, new ItemStack(Items.PAPER)));
		
		//Seeds Optional Way
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_BROCCOLI), 4, "cropBroccoli", new ItemStack(Items.WHEAT_SEEDS)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_CORN), 4, "cropCorn", new ItemStack(Items.WHEAT_SEEDS)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_CUCUMBER), 4, "cropCucumber", new ItemStack(Items.WHEAT_SEEDS)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_ONION), 4, "cropOnion", new ItemStack(Items.WHEAT_SEEDS)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_PEPPER), 4, "cropBellpepper", new ItemStack(Items.WHEAT_SEEDS)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_PINEAPPLE), 4, "cropPineapple", new ItemStack(Items.WHEAT_SEEDS)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModItems.SEED_TOMATO), 4, "cropTomato", new ItemStack(Items.WHEAT_SEEDS)));
		
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModBlocks.OLIVE_SAPLING), 4, "cropOlive", new ItemStack(Blocks.SAPLING)));
		manager.addRecipe(new BaseMortarShapelessOreRecipe(new ItemStack(ModBlocks.OLIVE_SAPLING), 4, "cropOliveblack", new ItemStack(Blocks.SAPLING)));
	}
}