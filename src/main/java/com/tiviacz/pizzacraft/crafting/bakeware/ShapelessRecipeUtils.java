package com.tiviacz.pizzacraft.crafting.bakeware;

import java.util.List;

import com.google.common.collect.Lists;
import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessRecipeUtils 
{
    public static boolean matches(NonNullList<ItemStack> input, InventoryCraftingImproved inv) 
    {
        List<ItemStack> list = Lists.newArrayList(input);

        for(int x = 0; x < inv.getWidth(); ++x) {
            for(int y = 0; y < inv.getHeight(); ++y) {
                ItemStack itemstack = inv.getStackInRowAndColumn(y, x);

                if(!itemstack.isEmpty()) {
                    boolean flag = false;

                    for(ItemStack slotStack : list) {
                        if(itemstack.getItem() == slotStack.getItem() && (slotStack.getMetadata() == OreDictionary.WILDCARD_VALUE || itemstack.getMetadata() == slotStack.getMetadata())) {
                            flag = true;
                            list.remove(slotStack);
                            break;
                        }
                    }

                    if(!flag) return false;
                }
            }
        }
        return list.isEmpty();
    }
}