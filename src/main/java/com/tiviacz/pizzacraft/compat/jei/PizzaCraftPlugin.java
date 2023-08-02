package com.tiviacz.pizzacraft.compat.jei;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModRecipes;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.plugins.vanilla.crafting.CategoryRecipeValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@JeiPlugin
public class PizzaCraftPlugin implements IModPlugin
{
    @Nullable
    private IRecipeCategory<ChoppingRecipe> choppingCategory;

    @Nullable
    private IRecipeCategory<CrushingRecipe> crushingCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(choppingCategory = new ChoppingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(crushingCategory = new CrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        for(Block choppingBoard : ModBlocks.getChoppingBoards())
        {
            registration.addRecipeCatalyst(new ItemStack(choppingBoard), ChoppingRecipeCategory.CHOPPING);
        }

        for(Block basin : ModBlocks.getBasins())
        {
            registration.addRecipeCatalyst(new ItemStack(basin), CrushingRecipeCategory.CRUSHING);
        }
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(PizzaCraft.MODID, "pizzacraft");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        IIngredientManager ingredientManager = registration.getIngredientManager();

        registration.addRecipes(ChoppingRecipeCategory.CHOPPING, getChoppingRecipes(choppingCategory, ingredientManager));
        registration.addRecipes(CrushingRecipeCategory.CRUSHING, getCrushing(crushingCategory, ingredientManager));
    }

    public List<ChoppingRecipe> getChoppingRecipes(IRecipeCategory<ChoppingRecipe> chopping, IIngredientManager ingredientManager) {
        CategoryRecipeValidator<ChoppingRecipe> validator = new CategoryRecipeValidator<>(chopping, 1);
        return getValidHandledRecipes(Minecraft.getInstance().level.getRecipeManager(), ModRecipes.CHOPPING_RECIPE_TYPE.get(), validator);
    }

    public List<CrushingRecipe> getCrushing(IRecipeCategory<CrushingRecipe> crushing, IIngredientManager ingredientManager) {
        CategoryRecipeValidator<CrushingRecipe> validator = new CategoryRecipeValidator<>(crushing, 1);
        return getValidHandledRecipes(Minecraft.getInstance().level.getRecipeManager(), ModRecipes.CRUSHING_RECIPE_TYPE.get(), validator);
    }

    private static <C extends Container, T extends Recipe<C>> List<T> getValidHandledRecipes(
            RecipeManager recipeManager,
            RecipeType<T> recipeType,
            CategoryRecipeValidator<T> validator
    ) {
        return recipeManager.getAllRecipesFor(recipeType)
                .stream()
                .filter(validator::isRecipeHandled)  /*validator.isRecipeValid(r) &&  */
                .toList();
    }
}
