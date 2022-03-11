package com.tiviacz.pizzacraft.compat.jei;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class PizzaCraftPlugin implements IModPlugin
{
    private static List<IRecipe<?>> findRecipes(IRecipeSerializer<?> serializer)
    {
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(r -> r.getSerializer() == serializer).collect(Collectors.toList());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new ChoppingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        //registration.addRecipeCategories(new PizzaIngredientsCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        for(Block choppingBoard : ModBlocks.getChoppingBoards())
        {
            registration.addRecipeCatalyst(new ItemStack(choppingBoard), ChoppingRecipeCategory.ID);
        }

        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MORTAR_AND_PESTLE.get()), MortarRecipeCategory.ID);

        for(Block basin : ModBlocks.getBasins())
        {
            registration.addRecipeCatalyst(new ItemStack(basin), CrushingRecipeCategory.ID);
        }

     //   registration.addRecipeCatalyst(new ItemStack(ModBlocks.RAW_PIZZA.get()), PizzaIngredientsCategory.ID);
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(PizzaCraft.MODID, "pizzacraft");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        registration.addRecipes(findRecipes(ModRecipes.CHOPPING_RECIPE_SERIALIZER.get()), ChoppingRecipeCategory.ID);
        registration.addRecipes(findRecipes(ModRecipes.MORTAR_RECIPE_SERIALIZER.get()), MortarRecipeCategory.ID);
        registration.addRecipes(findRecipes(ModRecipes.CRUSHING_RECIPE_SERIALIZER.get()), CrushingRecipeCategory.ID);
        //registration.addRecipes(CrushingRecipeCategory.getRecipes(), CrushingRecipeCategory.ID);
       // registration.addRecipes(PizzaIngredientsCategory.getIngredients(), PizzaIngredientsCategory.ID);
    }
}
