package com.tiviacz.pizzacraft.recipes.chopping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ChoppingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ChoppingRecipe>
{
    public ChoppingRecipeSerializer() {}

    public static final ChoppingRecipeSerializer INSTANCE = new ChoppingRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "chopping_recipe");

    @Override
    public ChoppingRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        //ChoppingRecipeJSON recipeJson = new Gson().fromJson(json, ChoppingRecipeJSON.class);

        Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));

        if(input.isEmpty())
        {
            throw new JsonParseException("Missing Input in Chopping Recipe!");
        }
        else
        {
            ItemStack outputStack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
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
    public ChoppingRecipe fromNetwork(ResourceLocation id, PacketBuffer buf)
    {
        Ingredient input = Ingredient.fromNetwork(buf);
        ItemStack output = buf.readItem();

        return new ChoppingRecipe(input, output, id);
    }

    @Override
    public void toNetwork(PacketBuffer buf, ChoppingRecipe recipe)
    {
        recipe.getInput().toNetwork(buf);
        buf.writeItem(recipe.getResultItem());
    }
}