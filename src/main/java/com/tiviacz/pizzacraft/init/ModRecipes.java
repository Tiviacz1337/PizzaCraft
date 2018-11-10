package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes 
{
	public static void initRecipes()
	{
		ResourceLocation pizzaGroup = new ResourceLocation(PizzaCraft.MODID + ":pizzaslicegroup");
		
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_0"), pizzaGroup, new ItemStack(ModItems.SLICE_0, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_0)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_1"), pizzaGroup, new ItemStack(ModItems.SLICE_1, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_1)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_2"), pizzaGroup, new ItemStack(ModItems.SLICE_2, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_2)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_3"), pizzaGroup, new ItemStack(ModItems.SLICE_3, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_3)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_4"), pizzaGroup, new ItemStack(ModItems.SLICE_4, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_4)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_5"), pizzaGroup, new ItemStack(ModItems.SLICE_5, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_5)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_6"), pizzaGroup, new ItemStack(ModItems.SLICE_6, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_6)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_7"), pizzaGroup, new ItemStack(ModItems.SLICE_7, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_7)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_8"), pizzaGroup, new ItemStack(ModItems.SLICE_8, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_8)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_9"), pizzaGroup, new ItemStack(ModItems.SLICE_9, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_9)))});
		GameRegistry.addShapelessRecipe(new ResourceLocation(PizzaCraft.MODID + ":slice_10"), pizzaGroup, new ItemStack(ModItems.SLICE_10, 6), new Ingredient[] {Ingredient.fromStacks(new ItemStack(ModItems.KNIFE, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_10)))});
	}
}
