package com.tiviacz.pizzacraft.crafting.bakeware;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class BaseShapelessRecipe implements IBakewareRecipe 
{
    public final NonNullList<ItemStack> input;
    private final ItemStack recipeOutput;

    public BaseShapelessRecipe(ItemStack output, NonNullList<ItemStack> inputList) 
    {
        this.recipeOutput = output;
        this.input = inputList;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() 
    {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(@Nonnull InventoryCraftingImproved inv, @Nonnull World worldIn) 
    {
        return ShapelessRecipeUtils.matches(input, inv);
    }
    
    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCraftingImproved inv) 
    {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() 
    {
        return this.input.size();
    }

    public NonNullList<ItemStack> getInput() 
    {
        return input;
    }
}