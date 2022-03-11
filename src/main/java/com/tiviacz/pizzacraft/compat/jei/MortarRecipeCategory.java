package com.tiviacz.pizzacraft.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe>
{
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "mortar");

    private final IDrawable background;
    private final IDrawable icon;
    private final String title;

    public MortarRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/mortar_recipe.png"), -9, -9, 128, 96);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.MORTAR_AND_PESTLE.get()));
        title = I18n.get("recipecategory." + PizzaCraft.MODID + ".mortar");
    }

    @Override
    public ResourceLocation getUid()
    {
        return ID;
    }

    @Override
    public Class<? extends MortarRecipe> getRecipeClass()
    {
        return MortarRecipe.class;
    }

    @Override
    public String getTitle()
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
    public void setIngredients(MortarRecipe mortarRecipe, IIngredients iIngredients)
    {
        List<Ingredient> ingredients = mortarRecipe.getInputs();
        iIngredients.setInputIngredients(ingredients);
        iIngredients.setOutput(VanillaTypes.ITEM, mortarRecipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MortarRecipe mortarRecipe, IIngredients iIngredients)
    {
        IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();

        stacks.init(4, true, 97, 39);
        stacks.set(4, mortarRecipe.getResultItem());

        for(int i = 0; i < mortarRecipe.getInputs().size(); i++)
        {
            stacks.init(i, true, 9, 69 - ((18 * i) + (i * 2)));
            stacks.set(i, Arrays.asList(mortarRecipe.getInputs().get(i).getItems()));
        }
    }

    @Override
    public void draw(MortarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        String duration = recipe.getDuration() + "x";
        fontRenderer.draw(matrixStack, duration, 44 - fontRenderer.width(duration) / 2F, 27, 0xff404040);
    }
}
