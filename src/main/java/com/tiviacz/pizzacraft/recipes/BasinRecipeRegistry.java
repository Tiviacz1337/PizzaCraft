package com.tiviacz.pizzacraft.recipes;

import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasinRecipeRegistry
{
    public static final List<Map<ResourceLocation, String>> RECIPES_REGISTRY2 = new ArrayList<>();
    public static final List<Map<Item, String>> RECIPES_REGISTRY = new ArrayList<>();

    public static final Map<ResourceLocation, String> TOMATO_RECIPE_NEW = new HashMap<ResourceLocation, String>() {{
        put(new ResourceLocation("forge", "crops/tomato"), "tomato_sauce");
    }};

    public static final Map<Item, String> TOMATO_RECIPE = new HashMap<Item, String>() {{
        put(ModItems.TOMATO.get(), "tomato_sauce");
    }};
    public static final Map<Item, String> OLIVE_OIL_RECIPE = new HashMap<Item, String>() {{
        put(ModItems.OLIVE.get(), "olive_oil");
    }};

    public static void addRecipesToRegistry()
    {
        RECIPES_REGISTRY2.add(TOMATO_RECIPE_NEW);
        RECIPES_REGISTRY.add(TOMATO_RECIPE);
        RECIPES_REGISTRY.add(OLIVE_OIL_RECIPE);
    }
}