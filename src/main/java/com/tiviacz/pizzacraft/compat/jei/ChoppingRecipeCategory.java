package com.tiviacz.pizzacraft.compat.jei;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class ChoppingRecipeCategory implements IRecipeCategory<ChoppingRecipe>
{
    public static final RecipeType<ChoppingRecipe> CHOPPING =
            RecipeType.create(PizzaCraft.MODID, "chopping", ChoppingRecipe.class);

    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "chopping");

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public ChoppingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/chopping_recipe.png"), -14, -5, 95, 31);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.OAK_CHOPPING_BOARD.get()));
        title = new TranslatableComponent("recipecategory." + PizzaCraft.MODID + ".chopping");
    }

    @Override
    public RecipeType<ChoppingRecipe> getRecipeType()
    {
        return CHOPPING;
    }

    @Override
    public Component getTitle()
    {
        return title;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChoppingRecipe choppingRecipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 7).addItemStacks(Arrays.asList(choppingRecipe.getInput().getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 7).addItemStack(choppingRecipe.output);

        //iIngredients.setInputIngredients(ingredients);
        //iIngredients.setOutput(VanillaTypes.ITEM, choppingRecipe.getResultItem());
    }

    @Override
    public ResourceLocation getUid()
    {
        return ID;
    }

    @Override
    public Class<? extends ChoppingRecipe> getRecipeClass()
    {
        return ChoppingRecipe.class;
    }
}