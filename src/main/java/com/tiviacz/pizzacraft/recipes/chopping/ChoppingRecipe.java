package com.tiviacz.pizzacraft.recipes.chopping;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ChoppingRecipe implements IRecipe<RecipeWrapper>
{
    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;

    public ChoppingRecipe(Ingredient input, ItemStack output, ResourceLocation id)
    {
        this.input = input;
        this.output = output;
        this.id = id;
    }

    public Ingredient getInput()
    {
        return this.input;
    }

    @Override
    public ItemStack getResultItem()
    {
        return this.output;
    }

    @Override
    public boolean matches(RecipeWrapper recipeWrapper, World world)
    {
        return input.test(recipeWrapper.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper recipeWrapper)
    {
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return false;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return ChoppingRecipeSerializer.INSTANCE;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return Type.CHOPPING_BOARD_RECIPE_TYPE;
    }

    @Override
    public String getGroup()
    {
        return "";
    }

    public static class Type implements IRecipeType<ChoppingRecipe>
    {
        private Type() {}
        public static final Type CHOPPING_BOARD_RECIPE_TYPE = new Type();

        public static final String NAME = "chopping_board_recipe";
    }
}
