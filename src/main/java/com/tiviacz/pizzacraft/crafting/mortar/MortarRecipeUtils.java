package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.Arrays;
import java.util.List;

import com.tiviacz.pizzacraft.crafting.bakeware.ItemStackUtils;
import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class MortarRecipeUtils 
{
    public static void addShapelessRecipe(List<ItemStack> list, Object... recipeComponents) 
    {
        Arrays.stream(recipeComponents).forEachOrdered(object -> 
        {
            if(object instanceof ItemStack) 
            {
                list.add(((ItemStack)object).copy());
            } 
            else if(object instanceof Item) 
            {
                list.add(ItemStackUtils.getItemStack(object));
            } 
            else 
            {
                if(!(object instanceof Block)) 
                {
                    throw new AssertionError("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }
                
                list.add(ItemStackUtils.getItemStack(object));
            }
        });
    }

    public static ItemStack findMatchingRecipe(List<IMortarRecipe> recipes, TileEntityMortarAndPestle tile, World worldIn) 
    {
        return recipes.stream().filter(irecipe -> irecipe.matches(tile, worldIn)).findFirst().map(irecipe -> irecipe.getResult()).orElse(ItemStack.EMPTY);
    }
    
    public static int getRecipeDuration(List<IMortarRecipe> recipes, TileEntityMortarAndPestle tile, World worldIn)
    {
    	return recipes.stream().filter(irecipe -> irecipe.matches(tile, worldIn)).findFirst().get().getDuration();
    }

    public static List<ItemStack> getRemainingItems(List<IMortarRecipe> recipes, TileEntityMortarAndPestle tile, World worldIn) 
    {
        for(IMortarRecipe recipe : recipes) 
        {
            if(recipe.matches(tile, worldIn)) 
            {
                return recipe.getRemainingItems(tile);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(tile.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) 
        {
            nonnulllist.set(i, tile.getInventory().getStackInSlot(i));
        }
        return nonnulllist;
    }

    public static void onTake(TileEntityMortarAndPestle tile, List<ItemStack> input) 
    {
    	ItemStack slot0 = tile.getInventory().getStackInSlot(0);
    	ItemStack slot1 = tile.getInventory().getStackInSlot(1);
    	ItemStack slot2 = tile.getInventory().getStackInSlot(2);
    	ItemStack slot3 = tile.getInventory().getStackInSlot(3);
    	
    	if(!slot0.isEmpty())
    	{
    		tile.decrStackSize(0, 1);
    	}
    	
    	if(!slot1.isEmpty())
    	{
    		tile.decrStackSize(1, 1);
    	}
    	
    	if(!slot2.isEmpty())
    	{
    		tile.decrStackSize(2, 1);
    	}
    	
    	if(!slot3.isEmpty())
    	{
    		tile.decrStackSize(3, 1);
    	}
    }
}