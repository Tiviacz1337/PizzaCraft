package com.tiviacz.pizzacraft.crafting.bakeware;

import java.util.Map;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BaseShapelessOreRecipe implements IBakewareRecipe 
{
    protected ItemStack output;
    protected NonNullList<Object> input = NonNullList.create();

    public BaseShapelessOreRecipe(Block result, Object... recipe) 
    {
        this(new ItemStack(result), recipe);
    }

    public BaseShapelessOreRecipe(Item result, Object... recipe) 
    {
        this(new ItemStack(result), recipe);
    }

    public BaseShapelessOreRecipe(ItemStack result, Object... recipe) 
    {
        output = result.copy();
        ShapelessOreRecipeUtils.createRecipe(output, input, recipe);
    }

    BaseShapelessOreRecipe(BaseShapelessRecipe recipe, Map<ItemStack, String> replacements) 
    {
        output = recipe.getRecipeOutput();

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
        return input.size();
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() 
    {
        return output;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCraftingImproved var1) 
    {
        return output.copy();
    }

    @Override
    public boolean matches(@Nonnull InventoryCraftingImproved inv, @Nonnull World world) 
    {
        return ShapelessOreRecipeUtils.matches(input, inv);
    }
    
    public NonNullList<Object> getInput() 
    {
        return this.input;
    }
}