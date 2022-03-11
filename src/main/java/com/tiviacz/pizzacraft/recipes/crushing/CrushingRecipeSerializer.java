package com.tiviacz.pizzacraft.recipes.crushing;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.SauceRegistry;
import com.tiviacz.pizzacraft.tileentity.BasinContent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class CrushingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe>
{
    public CrushingRecipeSerializer() {}

    public static final CrushingRecipeSerializer INSTANCE = new CrushingRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "crushing_recipe");

    @Override
    public CrushingRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        //ChoppingRecipeJSON recipeJson = new Gson().fromJson(json, ChoppingRecipeJSON.class);

        Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));

        if(input.isEmpty())
        {
            throw new JsonParseException("Missing Input in Crushing Recipe!");
        }
        else
        {
            //BasinContent basinContent = deserializeContent(JSONUtils.getString(json, "content"));
            int inputCount = JSONUtils.getAsInt(json, "inputCount");
            String basinContent = JSONUtils.getAsString(json, "content");
            ItemStack outputStack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            return new CrushingRecipe(input, inputCount, basinContent, outputStack, id);
            //ItemStack outputStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
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
    public CrushingRecipe fromNetwork(ResourceLocation id, PacketBuffer buf)
    {
        Ingredient input = Ingredient.fromNetwork(buf);
        int inputCount = buf.readInt();
        String basinContent = buf.readUtf();
        ItemStack outputStack = buf.readItem();
        //ItemStack output = buf.readItemStack();

        return new CrushingRecipe(input, inputCount, basinContent, outputStack, id);
    }

    @Override
    public void toNetwork(PacketBuffer buf, CrushingRecipe recipe)
    {
        recipe.getInput().toNetwork(buf);
        buf.writeInt(recipe.getInputCount());
        buf.writeUtf(recipe.getContentOutput().toString());
        buf.writeItem(recipe.getResultItem());
        //buf.writeItemStack(recipe.getRecipeOutput());
    }

    public static BasinContent deserializeContent(@Nullable String content)
    {
        if(content != null)
        {
            if(SauceRegistry.INSTANCE.basinContentFromString(content) != null)
            {
                return SauceRegistry.INSTANCE.basinContentFromString(content);
            }
            else
            {
                throw new JsonSyntaxException("Content does not exist!");
            }
        }
        else
        {
            throw new JsonSyntaxException("Content cannot be null");
        }
    }
    public static BasinContent deserializeContent(@Nullable JsonElement json)
    {
        if(json != null && !json.isJsonNull())
        {
            String content = json.getAsString();

            if(SauceRegistry.INSTANCE.basinContentFromString(content) != null)
            {
                return SauceRegistry.INSTANCE.basinContentFromString(content);
            }
            else
            {
                throw new JsonSyntaxException("Content does not exist!");
            }
        }
        else
        {
            throw new JsonSyntaxException("Content cannot be null");
        }
    }
}