package com.tiviacz.pizzacraft.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods
{
    //Vegetables
    public static final FoodProperties BROCCOLI = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties CORN = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties CUCUMBER = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties ONION = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties PEPPER = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties TOMATO = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();

    //Fruits
    public static final FoodProperties PINEAPPLE = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties OLIVE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build();

    //Slices
    public static final FoodProperties CUCUMBER_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final FoodProperties ONION_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final FoodProperties PEPPER_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final FoodProperties PINEAPPLE_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final FoodProperties TOMATO_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    //public static final Food CHEESE_SLICE = new Food.Builder().nutrition(1).saturationMod(1.0F).fastToEat().build();

    //Meats
    public static final FoodProperties HAM = new FoodProperties.Builder().nutrition(1).saturationMod(0.15F).fast().meat().build();
    public static final FoodProperties WING = new FoodProperties.Builder().nutrition(1).saturationMod(0.15F).fast().meat().build();
    public static final FoodProperties COOKED_WING = new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).fast().meat().build();

    //Sauce
    public static final FoodProperties OLIVE_OIL = new FoodProperties.Builder().nutrition(2).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 240, 1), 0.75F).build();
    public static final FoodProperties TOMATO_SAUCE = new FoodProperties.Builder().nutrition(6).saturationMod(1.2F).build();

    //Cheese
    public static final FoodProperties CHEESE = new FoodProperties.Builder().nutrition(4).saturationMod(1.6F).build();

    public static FoodProperties.Builder startBuilding(int nutrition, float saturationMod)
    {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
    }
}