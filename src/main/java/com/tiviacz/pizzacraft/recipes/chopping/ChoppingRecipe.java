package com.tiviacz.pizzacraft.recipes.chopping;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ChoppingRecipe implements Recipe<RecipeWrapper>
{
    private final Ingredient input;
    public final ItemStack output;
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
    public ItemStack getResultItem(RegistryAccess access)
    {
        return this.output;
    }

    @Override
    public boolean matches(RecipeWrapper recipeWrapper, Level level)
    {
        return input.test(recipeWrapper.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper recipeWrapper, RegistryAccess access)
    {
        return this.output;
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
    public RecipeSerializer<?> getSerializer()
    {
        return ChoppingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.CHOPPING_BOARD_RECIPE_TYPE;
    }

    @Override
    public String getGroup()
    {
        return "";
    }

    public static class Type implements RecipeType<ChoppingRecipe>
    {
        private Type() {}
        public static final Type CHOPPING_BOARD_RECIPE_TYPE = new Type();

        public static final String ID = "chopping_board_recipe";
    }
}
