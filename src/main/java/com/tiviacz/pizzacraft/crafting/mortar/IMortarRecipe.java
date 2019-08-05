package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.stream.IntStream;

import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public interface IMortarRecipe 
{
	public boolean matches(TileEntityMortarAndPestle tile, World worldIn);

    public ItemStack getResult();

    public int getRecipeSize();

    public ItemStack getRecipeOutput();
    
    public int getDuration();

    default NonNullList<ItemStack> getRemainingItems(TileEntityMortarAndPestle tile) 
    {
        NonNullList<ItemStack> ret = NonNullList.withSize(tile.getSizeInventory(), ItemStack.EMPTY);
        IntStream.range(0, ret.size()).forEach(i -> ret.set(i, ForgeHooks.getContainerItem(tile.getInventory().getStackInSlot(i))));
        return ret;
    }
}