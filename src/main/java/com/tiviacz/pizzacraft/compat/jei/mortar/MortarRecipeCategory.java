package com.tiviacz.pizzacraft.compat.jei.mortar;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.compat.jei.JEIUtils;
import com.tiviacz.pizzacraft.compat.jei.XYProperties;
import com.tiviacz.pizzacraft.util.TextUtils;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class MortarRecipeCategory implements IRecipeCategory 
{
	private static final ResourceLocation BACKGROUND = TextUtils.setResourceLocation("textures/gui/jei/mortar_and_pestle.png");
    private static final int OUTPUT_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private final IDrawable background;
    private final String localizedName;
    private final String category;

    public MortarRecipeCategory(IGuiHelper guiHelper, String name, String category) 
    {
        this.category = category;
        this.background = guiHelper.createDrawable(BACKGROUND, 0, 0, 116, 54);
        this.localizedName = new TextComponentTranslation("gui.jei.category." + name).getFormattedText();
    }

    @Nonnull
    @Override
    public String getUid() 
    {
        return this.category;
    } 

    @Nonnull
    @Override
    public String getTitle() 
    {
        return this.localizedName;
    }

    @Override
    public String getModName() 
    {
        return PizzaCraft.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() 
    {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) 
    {
        JEIUtils.setMortarRecipe(recipeLayout, recipeWrapper, ingredients, new XYProperties(94, 18, 2), INPUT_SLOT, OUTPUT_SLOT);
    }
}
