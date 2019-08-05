package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.List;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BaseMortarShapelessRecipe implements IMortarRecipe 
{
    public final List<ItemStack> input;
    private final ItemStack recipeOutput;
    public final int duration;

    public BaseMortarShapelessRecipe(ItemStack output, List<ItemStack> inputList, int duration) 
    {
        this.recipeOutput = output;
        this.input = inputList;
        this.duration = duration;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() 
    {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(@Nonnull TileEntityMortarAndPestle tile, @Nonnull World worldIn) 
    {
        return ShapelessMortarRecipeUtils.matches(this.input, tile);
    }
    
    @Override
    @Nonnull
    public ItemStack getResult() 
    {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() 
    {
        return this.input.size();
    }

    public List<ItemStack> getInput() 
    {
        return this.input;
    }

	@Override
	public int getDuration() 
	{
		return this.duration;
	}
}
