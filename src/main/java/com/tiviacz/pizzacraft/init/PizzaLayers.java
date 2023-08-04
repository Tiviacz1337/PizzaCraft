package com.tiviacz.pizzacraft.init;

import com.google.common.collect.Maps;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.tags.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PizzaLayers
{
    public static final Map<TagKey<Item>, ResourceLocation> TAG_TO_LAYER = Maps.newHashMap();
    public static final Map<TagKey<Item>, ResourceLocation> TAG_TO_RAW_LAYER = Maps.newHashMap();
    public static final Map<TagKey<Item>, ResourceLocation> TAG_TO_ITEM_LAYER = Maps.newHashMap();

    //Layers
    //Base
    public static final ResourceLocation PIZZA_SLICE = new ResourceLocation(PizzaCraft.MODID, "item/pizza_slice");

    public static final ResourceLocation CHEESE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/cheese_layer");
    public static final ResourceLocation RAW_CHEESE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_cheese_layer");

    //Vegetables
    public static final ResourceLocation BROCCOLI_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/broccoli_layer");
    public static final ResourceLocation CORN_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/corn_layer");
    public static final ResourceLocation CUCUMBER_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/cucumber_layer");
    public static final ResourceLocation ONION_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/onion_layer");
    public static final ResourceLocation PEPPER_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/pepper_layer");
    public static final ResourceLocation TOMATO_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/tomato_layer");

    public static final ResourceLocation RAW_BROCCOLI_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_broccoli_layer");
    public static final ResourceLocation RAW_CORN_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_corn_layer");
    public static final ResourceLocation RAW_CUCUMBER_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_cucumber_layer");
    public static final ResourceLocation RAW_ONION_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_onion_layer");
    public static final ResourceLocation RAW_PEPPER_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_pepper_layer");
    public static final ResourceLocation RAW_TOMATO_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_tomato_layer");

    public static final ResourceLocation BROCCOLI_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/broccoli_item_layer");
    public static final ResourceLocation CORN_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/corn_item_layer");
    public static final ResourceLocation CUCUMBER_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/cucumber_item_layer");
    public static final ResourceLocation ONION_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/onion_item_layer");
    public static final ResourceLocation PEPPER_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/pepper_item_layer");
    public static final ResourceLocation TOMATO_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/tomato_item_layer");

    //Fruits
    public static final ResourceLocation PINEAPPLE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/pineapple_layer");
    public static final ResourceLocation OLIVE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/olive_layer");

    public static final ResourceLocation RAW_PINEAPPLE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_pineapple_layer");
    public static final ResourceLocation RAW_OLIVE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_olive_layer");

    public static final ResourceLocation PINEAPPLE_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/pineapple_item_layer");
    public static final ResourceLocation OLIVE_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/olive_item_layer");

    //Mushrooms
    public static final ResourceLocation MUSHROOM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/mushroom_layer");

    public static final ResourceLocation RAW_MUSHROOM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_mushroom_layer");

    public static final ResourceLocation MUSHROOM_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/mushroom_item_layer");

    //Meats
    public static final ResourceLocation HAM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/ham_layer");
    public static final ResourceLocation BEEF_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/beef_layer");
    public static final ResourceLocation CHICKEN_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/chicken_layer");

    public static final ResourceLocation RAW_HAM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_ham_layer");
    public static final ResourceLocation RAW_BEEF_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_beef_layer");
    public static final ResourceLocation RAW_CHICKEN_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_chicken_layer");

    public static final ResourceLocation HAM_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/ham_item_layer");
    public static final ResourceLocation BEEF_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/beef_item_layer");
    public static final ResourceLocation CHICKEN_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/chicken_item_layer");

    //Sauces
    public static final ResourceLocation TOMATO_SAUCE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/tomato_sauce_layer");
    public static final ResourceLocation HOT_SAUCE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/hot_sauce_layer");

    public static final ResourceLocation TOMATO_SAUCE_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/tomato_sauce_item_layer");
    public static final ResourceLocation HOT_SAUCE_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/hot_sauce_item_layer");

    public static Map<TagKey<Item>, ResourceLocation> getTagToLayer()
    {
        return TAG_TO_LAYER;
    }

    public static Map<TagKey<Item>, ResourceLocation> getTagToRawLayer()
    {
        return TAG_TO_RAW_LAYER;
    }

    public static Map<TagKey<Item>, ResourceLocation> getTagToItemLayer()
    {
        return TAG_TO_ITEM_LAYER;
    }

    public static void setMaps()
    {
        setTagToLayerMap();
        setTagToRawLayerMap();
        setTagToItemLayer();
    }

    public static void setTagToLayerMap()
    {
        TAG_TO_LAYER.put(ModTags.CHEESE_LAYER, CHEESE_LAYER);

        TAG_TO_LAYER.put(ModTags.BROCCOLI_LAYER, BROCCOLI_LAYER);
        TAG_TO_LAYER.put(ModTags.CORN_LAYER, CORN_LAYER);
        TAG_TO_LAYER.put(ModTags.CUCUMBER_LAYER, CUCUMBER_LAYER);
        TAG_TO_LAYER.put(ModTags.ONION_LAYER, ONION_LAYER);
        TAG_TO_LAYER.put(ModTags.PEPPER_LAYER, PEPPER_LAYER);
        TAG_TO_LAYER.put(ModTags.TOMATO_LAYER, TOMATO_LAYER);

        TAG_TO_LAYER.put(ModTags.PINEAPPLE_LAYER, PINEAPPLE_LAYER);
        TAG_TO_LAYER.put(ModTags.OLIVE_LAYER, OLIVE_LAYER);

        TAG_TO_LAYER.put(ModTags.MUSHROOM_LAYER, MUSHROOM_LAYER);

        TAG_TO_LAYER.put(ModTags.HAM_LAYER, HAM_LAYER);
        TAG_TO_LAYER.put(ModTags.CHICKEN_LAYER, CHICKEN_LAYER);

        TAG_TO_LAYER.put(ModTags.TOMATO_SAUCE, TOMATO_SAUCE_LAYER);
        TAG_TO_LAYER.put(ModTags.HOT_SAUCE, HOT_SAUCE_LAYER);
    }

    public static void setTagToRawLayerMap()
    {
        TAG_TO_RAW_LAYER.put(ModTags.CHEESE_LAYER, RAW_CHEESE_LAYER);

        TAG_TO_RAW_LAYER.put(ModTags.BROCCOLI_LAYER, RAW_BROCCOLI_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.CORN_LAYER, RAW_CORN_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.CUCUMBER_LAYER, RAW_CUCUMBER_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.ONION_LAYER, RAW_ONION_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.PEPPER_LAYER, RAW_PEPPER_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.TOMATO_LAYER, RAW_TOMATO_LAYER);

        TAG_TO_RAW_LAYER.put(ModTags.PINEAPPLE_LAYER, RAW_PINEAPPLE_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.OLIVE_LAYER, RAW_OLIVE_LAYER);

        TAG_TO_RAW_LAYER.put(ModTags.MUSHROOM_LAYER, RAW_MUSHROOM_LAYER);

        TAG_TO_RAW_LAYER.put(ModTags.HAM_LAYER, RAW_HAM_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.CHICKEN_LAYER, RAW_CHICKEN_LAYER);

        TAG_TO_RAW_LAYER.put(ModTags.TOMATO_SAUCE, TOMATO_SAUCE_LAYER);
        TAG_TO_RAW_LAYER.put(ModTags.HOT_SAUCE, HOT_SAUCE_LAYER);
    }

    public static void setTagToItemLayer()
    {
        TAG_TO_ITEM_LAYER.put(ModTags.BROCCOLI_LAYER, BROCCOLI_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.CORN_LAYER, CORN_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.CUCUMBER_LAYER, CUCUMBER_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.ONION_LAYER, ONION_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.PEPPER_LAYER, PEPPER_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.TOMATO_LAYER, TOMATO_ITEM_LAYER);

        TAG_TO_ITEM_LAYER.put(ModTags.PINEAPPLE_LAYER, PINEAPPLE_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.OLIVE_LAYER, OLIVE_ITEM_LAYER);

        TAG_TO_ITEM_LAYER.put(ModTags.MUSHROOM_LAYER, MUSHROOM_ITEM_LAYER);

        TAG_TO_ITEM_LAYER.put(ModTags.HAM_LAYER, HAM_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.CHICKEN_LAYER, CHICKEN_ITEM_LAYER);

        TAG_TO_ITEM_LAYER.put(ModTags.TOMATO_SAUCE, TOMATO_SAUCE_ITEM_LAYER);
        TAG_TO_ITEM_LAYER.put(ModTags.HOT_SAUCE, TOMATO_SAUCE_ITEM_LAYER);
    }

    public static ResourceLocation createResourceLocation(String tagName)
    {
        return new ResourceLocation(PizzaCraft.MODID, tagName);
    }

    public static final List<TagKey<Item>> VALID_TAGS = Arrays.asList
    (
            ModTags.CHEESE_LAYER,

            ModTags.BROCCOLI_LAYER,
            ModTags.CORN_LAYER,
            ModTags.CUCUMBER_LAYER,
            ModTags.ONION_LAYER,
            ModTags.PEPPER_LAYER,
            ModTags.TOMATO_LAYER,

            ModTags.PINEAPPLE_LAYER,
            ModTags.OLIVE_LAYER,

            ModTags.MUSHROOM_LAYER,

            ModTags.HAM_LAYER,
            ModTags.CHICKEN_LAYER,

            ModTags.TOMATO_SAUCE,
            ModTags.HOT_SAUCE
    );

    public static final List<TagKey<Item>> VALID_ITEM_TAGS = Arrays.asList
    (
            ModTags.BROCCOLI_LAYER,
            ModTags.CORN_LAYER,
            ModTags.CUCUMBER_LAYER,
            ModTags.ONION_LAYER,
            ModTags.PEPPER_LAYER,
            ModTags.TOMATO_LAYER,

            ModTags.PINEAPPLE_LAYER,
            ModTags.OLIVE_LAYER,

            ModTags.MUSHROOM_LAYER,

            ModTags.HAM_LAYER,
            ModTags.CHICKEN_LAYER,

            ModTags.TOMATO_SAUCE,
            ModTags.HOT_SAUCE
    );
}
