package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.tiviacz.pizzacraft.crafting.bakeware.ItemStackUtils;
import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessMortarOreRecipeUtils 
{
    public static void createRecipe(ItemStack output, List<Object> input, Object... recipe) 
    {
        Arrays.stream(recipe).forEachOrdered(in -> 
        {
        	if(in instanceof ItemStack) 
            {
                input.add(((ItemStack)in).copy());
            } 
        	
        	else if(in instanceof Item || in instanceof Block) 
        	{
                input.add(ItemStackUtils.getItemStack(in));
            } 
        	
        	else if(in instanceof String) 
        	{
                input.add(OreDictionary.getOres((String)in));
            } 
        	
        	else 
        	{
                StringBuilder ret = new StringBuilder("Invalid shapeless ore recipe: ");
                Arrays.stream(recipe).forEachOrdered(tmp -> ret.append(tmp).append(", "));
                ret.append(output);
                throw new RuntimeException(ret.toString());
            }
        });
    }

    public static boolean matches(List<Object> input, TileEntityMortarAndPestle tile) 
    {
        List<Object> required = new ArrayList();
        required.addAll(input);

        for(int x = 0; x < tile.getInventory().getSlots(); x++) 
        {
            ItemStack slot = tile.getInventory().getStackInSlot(x);

            if(!slot.isEmpty()) 
            {
                boolean inRecipe = false;

                for(Object aRequired : required) 
                {
                    boolean match = false;

                    if(aRequired instanceof ItemStack) 
                    {
                        match = OreDictionary.itemMatches((ItemStack) aRequired, slot, false);
                    } 
                    
                    else if(aRequired instanceof List) 
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>) aRequired).iterator();
                        
                        while(itr.hasNext() && !match) 
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if(match) 
                    {
                        inRecipe = true;
                        required.remove(aRequired);
                        break;
                    }
                }

                if(!inRecipe) 
                {
                    return false;
                }
            }
        }
        return required.isEmpty();
    }
}