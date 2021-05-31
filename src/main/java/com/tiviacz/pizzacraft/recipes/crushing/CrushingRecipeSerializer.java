package com.tiviacz.pizzacraft.recipes.crushing;

import com.google.gson.*;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.SauceRegistry;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CrushingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe>
{
    public CrushingRecipeSerializer() {}

    public static final CrushingRecipeSerializer INSTANCE = new CrushingRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "crushing_recipe");

    @Override
    public CrushingRecipe read(ResourceLocation id, JsonObject json)
    {
        //ChoppingRecipeJSON recipeJson = new Gson().fromJson(json, ChoppingRecipeJSON.class);

        Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));

        if(input.hasNoMatchingItems())
        {
            throw new JsonParseException("Missing Input in Crushing Recipe!");
        }
        else
        {
            //BasinContent basinContent = deserializeContent(JSONUtils.getString(json, "content"));
            int inputCount = JSONUtils.getInt(json, "inputCount");
            String basinContent = JSONUtils.getString(json, "content");
            ItemStack outputStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
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
    public CrushingRecipe read(ResourceLocation id, PacketBuffer buf)
    {
        Ingredient input = Ingredient.read(buf);
        int inputCount = buf.readInt();
        String basinContent = buf.readString();
        ItemStack outputStack = buf.readItemStack();
        //ItemStack output = buf.readItemStack();

        return new CrushingRecipe(input, inputCount, basinContent, outputStack, id);
    }

    @Override
    public void write(PacketBuffer buf, CrushingRecipe recipe)
    {
        recipe.getInput().write(buf);
        buf.writeInt(recipe.getInputCount());
        buf.writeString(recipe.getContentOutput().toString());
        buf.writeItemStack(recipe.getRecipeOutput());
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