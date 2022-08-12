package com.tiviacz.pizzacraft.recipes.mortar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MortarRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MortarRecipe>
{
    public MortarRecipeSerializer() {}

    public static final MortarRecipeSerializer INSTANCE = new MortarRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "mortar_recipe");

    @Override
    public MortarRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        int duration = GsonHelper.getAsInt(json, "duration", 20);
        NonNullList<Ingredient> nonnulllist = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));

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
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new MortarRecipe(nonnulllist, itemstack, duration, id);
        }
    }

    @Nullable
    @Override
    public MortarRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
    {
        int d = buffer.readInt();
        int i = buffer.readVarInt();
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

        for(int j = 0; j < nonnulllist.size(); ++j)
        {
            nonnulllist.set(j, Ingredient.fromNetwork(buffer));
        }

        ItemStack itemstack = buffer.readItem();
        return new MortarRecipe(nonnulllist, itemstack, d, id);
    }

    private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray)
    {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < ingredientArray.size(); ++i)
        {
            Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
            if(!ingredient.isEmpty())
            {
                nonnulllist.add(ingredient);
            }
        }
        return nonnulllist;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, MortarRecipe recipe)
    {
        buffer.writeInt(recipe.getDuration());
        buffer.writeVarInt(recipe.getInputs().size());

        for(Ingredient ingredient : recipe.getInputs())
        {
            ingredient.toNetwork(buffer);
        }

        buffer.writeItem(recipe.getResultItem());
    }
}