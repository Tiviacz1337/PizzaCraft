package com.tiviacz.pizzacraft.init;

import com.google.common.collect.Maps;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class PizzaLayers
{
    public static final Map<Item, ResourceLocation> ITEM_TO_LAYER = Maps.newHashMap();
    public static final Map<Item, ResourceLocation> ITEM_TO_RAW_LAYER = Maps.newHashMap();
    //public static final Map<ResourceLocation, ResourceLocation> UNCOOKED_TO_COOKED = Maps.newHashMap();
    public static final Map<Item, Integer> MAX_STACK_SIZE = Maps.newHashMap();

    public static final ResourceLocation CRUST_MODEL = new ResourceLocation(PizzaCraft.MODID, "models/block/raw_pizza_crust");
    //Layers//

    //Base
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

    //Fruits
    public static final ResourceLocation PINEAPPLE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/pineapple_layer");
    public static final ResourceLocation OLIVE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/olive_layer");

    public static final ResourceLocation RAW_PINEAPPLE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_pineapple_layer");
    public static final ResourceLocation RAW_OLIVE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_olive_layer");

    public static final ResourceLocation MUSHROOM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/mushroom_layer");

    public static final ResourceLocation RAW_MUSHROOM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_mushroom_layer");

    //Meats
    public static final ResourceLocation HAM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/ham_layer");
    public static final ResourceLocation BEEF_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/beef_layer");
    public static final ResourceLocation CHICKEN_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/chicken_layer");

    public static final ResourceLocation RAW_HAM_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_ham_layer");
    public static final ResourceLocation RAW_BEEF_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_beef_layer");
    public static final ResourceLocation RAW_CHICKEN_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/raw_chicken_layer");

    //Sauces
    public static final ResourceLocation TOMATO_SAUCE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/tomato_sauce_layer");

    public static Map<Item, ResourceLocation> getItemToLayerMap()
    {
        return ITEM_TO_LAYER;
    }

    public static Map<Item, ResourceLocation> getItemToRawLayerMap()
    {
        return ITEM_TO_RAW_LAYER;
    }
   // public static Map<ResourceLocation, ResourceLocation> getUncookedToCookedMap()
  //  {
   //     return UNCOOKED_TO_COOKED;
   // }

    public static Map<Item, Integer> getMaxStackSizeMap()
    {
        return MAX_STACK_SIZE;
    }

    public static void setMaps()
    {
        setItemToLayerMap();
        setItemToRawLayerMap();
        setMaxStackSizeMap();
    }

    public static void setItemToLayerMap()
    {
        //Layer map
        ITEM_TO_LAYER.put(ModItems.CHEESE.get(), CHEESE_LAYER);

        ITEM_TO_LAYER.put(ModItems.BROCCOLI.get(), BROCCOLI_LAYER);
        ITEM_TO_LAYER.put(ModItems.CORN.get(), CORN_LAYER);
        ITEM_TO_LAYER.put(ModItems.CUCUMBER_SLICE.get(), CUCUMBER_LAYER);
        ITEM_TO_LAYER.put(ModItems.ONION_SLICE.get(), ONION_LAYER);
        ITEM_TO_LAYER.put(ModItems.PEPPER_SLICE.get(), PEPPER_LAYER);
        ITEM_TO_LAYER.put(ModItems.TOMATO_SLICE.get(), TOMATO_LAYER);

        ITEM_TO_LAYER.put(ModItems.PINEAPPLE_SLICE.get(), PINEAPPLE_LAYER);
        ITEM_TO_LAYER.put(ModItems.OLIVE.get(), OLIVE_LAYER);

        ITEM_TO_LAYER.put(Items.BROWN_MUSHROOM, MUSHROOM_LAYER);
        ITEM_TO_LAYER.put(Items.RED_MUSHROOM, MUSHROOM_LAYER);

        ITEM_TO_LAYER.put(ModItems.HAM.get().asItem(), HAM_LAYER);
        ITEM_TO_LAYER.put(ModItems.WING.get().asItem(), CHICKEN_LAYER);

        ITEM_TO_LAYER.put(ModItems.TOMATO_SAUCE.get(), TOMATO_SAUCE_LAYER);
    }

    public static void setItemToRawLayerMap()
    {
        //Layer map
        ITEM_TO_RAW_LAYER.put(ModItems.CHEESE.get(), RAW_CHEESE_LAYER);

        ITEM_TO_RAW_LAYER.put(ModItems.BROCCOLI.get(), RAW_BROCCOLI_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.CORN.get(), RAW_CORN_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.CUCUMBER_SLICE.get(), RAW_CUCUMBER_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.ONION_SLICE.get(), RAW_ONION_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.PEPPER_SLICE.get(), RAW_PEPPER_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.TOMATO_SLICE.get(), RAW_TOMATO_LAYER);

        ITEM_TO_RAW_LAYER.put(ModItems.PINEAPPLE_SLICE.get(), RAW_PINEAPPLE_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.OLIVE.get(), RAW_OLIVE_LAYER);

        ITEM_TO_RAW_LAYER.put(Items.BROWN_MUSHROOM, RAW_MUSHROOM_LAYER);
        ITEM_TO_RAW_LAYER.put(Items.RED_MUSHROOM, RAW_MUSHROOM_LAYER);

        ITEM_TO_RAW_LAYER.put(ModItems.HAM.get().asItem(), RAW_HAM_LAYER);
        ITEM_TO_RAW_LAYER.put(ModItems.WING.get().asItem(), RAW_CHICKEN_LAYER);

        ITEM_TO_RAW_LAYER.put(ModItems.TOMATO_SAUCE.get().asItem(), TOMATO_SAUCE_LAYER);
    }

    public static void setMaxStackSizeMap()
    {
        //Stack size map
        MAX_STACK_SIZE.put(ModItems.CHEESE.get(), 1);
        MAX_STACK_SIZE.put(ModItems.BROCCOLI.get(), 2);
        MAX_STACK_SIZE.put(ModItems.CORN.get(), 2);
        MAX_STACK_SIZE.put(ModItems.CUCUMBER_SLICE.get(), 4);
        MAX_STACK_SIZE.put(ModItems.ONION_SLICE.get(), 4);
        MAX_STACK_SIZE.put(ModItems.PEPPER_SLICE.get(), 4);
        MAX_STACK_SIZE.put(ModItems.TOMATO_SLICE.get(), 4);

        MAX_STACK_SIZE.put(ModItems.PINEAPPLE_SLICE.get(), 4);
        MAX_STACK_SIZE.put(ModItems.OLIVE.get(), 4);

        MAX_STACK_SIZE.put(Items.BROWN_MUSHROOM, 4);
        MAX_STACK_SIZE.put(Items.RED_MUSHROOM, 4);

        MAX_STACK_SIZE.put(ModItems.HAM.get(), 4);
        MAX_STACK_SIZE.put(ModItems.WING.get(), 4);

        MAX_STACK_SIZE.put(ModItems.TOMATO_SAUCE.get(), 1);
    }
}
