package com.tiviacz.pizzacraft.compat.jei.chopping;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.util.TextUtils;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class ChoppingBoardRecipeCategory implements IRecipeCategory
{
	private static final ResourceLocation TEXTURES = TextUtils.setResourceLocation("textures/gui/jei/chopping_board.png");
	private static final int OUTPUT_SLOT = 0;
	private static final int INPUT_SLOT = 1;
	private final IDrawable background;
	private final String localizedName;
	private final String category;
	
	public ChoppingBoardRecipeCategory(IGuiHelper helper, String name, String category) 
	{
		this.category = category;
		this.background = helper.createDrawable(TEXTURES, 0, 0, 116, 54);
		this.localizedName = new TextComponentTranslation("gui.jei.category." + name).getFormattedText();
	}
	
	@Override
	public IDrawable getBackground()
	{
		return this.background;
	}
	
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
	
	@Override
	public String getUid()
	{
		return this.category;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) 
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT_SLOT, true, 24, 18);
		stacks.init(OUTPUT_SLOT, false, 75, 18);
		stacks.set(ingredients);
	}
}