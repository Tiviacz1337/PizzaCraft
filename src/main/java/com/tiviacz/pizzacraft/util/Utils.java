package com.tiviacz.pizzacraft.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class Utils
{
    public static boolean checkCount(ItemStack stack1, ItemStack stack2, int count)
    {
        return stack1.getCount() + stack2.getCount() <= count;
    }

    public static boolean checkItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return (!stack1.isEmpty() && stack1.getItem() == stack2.getItem());
    }

    public static boolean checkItemStacksAndCount(ItemStack stack1, ItemStack stack2, int count)
    {
        return checkItemStacks(stack1, stack2) && checkCount(stack1, stack2, count);
    }

    public static int getProperSlotForInsert(ItemStack stack, IItemHandlerModifiable inv)
    {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            if(inv.getStackInSlot(i).isEmpty()) // || Utils.checkItemStacksAndCount(inv.get(i), stack))
            {
                return i;
            }
        }
        return 0;
    }

    public static int getProperSlotForExtract(IItemHandlerModifiable inv)
    {
        for(int i = inv.getSlots() - 1; i > 0; i--)
        {
            if(!inv.getStackInSlot(i).isEmpty())
            {
                return i;
            }
        }
        return 0;
    }
}