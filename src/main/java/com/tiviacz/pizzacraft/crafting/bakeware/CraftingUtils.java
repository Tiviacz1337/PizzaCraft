package com.tiviacz.pizzacraft.crafting.bakeware;

import java.util.Arrays;
import java.util.List;

import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class CraftingUtils 
{
    public static void addShapelessRecipe(NonNullList<ItemStack> list, Object... recipeComponents) 
    {
        Arrays.stream(recipeComponents).forEachOrdered(object -> 
        {
            if(object instanceof ItemStack) 
            {
                list.add(((ItemStack) object).copy());
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

    public static ItemStack findMatchingRecipe(List<IBakewareRecipe> recipes, InventoryCraftingImproved craftMatrix, World worldIn) 
    {
        return recipes.stream().filter(irecipe -> irecipe.matches(craftMatrix, worldIn)).findFirst().map(irecipe -> irecipe.getCraftingResult(craftMatrix)).orElse(ItemStack.EMPTY);
    }

    public static NonNullList<ItemStack> getRemainingItems(List<IBakewareRecipe> recipes, InventoryCraftingImproved craftMatrix, World worldIn) 
    {
        for(IBakewareRecipe recipe : recipes) 
        {
            if(recipe.matches(craftMatrix, worldIn)) 
            {
                return recipe.getRemainingItems(craftMatrix);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) 
        {
            nonnulllist.set(i, craftMatrix.getStackInSlot(i));
        }
        return nonnulllist;
    }

    public static void onTake(EntityPlayer player, InventoryCraftingImproved craftMatrix, NonNullList<ItemStack> input) 
    {
        for(int i = 0; i < input.size(); ++i) 
        {
            ItemStack slotStack = craftMatrix.getStackInSlot(i);
            ItemStack inputStack = input.get(i);

            if(!slotStack.isEmpty()) 
            {
                craftMatrix.decrStackSize(i, 1);
                slotStack = craftMatrix.getStackInSlot(i);
            }

            if(!inputStack.isEmpty()) 
            {
                if(slotStack.isEmpty()) 
                {
                    craftMatrix.setInventorySlotContents(i, inputStack);
                } 
                else if(ItemStack.areItemsEqual(slotStack, inputStack) && ItemStack.areItemStackTagsEqual(slotStack, inputStack)) 
                {
                    inputStack.grow(slotStack.getCount());
                    craftMatrix.setInventorySlotContents(i, inputStack);
                } 
                else if(!player.inventory.addItemStackToInventory(inputStack)) 
                {
                    player.dropItem(inputStack, false);
                }
            }
        }
    }
}