package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.Map;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BaseMortarShapelessOreRecipe implements IMortarRecipe
{
    protected ItemStack output;
    protected NonNullList<Object> input = NonNullList.create();
    protected int duration;

    public BaseMortarShapelessOreRecipe(Block result, int duration, Object... recipe) 
    {
        this(new ItemStack(result), duration, recipe);
    }

    public BaseMortarShapelessOreRecipe(Item result, int duration, Object... recipe) 
    {
        this(new ItemStack(result), duration, recipe);
    }

    public BaseMortarShapelessOreRecipe(ItemStack result, int duration, Object... recipe) 
    {
    	this.duration = duration;
        this.output = result.copy();
        ShapelessMortarOreRecipeUtils.createRecipe(output, input, recipe);
    }

    public BaseMortarShapelessOreRecipe(BaseMortarShapelessRecipe recipe, Map<ItemStack, String> replacements, int duration) 
    {
    	this.duration = duration;
        this.output = recipe.getRecipeOutput();

        recipe.input.stream().map(
            ingredient -> replacements.entrySet().stream().filter(
                replace -> OreDictionary.itemMatches(replace.getKey(), ingredient, false)
            ).findFirst().<Object>map(
                replace -> OreDictionary.getOres(replace.getValue())
            ).orElse(ingredient)
        ).forEachOrdered(finalObj -> input.add(finalObj));
    }

    @Override
    public int getRecipeSize()
    {
        return this.input.size();
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() 
    {
        return this.output;
    }

    @Override
    @Nonnull
    public ItemStack getResult() 
    {
        return this.output.copy();
    }

    @Override
    public boolean matches(@Nonnull TileEntityMortarAndPestle tile, @Nonnull World world) 
    {
        return ShapelessMortarOreRecipeUtils.matches(input, tile);
    }
    
    public NonNullList<Object> getInput() 
    {
        return this.input;
    }

	@Override
	public int getDuration() 
	{
		return this.duration;
	}
}