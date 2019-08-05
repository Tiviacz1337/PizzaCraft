package com.tiviacz.pizzacraft.crafting.chopping;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ChoppingBoardRecipes
{
	private static final ChoppingBoardRecipes CHOPPING_BOARD_RECIPES = new ChoppingBoardRecipes();
	
	private final Map<ItemStack, ItemStack> choppingList = Maps.<ItemStack, ItemStack>newHashMap();
	
	public static ChoppingBoardRecipes instance()
	{
		return CHOPPING_BOARD_RECIPES;
	}
	
	private ChoppingBoardRecipes()
	{
		this.createOreChoppingRecipe("cropTomato", new ItemStack(ModItems.TOMATO_SLICE, 2));
		this.createOreChoppingRecipe("cropCucumber", new ItemStack(ModItems.CUCUMBER_SLICE, 2));
		this.createOreChoppingRecipe("cropOnion", new ItemStack(ModItems.ONION_SLICE, 2));
		this.addChoppingRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ModItems.HAM, 3));
	}
	
	public void createOreChoppingRecipe(String entry, ItemStack result)
	{
		for(ItemStack stack : OreDictionary.getOres(entry))
		{
			this.addChoppingRecipe(stack, result);
		}
	}
	
	public void addChoppingRecipe(ItemStack input, ItemStack stack)
    {
        this.choppingList.put(input, stack);
    }
	
	public ItemStack getChoppingResult(ItemStack stack)
    {	
        for(Entry<ItemStack, ItemStack> entry : this.choppingList.entrySet())
        {
            if(this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }
	
	public Map<ItemStack, ItemStack> getRecipes()
	{
		return this.choppingList;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}