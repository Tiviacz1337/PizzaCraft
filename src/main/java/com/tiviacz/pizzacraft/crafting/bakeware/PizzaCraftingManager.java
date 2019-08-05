package com.tiviacz.pizzacraft.crafting.bakeware;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public abstract class PizzaCraftingManager 
{
    public static final PizzaCraftingManager PIZZA_CRAFTING = new PizzaCraftingManager(3, "PizzaCrafting") 
    {
        @Override
        public void setRecipes() 
        {
            new PizzaRecipes().addRecipes(this);
        }
    };

    public static PizzaCraftingManager getPizzaCraftingInstance() 
    {
        return PIZZA_CRAFTING;
    }

    private final List<IBakewareRecipe> recipes = Lists.newArrayList();
    private int xy;
    private String name;

    public PizzaCraftingManager(int xy, String name) 
    {
        this.xy = xy;
        this.name = name;
        this.recipes.sort((pCompare1, pCompare2) -> Integer.compare(pCompare2.getRecipeSize(), pCompare1.getRecipeSize()));
        this.setRecipes();
    }

    public abstract void setRecipes();

    public String getName() 
    {
        return name;
    }

    public void addShapelessRecipe(ItemStack stack, Object... recipeComponents) 
    {
        NonNullList<ItemStack> list = NonNullList.create();
        CraftingUtils.addShapelessRecipe(list, recipeComponents);
        this.recipes.add(new BaseShapelessRecipe(stack, list));
    }

    public void addRecipe(IBakewareRecipe recipe) 
    {
        this.recipes.add(recipe);
    }

    public void addRecipes(IBakewareRecipe... recipes) 
    {
        Arrays.stream(recipes).forEach(this::addRecipe);
    }

    public void removeRecipe(IBakewareRecipe recipe) 
    {
        this.recipes.remove(recipe);
    }

    public ItemStack findMatchingRecipe(InventoryCraftingImproved craftMatrix, World worldIn) 
    {
        return CraftingUtils.findMatchingRecipe(recipes, craftMatrix, worldIn);
    } 

    public NonNullList<ItemStack> getRemainingItems(InventoryCraftingImproved craftMatrix, World worldIn) 
    {
        return CraftingUtils.getRemainingItems(recipes, craftMatrix, worldIn);
    }

    public List<IBakewareRecipe> getRecipeList() 
    {
        return this.recipes;
    }
}