package com.tiviacz.pizzacraft.recipes.mortar;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class MortarRecipe implements Recipe<Container>
{
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int duration;

    public MortarRecipe(NonNullList<Ingredient> inputs, ItemStack output, int duration, ResourceLocation id)
    {
        this.inputs = inputs;
        this.output = output;
        this.id = id;
        this.duration = duration;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public NonNullList<Ingredient> getInputs()
    {
        return this.inputs;
    }

    @Override
    public ItemStack getResultItem()
    {
        return this.output;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for(int j = 0; j < container.getContainerSize(); ++j)
        {
            ItemStack itemstack = container.getItem(j);
            if(!itemstack.isEmpty())
            {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputs.size() && RecipeMatcher.findMatches(inputs, this.inputs) != null;
    }

    @Override
    public ItemStack assemble(Container container)
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
    public RecipeSerializer<?> getSerializer()
    {
        return MortarRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE;
    }

    public static class Type implements RecipeType<MortarRecipe>
    {
        private Type() {}
        public static final MortarRecipe.Type MORTAR_AND_PESTLE_RECIPE_TYPE = new MortarRecipe.Type();

        public static final String ID = "mortar_recipe";
    }
}