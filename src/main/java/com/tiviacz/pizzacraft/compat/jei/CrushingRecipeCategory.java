package com.tiviacz.pizzacraft.compat.jei;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrushingRecipeCategory implements IRecipeCategory<CrushingRecipe>
{
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "crushing");

    private final IDrawable background;
    private final IDrawable icon;
    private final String title;
    //private final ITickTimer timer;

    public CrushingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/crushing_recipe.png"), -5, -5, 96, 36);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.GRANITE_BASIN.get()));
        title = I18n.get("recipecategory." + PizzaCraft.MODID + ".crushing");
        //timer = guiHelper.createTickTimer(60, 320, false);
    }

    @Override
    public ResourceLocation getUid()
    {
        return ID;
    }

    @Override
    public Class<? extends CrushingRecipe> getRecipeClass()
    {
        return CrushingRecipe.class;
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

  /*  @Nonnull
    public static List<CrushingRecipeType> getRecipes()
    {
        List<CrushingRecipeType> recipes = new ArrayList<>();
        recipes.add(new CrushingRecipeType(new ItemStack(ModItems.TOMATO.get(), 5), 5, new ItemStack(ModItems.TOMATO_SAUCE.get())));
        return recipes;
    } */

    @Override
    public void setIngredients(CrushingRecipe crushingRecipe, IIngredients iIngredients)
    {
        List<Ingredient> ingredients = Collections.singletonList(crushingRecipe.getInput());
        iIngredients.setInputIngredients(ingredients);
        iIngredients.setOutput(VanillaTypes.ITEM, crushingRecipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, CrushingRecipe crushingRecipe, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();

        guiItemStacks.init(0, false, 5, 9);
        guiItemStacks.init(1, false, 68, 9);

        List<ItemStack> inputs = Arrays.asList(crushingRecipe.getInput().getItems());
        inputs.forEach(s -> s.setCount(crushingRecipe.getInputCount()));

        guiItemStacks.set(0, inputs);
        guiItemStacks.set(1, crushingRecipe.getResultItem());
    }

  //  @Override
  //  public void draw(CrushingRecipeType recipe, MatrixStack matrixStack, double mouseX, double mouseY)
  //  {
      //  IRenderTypeBuffer.Impl buffer = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
      //  ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
      //  BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

       // int value = timer.getValue();

        //Basin
       // matrixStack.push();
        //matrixStack.translate(52, 40, 128 + -32);
        //matrixStack.scale(32, 32, 32);
        //matrixStack.rotate(new Quaternion(30, 45, 0, true));
        //itemRenderer.renderItem(ModBlocks.ANDESITE_BASIN.get().asItem().getDefaultInstance(), ItemCameraTransforms.TransformType.FIXED, 0xF000F0, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        //dispatcher.renderBlock(ModBlocks.BASALT_BASIN.get().getDefaultState(), matrixStack, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
       // matrixStack.pop();

        //// ITEM
       /* matrixStack.push();
        matrixStack.translate(63, 36, 128 + -16);
        matrixStack.scale(16, -16, 16);
        matrixStack.rotate(new Quaternion(recipe.getInput().getItem() instanceof BlockItem ? 30 : 0, -90 + 180 * ((float) value / timer.getMaxValue()), 0, true));
        itemRenderer.renderItem(recipe.getInput(), ItemCameraTransforms.TransformType.FIXED, 0xF000F0, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        matrixStack.pop();

        buffer.finish(); */

        //FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        //String help = FluxTranslate.JEI_LEFT_CLICK.format(recipe.getCrusher().getBlock().getTranslatedName().getString());
        //fontRenderer.drawString(matrixStack, help, 64 - fontRenderer.getStringWidth(help) / 2f, 68, 0xff404040);
   // }
}
