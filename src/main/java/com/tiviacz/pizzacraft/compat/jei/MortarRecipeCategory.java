package com.tiviacz.pizzacraft.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe>
{
    public static final RecipeType<MortarRecipe> MORTAR =
            RecipeType.create(PizzaCraft.MODID, "mortar", MortarRecipe.class);

    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "mortar");

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public MortarRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/mortar_recipe.png"), -9, -9, 128, 96);
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.MORTAR_AND_PESTLE.get()));
        title = Component.translatable("recipecategory." + PizzaCraft.MODID + ".mortar");
    }

    @Override
    public RecipeType<MortarRecipe> getRecipeType()
    {
        return MORTAR;
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
    public void setRecipe(IRecipeLayoutBuilder builder, MortarRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 40).addItemStack(recipe.getResultItem());

        for(int i = 0; i < recipe.getInputs().size(); i++)
        {
            builder.addSlot(RecipeIngredientRole.INPUT, 10, 70 - ((18 * i) + (i * 2))).addIngredients(recipe.getInputs().get(i));
        }
    }

    @Override
    public void draw(MortarRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY)
    {
        Font font = Minecraft.getInstance().font;
        String duration = recipe.getDuration() + "x";
        font.draw(poseStack, duration, 44 - font.width(duration) / 2F, 27, 0xff404040);
    }
}
