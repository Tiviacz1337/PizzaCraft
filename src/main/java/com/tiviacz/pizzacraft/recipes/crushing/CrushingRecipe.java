package com.tiviacz.pizzacraft.recipes.crushing;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CrushingRecipe implements Recipe<RecipeWrapper>
{
    private final Ingredient input;
    private final int inputCount;
    private final String contentOutput;
    public final ItemStack stackOutput;
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
    public boolean matches(RecipeWrapper inv, Level level)
    {
        return input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv)
    {
        return this.stackOutput;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
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
    public ItemStack getResultItem()
    {
        return this.stackOutput;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return CrushingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.CRUSHING_RECIPE_TYPE;
    }

    public static class Type implements RecipeType<CrushingRecipe>
    {
        private Type() {}
        public static final CrushingRecipe.Type CRUSHING_RECIPE_TYPE = new CrushingRecipe.Type();

        public static final String ID = "crushing_recipe";
    }
}