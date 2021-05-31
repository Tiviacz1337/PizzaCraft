package com.tiviacz.pizzacraft.recipes.crushing;

import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.tileentity.BasinContent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CrushingRecipe implements IRecipe<RecipeWrapper>
{
    private final Ingredient input;
    private final int inputCount;
    private final String contentOutput;
    private final ItemStack stackOutput;
    private final ResourceLocation id;

    public CrushingRecipe(Ingredient input, int inputCount, String contentOutput, ItemStack stackOutput, ResourceLocation id)
    {
        this.input = input;
        this.inputCount = inputCount;
        this.contentOutput = contentOutput;
        this.stackOutput = stackOutput;
        this.id = id;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn)
    {
        return input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv)
    {
        return getRecipeOutput();
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return false;
    }

    public int getInputCount()
    {
        return this.inputCount;
    }

    public Ingredient getInput()
    {
        return this.input;
    }

    public String getContentOutput()
    {
        return this.contentOutput;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return this.stackOutput;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return CrushingRecipeSerializer.INSTANCE;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return Type.CRUSHING_RECIPE_TYPE;
    }

    public static class Type implements IRecipeType<CrushingRecipe>
    {
        private Type() {}
        public static final CrushingRecipe.Type CRUSHING_RECIPE_TYPE = new CrushingRecipe.Type();

        public static final String NAME = "crushing_recipe";
    }
}