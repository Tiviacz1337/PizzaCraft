package com.tiviacz.pizzacraft.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe>
{
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "mortar");

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public MortarRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/mortar_recipe.png"), -9, -9, 128, 96);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.MORTAR_AND_PESTLE.get()));
        title = new TranslatableComponent("recipecategory." + PizzaCraft.MODID + ".mortar");
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
    public void draw(MortarRecipe recipe, PoseStack poseStack, double mouseX, double mouseY)
    {
        Font font = Minecraft.getInstance().font;
        String duration = recipe.getDuration() + "x";
        font.draw(poseStack, duration, 44 - font.width(duration) / 2F, 27, 0xff404040);
    }
}
