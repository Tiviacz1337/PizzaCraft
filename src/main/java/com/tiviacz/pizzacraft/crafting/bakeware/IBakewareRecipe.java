package com.tiviacz.pizzacraft.crafting.bakeware;

import java.util.stream.IntStream;

import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public interface IBakewareRecipe 
{
    public boolean matches(InventoryCraftingImproved inv, World worldIn);

    public ItemStack getCraftingResult(InventoryCraftingImproved inv);

    public int getRecipeSize();

    public ItemStack getRecipeOutput();

    default NonNullList<ItemStack> getRemainingItems(InventoryCraftingImproved inv) 
    {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        IntStream.range(0, ret.size()).forEach(i -> ret.set(i, ForgeHooks.getContainerItem(inv.getStackInSlot(i))));
        return ret;
    }
}