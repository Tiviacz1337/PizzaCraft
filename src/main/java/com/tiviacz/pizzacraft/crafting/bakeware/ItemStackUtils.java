package com.tiviacz.pizzacraft.crafting.bakeware;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackUtils 
{
	public static ItemStack getItemStack(String modid, String itemName, int meta) 
	{
        return getItemStack(Item.getByNameOrId(modid + ":" + itemName), meta);
    }

    public static ItemStack getItemStack(String modid, String itemName) 
    {
        return getItemStack(modid, itemName, 0);
    }

    public static ItemStack getItemStack(Object type, int amount, int meta) 
    {
        if(type instanceof String) 
        {
            return new ItemStack(Objects.requireNonNull(Item.getByNameOrId((String) type)), amount, meta);
        } 
        
        else if(type instanceof Block) 
        {
            return new ItemStack((Block) type, amount, meta);
        } 
        
        else if(type instanceof Item) 
        {
            return new ItemStack((Item) type, amount, meta);
        }
        
        return ItemStack.EMPTY;
    }


    public static ItemStack getItemStack(Object type, int meta) 
    {
        if(type instanceof String) 
        {
            return getItemStack(Item.getByNameOrId((String) type), 1, meta);
        } 
        
        else if(type instanceof Block)
        {
            return getItemStack(type, 1, meta);
        } 
        
        else if(type instanceof Item)
        {
            return getItemStack(type, 1, meta);
        }
        
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(Object type) 
    {
        if(type instanceof String) 
        {
            return getItemStack(Item.getByNameOrId((String) type), 0);
        } 
        
        else if(type instanceof Block) 
        {
            return getItemStack(type, 0);
        }
        
        else if(type instanceof Item) 
        {
            return getItemStack(type, 0);
        } 
        
        else if(type instanceof ItemStack) 
        {
            return (ItemStack)type;
        }
        
        return ItemStack.EMPTY;
    }
}
