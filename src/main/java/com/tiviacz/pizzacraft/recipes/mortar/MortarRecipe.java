package com.tiviacz.pizzacraft.recipes.mortar;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class MortarRecipe implements IRecipe<IInventory>
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
    public boolean matches(IInventory inv, World worldIn)
    {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for(int j = 0; j < inv.getContainerSize(); ++j)
        {
            ItemStack itemstack = inv.getItem(j);
            if(!itemstack.isEmpty())
            {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputs.size() && RecipeMatcher.findMatches(inputs, this.inputs) != null;
    }

    @Override
    public ItemStack assemble(IInventory inv)
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
        return MortarRecipeSerializer.INSTANCE;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE;
    }

    public static class Type implements IRecipeType<MortarRecipe>
    {
        private Type() {}
        public static final MortarRecipe.Type MORTAR_AND_PESTLE_RECIPE_TYPE = new MortarRecipe.Type();

        public static final String ID = "mortar_recipe";
    }
}