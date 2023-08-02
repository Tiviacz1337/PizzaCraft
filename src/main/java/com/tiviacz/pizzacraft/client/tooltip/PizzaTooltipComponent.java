package com.tiviacz.pizzacraft.client.tooltip;

import com.tiviacz.pizzacraft.util.NBTUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class PizzaTooltipComponent implements TooltipComponent
{
    protected ItemStackHandler inventory = new ItemStackHandler(10);
    protected ItemStack stack;

    public PizzaTooltipComponent(ItemStack stack)
    {
        this.stack = stack;
        NBTUtils.loadInventoryFromStack(stack, inventory);
    }

    public NonNullList<ItemStack> getIngredients()
    {
        NonNullList<ItemStack> nonNullList = NonNullList.create();

        for(int i = 0; i < inventory.getSlots() - 1; i++)
        {
            if(!inventory.getStackInSlot(i).isEmpty())
            {
                nonNullList.add(inventory.getStackInSlot(i));
            }
        }
        return nonNullList;
    }
}
