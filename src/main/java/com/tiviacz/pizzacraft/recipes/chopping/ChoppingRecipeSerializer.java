package com.tiviacz.pizzacraft.recipes.chopping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ChoppingRecipeSerializer implements RecipeSerializer<ChoppingRecipe>
{
    public ChoppingRecipeSerializer() {}

    public static final ChoppingRecipeSerializer INSTANCE = new ChoppingRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "chopping_recipe");

    @Override
    public ChoppingRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        //ChoppingRecipeJSON recipeJson = new Gson().fromJson(json, ChoppingRecipeJSON.class);

        Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));

        if(input.isEmpty())
        {
            throw new JsonParseException("Missing Input in Chopping Recipe!");
        }
        else
        {
            ItemStack outputStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new ChoppingRecipe(input, outputStack, id);
        }
        //if(recipeJson.input == null || recipeJson.outputItem == null)
        //{
        //    throw new JsonSyntaxException("Missing Attributes in Cutting Recipe!");
        //}

        //Ingredient input = Ingredient.deserialize(recipeJson.getInput());
/*        Item outputItem = Registry.ITEM.getOrDefault(new ResourceLocation(recipeJson.getOutputItemId()));

        if(outputItem == Items.AIR)
        {
            throw new JsonSyntaxException("The Item " + recipeJson.outputItem + " does not exist!");
        }

        ItemStack outputStack = new ItemStack(outputItem, recipeJson.getOutputCount());

        return new ChoppingRecipe(input, outputStack, id); */
    }

    @Override
    public ChoppingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
    {
        Ingredient input = Ingredient.fromNetwork(buf);
        ItemStack output = buf.readItem();

        return new ChoppingRecipe(input, output, id);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ChoppingRecipe recipe)
    {
        recipe.getInput().toNetwork(buf);
        buf.writeItem(recipe.output);
    }
}