package com.tiviacz.pizzacraft.recipes.mortar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class MortarRecipeSerializer implements IRecipeSerializer<MortarRecipe>
{
    public MortarRecipeSerializer() {}

    public static final MortarRecipeSerializer INSTANCE = new MortarRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "mortar_recipe");

    @Override
    public MortarRecipe read(ResourceLocation id, JsonObject json)
    {
        int duration = JSONUtils.getInt(json, "duration", 20);
        NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));

        if(nonnulllist.isEmpty())
        {
            throw new JsonParseException("No ingredients for shapeless recipe");
        }
        else if(nonnulllist.size() > 4)
        {
            throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + 4);
        }
        else
        {
            ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new MortarRecipe(nonnulllist, itemstack, duration, id);
        }
    }

    @Nullable
    @Override
    public MortarRecipe read(ResourceLocation id, PacketBuffer buffer)
    {
        int d = buffer.readInt();
        int i = buffer.readVarInt();
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

        for(int j = 0; j < nonnulllist.size(); ++j)
        {
            nonnulllist.set(j, Ingredient.read(buffer));
        }

        ItemStack itemstack = buffer.readItemStack();
        return new MortarRecipe(nonnulllist, itemstack, d, id);
    }

    private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray)
    {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < ingredientArray.size(); ++i)
        {
            Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
            if(!ingredient.hasNoMatchingItems())
            {
                nonnulllist.add(ingredient);
            }
        }
        return nonnulllist;
    }

    @Override
    public void write(PacketBuffer buffer, MortarRecipe recipe)
    {
        buffer.writeInt(recipe.getDuration());
        buffer.writeVarInt(recipe.getInputs().size());

        for(Ingredient ingredient : recipe.getInputs())
        {
            ingredient.write(buffer);
        }

        buffer.writeItemStack(recipe.getRecipeOutput());
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