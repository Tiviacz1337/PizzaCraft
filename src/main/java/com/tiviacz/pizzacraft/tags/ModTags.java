package com.tiviacz.pizzacraft.tags;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags
{
    public static final TagKey<Item> FERMENTING_ITEMS_TAG = TagKey.create(Registry.ITEM.key(), createResourceLocation( "fermenting_items"));
    public static final TagKey<Item> INGREDIENTS = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients"));
    public static final TagKey<Item> SLICED_INGREDIENTS = TagKey.create(Registry.ITEM.key(), createResourceLocation("sliced_ingredients"));

    public static final TagKey<Item> CHEESE_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/cheese_layer"));

    public static final TagKey<Item> BROCCOLI_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/vegetables/broccoli_layer"));
    public static final TagKey<Item> CORN_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/vegetables/corn_layer"));
    public static final TagKey<Item> CUCUMBER_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/vegetables/cucumber_layer"));
    public static final TagKey<Item> ONION_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/vegetables/onion_layer"));
    public static final TagKey<Item> PEPPER_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/vegetables/pepper_layer"));
    public static final TagKey<Item> TOMATO_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/vegetables/tomato_layer"));

    public static final TagKey<Item> PINEAPPLE_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/fruits/pineapple_layer"));
    public static final TagKey<Item> OLIVE_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/fruits/olive_layer"));

    public static final TagKey<Item> MUSHROOM_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/mushrooms/mushroom_layer"));

    public static final TagKey<Item> HAM_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/meats/ham_layer"));
    public static final TagKey<Item> CHICKEN_LAYER = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/meats/chicken_layer"));

    public static final TagKey<Item> TOMATO_SAUCE = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/sauces/tomato_sauce_layer"));
    public static final TagKey<Item> HOT_SAUCE = TagKey.create(Registry.ITEM.key(), createResourceLocation("ingredients/sauces/hot_sauce_layer"));

    public static ResourceLocation createResourceLocation(String tagName)
    {
        return new ResourceLocation(PizzaCraft.MODID, tagName);
    }
}