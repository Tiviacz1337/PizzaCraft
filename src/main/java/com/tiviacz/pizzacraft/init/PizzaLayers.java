package com.tiviacz.pizzacraft.init;

import com.google.common.collect.Maps;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PizzaLayers
{
   // public static final Map<Item, ResourceLocation> ITEM_TO_LAYER = Maps.newHashMap();
   // public static final Map<Item, ResourceLocation> ITEM_TO_RAW_LAYER = Maps.newHashMap();
    public static final Map<TagKey<Item>, ResourceLocation> TAG_TO_LAYER = Maps.newHashMap();
    public static final Map<TagKey<Item>, ResourceLocation> TAG_TO_RAW_LAYER = Maps.newHashMap();
    public static final Map<TagKey<Item>, ResourceLocation> TAG_TO_ITEM_LAYER = Maps.newHashMap();
    public static final Map<TagKey<Item>, Integer> TAG_TO_MAX_STACK_SIZE = Maps.newHashMap();
    //public static final Map<Item, Integer> MAX_STACK_SIZE = Maps.newHashMap();

    //public static final ResourceLocation CRUST_MODEL = new ResourceLocation(PizzaCraft.MODID, "models/block/raw_pizza_crust");

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

    public static final ResourceLocation TOMATO_SAUCE_ITEM_LAYER = new ResourceLocation(PizzaCraft.MODID, "item/tomato_sauce_item_layer");

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

    public static Map<TagKey<Item>, Integer> getTagToMaxStackSize()
    {
        return TAG_TO_MAX_STACK_SIZE;
    }

    public static int getMaxStackSizeForStack(ItemStack stack)
    {
        List<TagKey<Item>> tags = stack.getTags().toList();

        for(TagKey<Item> tag : tags)
        {
            if(PizzaLayers.VALID_TAGS.contains(tag))
            {
                return PizzaLayers.getTagToMaxStackSize().get(tag);
            }
        }
      /*  if(stack.getTags().anyMatch(t -> PizzaLayers.VALID_TAGS.contains(t.location())))
        {
            return PizzaLayers.getTagToMaxStackSize().get(stack.getTags().findFirst().get().location());
        } */
        return 0;
     /*  for(ResourceLocation tagLocation : stack.getItem().getTags())
        {
            if(PizzaLayers.VALID_TAGS.contains(tagLocation))
            {
                return PizzaLayers.getTagToMaxStackSize().get(tagLocation);
            }
        }
        return 0; */
    }

  /*  public static Map<ResourceLocation, Integer> getTagToMaxStackSize()
    {
        return TAG_TO_MAX_STACK_SIZE;
    } */

/*    public static Map<Item, ResourceLocation> getItemToLayerMap()
    {
        return ITEM_TO_LAYER;
    }

    public static Map<Item, ResourceLocation> getItemToRawLayerMap()
    {
        return ITEM_TO_RAW_LAYER;
    } */

/*    public static Map<Item, Integer> getMaxStackSizeMap()
    {
        return MAX_STACK_SIZE;
    } */

    public static void setMaps()
    {
        setTagToLayerMap();
        setTagToRawLayerMap();
        setTagToItemLayer();
        setTagToMaxStackSize();
       // setMaxStackSizeMap();
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
    }

    public static void setTagToMaxStackSize()
    {
        TAG_TO_MAX_STACK_SIZE.put(ModTags.CHEESE_LAYER, 1);

        TAG_TO_MAX_STACK_SIZE.put(ModTags.BROCCOLI_LAYER, 2);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.CORN_LAYER, 2);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.CUCUMBER_LAYER, 4);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.ONION_LAYER, 4);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.PEPPER_LAYER, 4);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.TOMATO_LAYER, 4);

        TAG_TO_MAX_STACK_SIZE.put(ModTags.PINEAPPLE_LAYER, 4);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.OLIVE_LAYER, 4);

        TAG_TO_MAX_STACK_SIZE.put(ModTags.MUSHROOM_LAYER, 4);

        TAG_TO_MAX_STACK_SIZE.put(ModTags.HAM_LAYER,4);
        TAG_TO_MAX_STACK_SIZE.put(ModTags.CHICKEN_LAYER, 4);

        TAG_TO_MAX_STACK_SIZE.put(ModTags.TOMATO_SAUCE, 1);
    }

    public static ResourceLocation createResourceLocation(String tagName)
    {
        return new ResourceLocation(PizzaCraft.MODID, tagName);
    }

 /*   public static void setItemToLayerMap()
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
    } */

    public static void setMaxStackSizeMap()
    {
  /*      //Stack size map
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

        MAX_STACK_SIZE.put(ModItems.TOMATO_SAUCE.get(), 1); */
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

            ModTags.TOMATO_SAUCE
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

            ModTags.TOMATO_SAUCE
    );
}
