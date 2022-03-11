package com.tiviacz.pizzacraft.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods
{
    //Vegetables
    public static final Food BROCCOLI = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final Food CORN = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final Food CUCUMBER = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final Food ONION = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final Food PEPPER = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final Food TOMATO = new Food.Builder().nutrition(2).saturationMod(0.6F).build();

    //Fruits
    public static final Food PINEAPPLE = new Food.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final Food OLIVE = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();

    //Slices
    public static final Food CUCUMBER_SLICE = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final Food ONION_SLICE = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final Food PEPPER_SLICE = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final Food PINEAPPLE_SLICE = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    public static final Food TOMATO_SLICE = new Food.Builder().nutrition(1).saturationMod(0.3F).fast().build();
    //public static final Food CHEESE_SLICE = new Food.Builder().nutrition(1).saturationMod(1.0F).fastToEat().build();

    //Meats
    public static final Food HAM = new Food.Builder().nutrition(1).saturationMod(0.15F).fast().meat().build();
    public static final Food WING = new Food.Builder().nutrition(1).saturationMod(0.15F).fast().meat().build();
    public static final Food COOKED_WING = new Food.Builder().nutrition(3).saturationMod(0.3F).fast().meat().build();

    //Sauce
    public static final Food OLIVE_OIL = new Food.Builder().nutrition(2).saturationMod(1.2F).effect(() -> new EffectInstance(Effects.ABSORPTION, 240, 1), 0.75F).build();
    public static final Food TOMATO_SAUCE = new Food.Builder().nutrition(6).saturationMod(1.2F).build();

    //Cheese
    public static final Food CHEESE = new Food.Builder().nutrition(4).saturationMod(1.6F).build();

    public static Food.Builder startBuilding(int nutrition, float saturationMod)
    {
        return new Food.Builder().nutrition(nutrition).saturationMod(saturationMod);
    }
}