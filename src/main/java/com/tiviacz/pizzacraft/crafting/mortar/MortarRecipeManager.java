package com.tiviacz.pizzacraft.crafting.mortar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class MortarRecipeManager 
{
    public static final MortarRecipeManager MORTAR_MANAGER = new MortarRecipeManager(2, "MortarManager") 
    {
        @Override
        public void setRecipes() 
        {
            new MortarRecipes().addRecipes(this);
        }
    };

    public static MortarRecipeManager getMortarManagerInstance() 
    {
        return MORTAR_MANAGER;
    }

    private final List<IMortarRecipe> recipes = new ArrayList<IMortarRecipe>();
    private int xy;
    private String name;

    public MortarRecipeManager(int xy, String name) 
    {
        this.xy = xy;
        this.name = name;
        this.recipes.sort((pCompare1, pCompare2) -> Integer.compare(pCompare2.getRecipeSize(), pCompare1.getRecipeSize()));
        this.setRecipes();
    }

    public abstract void setRecipes();

    public String getName() 
    {
        return this.name;
    }

    public void addShapelessRecipe(ItemStack stack, int duration, Object... recipeComponents) 
    {
        List<ItemStack> list = new ArrayList<ItemStack>();
        MortarRecipeUtils.addShapelessRecipe(list, recipeComponents);
        this.recipes.add(new BaseMortarShapelessRecipe(stack, list, duration));
    }

    public void addRecipe(IMortarRecipe recipe) 
    {
        this.recipes.add(recipe);
    }

    public void addRecipes(IMortarRecipe... recipes) 
    {
        Arrays.stream(recipes).forEach(this::addRecipe);
    }

    public void removeRecipe(IMortarRecipe recipe) 
    {
        this.recipes.remove(recipe);
    }

    public ItemStack findMatchingRecipe(TileEntityMortarAndPestle tile, World worldIn) 
    {
        return MortarRecipeUtils.findMatchingRecipe(recipes, tile, worldIn);
    }
    
    public int getRecipeDuration(TileEntityMortarAndPestle tile, World worldIn)
    {
    	return MortarRecipeUtils.getRecipeDuration(recipes, tile, worldIn);
    }

    public List<ItemStack> getRemainingItems(TileEntityMortarAndPestle tile, World worldIn) 
    {
        return MortarRecipeUtils.getRemainingItems(recipes, tile, worldIn);
    }

    public List<IMortarRecipe> getRecipeList() 
    {
        return this.recipes;
    }
}