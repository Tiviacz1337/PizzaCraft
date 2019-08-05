package com.tiviacz.pizzacraft.compat.jei.chopping;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class ChoppingBoardRecipeWrapper implements IRecipeWrapper
{
	private final ItemStack input;
	private final ItemStack output;
	
	public ChoppingBoardRecipeWrapper(ItemStack input, ItemStack output)
	{
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInput(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
