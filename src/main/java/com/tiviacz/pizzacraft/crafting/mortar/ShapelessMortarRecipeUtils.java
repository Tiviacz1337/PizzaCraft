package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.List;

import com.google.common.collect.Lists;
import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessMortarRecipeUtils 
{
	public static boolean matches(List<ItemStack> input, TileEntityMortarAndPestle tile) 
    {
        List<ItemStack> list = Lists.newArrayList(input);

        for(int x = 0; x < 3; ++x) 
        {
            for(int y = 0; y < 3; ++y) 
            {
                ItemStack itemstack = getStackInRowAndColumn(tile, 3, 3);

                if(!itemstack.isEmpty()) 
                {
                    boolean flag = false;

                    for(ItemStack slotStack : list) 
                    {
                        if(itemstack.getItem() == slotStack.getItem() && (slotStack.getMetadata() == OreDictionary.WILDCARD_VALUE || itemstack.getMetadata() == slotStack.getMetadata())) 
                        {
                            flag = true;
                            list.remove(slotStack);
                            break;
                        }
                    }
                    if(!flag) 
                    {
                    	return false;
                    }
                }
            }
        }
        return list.isEmpty();
    }
	
	public static ItemStack getStackInRowAndColumn(TileEntityMortarAndPestle tile, int row, int column)
    {
        return row >= 0 && row < 3 && column >= 0 && column <= 3 ? tile.getInventory().getStackInSlot(row + column * 3) : ItemStack.EMPTY;
    }
}