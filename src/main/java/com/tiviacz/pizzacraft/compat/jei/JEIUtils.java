package com.tiviacz.pizzacraft.compat.jei;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.tiviacz.pizzacraft.compat.jei.chopping.ChoppingBoardRecipeWrapper;
import com.tiviacz.pizzacraft.crafting.bakeware.IBakewareRecipe;
import com.tiviacz.pizzacraft.crafting.chopping.ChoppingBoardRecipes;
import com.tiviacz.pizzacraft.crafting.mortar.IMortarRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

public class JEIUtils 
{
	public static List<ChoppingBoardRecipeWrapper> getRecipes(IJeiHelpers helpers)
	{
		IStackHelper stackHelper = helpers.getStackHelper();
		ChoppingBoardRecipes instance = ChoppingBoardRecipes.instance();
		Map<ItemStack, ItemStack> recipes = instance.getRecipes();
		List<ChoppingBoardRecipeWrapper> jeiRecipes = Lists.newArrayList();
		
		for(Entry<ItemStack, ItemStack> ent : recipes.entrySet())
		{
			ItemStack input = ent.getKey();
			ItemStack output = ent.getValue();
			ChoppingBoardRecipeWrapper recipe = new ChoppingBoardRecipeWrapper(input, output);
			jeiRecipes.add(recipe);
		}
		
		return jeiRecipes;
	}
	
    public static void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients, ICraftingGridHelper craftingGridHelper, XYProperties properties, int inputSlot, int outputSlot) 
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(outputSlot, false, properties.getX(), properties.getY());

        for(int y = 0; y < properties.getHeight(); ++y) 
        {
            for(int x = 0; x < properties.getWidth(); ++x) 
            {
                int index = inputSlot + x + (y * properties.getHeight());
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }

        if(recipeWrapper instanceof ICustomCraftingRecipeWrapper) 
        {
            ICustomCraftingRecipeWrapper customWrapper = (ICustomCraftingRecipeWrapper)recipeWrapper;
            customWrapper.setRecipe(recipeLayout, ingredients);
            return;
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        if(recipeWrapper instanceof IShapedCraftingRecipeWrapper) 
        {
            IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper)recipeWrapper;
            craftingGridHelper.setInputs(guiItemStacks, inputs, wrapper.getWidth(), wrapper.getHeight());
        } 
        
        else 
        {
        	craftingGridHelper.setInputs(guiItemStacks, inputs);
            recipeLayout.setShapeless();
        }

        if(!outputs.isEmpty()) 
        {
            guiItemStacks.set(outputSlot, outputs.get(0));
        }
    }
    
    public static void setMortarRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients, XYProperties properties, int inputSlot, int outputSlot) 
    {
    	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
    	
        guiItemStacks.init(outputSlot, false, properties.getX(), properties.getY());
        
        guiItemStacks.init(1, true, 4, 18); 	//Left
        guiItemStacks.init(2, true, 42, 18);	//Right
        guiItemStacks.init(3, true, 23, -5); 	//Up
        guiItemStacks.init(4, true, 23, 30);	//Down
        
        guiItemStacks.set(ingredients);
    }

    public static void getBakewareIngredients(IIngredients ingredients, IBakewareRecipe recipe, IJeiHelpers jeiHelpers, List inputItems) 
    {
    	IStackHelper stackHelper = jeiHelpers.getStackHelper();
    	List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(inputItems);
    	
    	ingredients.setInputLists(VanillaTypes.ITEM, inputs);
    	ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }
    
    public static void getMortarIngredients(IIngredients ingredients, IMortarRecipe recipe, IJeiHelpers jeiHelpers, List inputItems) 
    {
    	IStackHelper stackHelper = jeiHelpers.getStackHelper();
    	List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(inputItems);
    	
    	ingredients.setInputLists(VanillaTypes.ITEM, inputs);
    	ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }
}