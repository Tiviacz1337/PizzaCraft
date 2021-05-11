package com.tiviacz.pizzacraft.recipes.chopping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;

public class ChoppingRecipeSerializer implements IRecipeSerializer<ChoppingRecipe>
{

    public ChoppingRecipeSerializer() {}

    public static final ChoppingRecipeSerializer INSTANCE = new ChoppingRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "chopping_recipe");

    @Override
    public ChoppingRecipe read(ResourceLocation id, JsonObject json)
    {
        ChoppingRecipeJSON recipeJson = new Gson().fromJson(json, ChoppingRecipeJSON.class);

        if(recipeJson.input == null || recipeJson.outputItem == null)
        {
            throw new JsonSyntaxException("Missing Attributes in Cutting Recipe!");
        }

        Ingredient input = Ingredient.deserialize(recipeJson.getInput());
        Item outputItem = Registry.ITEM.getOrDefault(new ResourceLocation(recipeJson.getOutputItemId()));

        if(outputItem == Items.AIR)
        {
            throw new JsonSyntaxException("The Item " + recipeJson.outputItem + " does not exist!");
        }

        ItemStack outputStack = new ItemStack(outputItem, recipeJson.getOutputCount());

        return new ChoppingRecipe(input, outputStack, id);
    }

    @Override
    public ChoppingRecipe read(ResourceLocation id, PacketBuffer buf)
    {
        Ingredient input = Ingredient.read(buf);
        ItemStack output = buf.readItemStack();

        return new ChoppingRecipe(input, output, id);
    }

    @Override
    public void write(PacketBuffer buf, ChoppingRecipe recipe)
    {
        recipe.getInput().write(buf);
        buf.writeItemStack(recipe.getRecipeOutput());
    }

    @Override
    public IRecipeSerializer<?> setRegistryName(ResourceLocation name)
    {
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName()
    {
        return ID;
    }

    @Override
    public Class<IRecipeSerializer<?>> getRegistryType()
    {
        return null;
    }
}