package com.tiviacz.pizzacraft.compat.jei;

import java.util.Arrays;

import com.tiviacz.pizzacraft.compat.jei.bakeware.BakewareRecipeCategory;
import com.tiviacz.pizzacraft.compat.jei.bakeware.ShapelessOreRecipeWrapper;
import com.tiviacz.pizzacraft.compat.jei.chopping.ChoppingBoardRecipeCategory;
import com.tiviacz.pizzacraft.compat.jei.mortar.MortarRecipeCategory;
import com.tiviacz.pizzacraft.compat.jei.mortar.MortarShapelessOreRecipeWrapper;
import com.tiviacz.pizzacraft.crafting.bakeware.BaseShapelessOreRecipe;
import com.tiviacz.pizzacraft.crafting.bakeware.ItemStackUtils;
import com.tiviacz.pizzacraft.crafting.bakeware.PizzaCraftingManager;
import com.tiviacz.pizzacraft.crafting.mortar.BaseMortarShapelessOreRecipe;
import com.tiviacz.pizzacraft.crafting.mortar.MortarRecipeManager;
import com.tiviacz.pizzacraft.gui.GuiBakeware;
import com.tiviacz.pizzacraft.gui.container.ContainerBakeware;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.util.TextUtils;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class PizzaCraftPlugin implements IModPlugin
{
    public static final String JEI_CATEGORY_BAKEWARE = TextUtils.setLocation("bakeware");
    public static final String JEI_CATEGORY_MORTAR_AND_PESTLE = TextUtils.setLocation("mortar_and_pestle");
    public static final String JEI_CATEGORY_CHOPPING_BOARD = TextUtils.setLocation("chopping_board");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        
        registry.addRecipeCategories(new BakewareRecipeCategory(jeiHelpers.getGuiHelper(), "bakeware", JEI_CATEGORY_BAKEWARE));
        registry.addRecipeCategories(new MortarRecipeCategory(jeiHelpers.getGuiHelper(), "mortar_and_pestle", JEI_CATEGORY_MORTAR_AND_PESTLE));
        registry.addRecipeCategories(new ChoppingBoardRecipeCategory(jeiHelpers.getGuiHelper(), "chopping_board", JEI_CATEGORY_CHOPPING_BOARD));
    }

    @Override
    public void register(IModRegistry registry) 
    {
    	registerDescriptions(registry);
    	final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    	IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();
    	
    	//Chopping Board
    	registry.addRecipes(JEIUtils.getRecipes(jeiHelpers), JEI_CATEGORY_CHOPPING_BOARD);
    	registry.addRecipeCatalyst(ItemStackUtils.getItemStack(ModBlocks.CHOPPING_BOARD), JEI_CATEGORY_CHOPPING_BOARD);

        //Bakeware
        this.handleBakewareRecipes(registry, JEI_CATEGORY_BAKEWARE);
        recipeTransfer.addRecipeTransferHandler(ContainerBakeware.class, JEI_CATEGORY_BAKEWARE, 0, 9, 10, 36);
        registry.addRecipeClickArea(GuiBakeware.class, 90, 35, 22, 15, JEI_CATEGORY_BAKEWARE);
        registry.addRecipeCatalyst(ItemStackUtils.getItemStack(ModBlocks.BAKEWARE), JEI_CATEGORY_BAKEWARE);
        registry.addRecipes(PizzaCraftingManager.getPizzaCraftingInstance().getRecipeList(), JEI_CATEGORY_BAKEWARE);
        
        //Mortar and Pestle
        this.handleMortarAndPestleRecipes(registry, JEI_CATEGORY_MORTAR_AND_PESTLE);
        registry.addRecipeCatalyst(ItemStackUtils.getItemStack(ModBlocks.MORTAR_AND_PESTLE), JEI_CATEGORY_MORTAR_AND_PESTLE);
        registry.addRecipes(MortarRecipeManager.getMortarManagerInstance().getRecipeList(), JEI_CATEGORY_MORTAR_AND_PESTLE);
    }

    private void handleBakewareRecipes(IModRegistry registry, String category) 
    {
        registry.handleRecipes(BaseShapelessOreRecipe.class, recipe -> new ShapelessOreRecipeWrapper(registry.getJeiHelpers(), recipe, recipe.getInput()), category);
    }
    
    private void handleMortarAndPestleRecipes(IModRegistry registry, String category)
    {
    	registry.handleRecipes(BaseMortarShapelessOreRecipe.class, recipe -> new MortarShapelessOreRecipeWrapper(registry.getJeiHelpers(), recipe, recipe.getInput()), category);
    }

    //Descriptions
    private void registerDescriptions(IModRegistry registry, EntryDescription entry) 
    {
        registry.addIngredientInfo(entry.getStack(), VanillaTypes.ITEM, TextUtils.translatedText(entry.getDesc()));
    }

    private void registerDescriptions(IModRegistry registry, EntryDescription... entries) 
    {
        Arrays.stream(entries).forEachOrdered(entry -> registerDescriptions(registry, entry));
    }

    private EntryDescription createDesc(ItemStack stack, String desc) 
    {
        return new EntryDescription(stack, desc);
    }

    private class EntryDescription 
    {
        private final ItemStack stack;
        private final String desc;

        public EntryDescription(ItemStack stack, String desc) 
        {
            this.stack = stack;
            this.desc = desc;
        }

        public String getDesc() 
        {
            return desc;
        }

        public ItemStack getStack() 
        {
            return stack;
        }
    }
}