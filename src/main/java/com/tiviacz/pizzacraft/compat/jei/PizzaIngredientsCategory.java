package com.tiviacz.pizzacraft.compat.jei;

public class PizzaIngredientsCategory// implements IRecipeCategory<PizzaIngredientsType>
{

 /*   public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "pizza_ingredients");

    private final IDrawable background;
    private final IDrawable icon;
    private final String title;

    public PizzaIngredientsCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/crushing_recipe.png"), -5, -5, 96, 36);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.RAW_PIZZA.get()));
        title = I18n.format("recipecategory." + PizzaCraft.MODID + ".pizza_ingredients");
    }

    @Override
    public ResourceLocation getUid()
    {
        return ID;
    }

    @Override
    public Class<? extends PizzaIngredientsType> getRecipeClass()
    {
        return PizzaIngredientsType.class;
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

    @Nonnull
    public static List<PizzaIngredientsType> getIngredients()
    {
        List<PizzaIngredientsType> ingredients = new ArrayList<>();
        ingredients.add(new PizzaIngredientsType(Ingredient.fromTag(ModTags.INGREDIENTS_ITEM.get()), 4));
        return ingredients;
    }

    @Override
    public void setIngredients(PizzaIngredientsType pizzaIngredientsType, IIngredients iIngredients)
    {
        iIngredients.setInputs(VanillaTypes.ITEM, Arrays.asList(pizzaIngredientsType.tagName.getMatchingStacks()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, PizzaIngredientsType pizzaIngredientsType, IIngredients iIngredients)
    {
        IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();

        stacks.init(0, true, 14, 6);
        stacks.set(0, Arrays.asList(pizzaIngredientsType.tagName.getMatchingStacks()));
    } */
}