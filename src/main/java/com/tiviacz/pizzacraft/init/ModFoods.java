package com.tiviacz.pizzacraft.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods
{
    //Vegetables
    public static final Food BROCCOLI = new Food.Builder().hunger(2).saturation(0.6F).build();
    public static final Food CORN = new Food.Builder().hunger(2).saturation(0.6F).build();
    public static final Food CUCUMBER = new Food.Builder().hunger(2).saturation(0.6F).build();
    public static final Food ONION = new Food.Builder().hunger(2).saturation(0.6F).build();
    public static final Food PEPPER = new Food.Builder().hunger(2).saturation(0.6F).build();
    public static final Food TOMATO = new Food.Builder().hunger(2).saturation(0.6F).build();

    //Fruits
    public static final Food PINEAPPLE = new Food.Builder().hunger(2).saturation(0.6F).build();
    public static final Food OLIVE = new Food.Builder().hunger(1).saturation(0.3F).fastToEat().build();

    //Slices
    public static final Food CUCUMBER_SLICE = new Food.Builder().hunger(1).saturation(0.3F).fastToEat().build();
    public static final Food ONION_SLICE = new Food.Builder().hunger(1).saturation(0.3F).fastToEat().build();
    public static final Food PEPPER_SLICE = new Food.Builder().hunger(1).saturation(0.3F).fastToEat().build();
    public static final Food PINEAPPLE_SLICE = new Food.Builder().hunger(1).saturation(0.3F).fastToEat().build();
    public static final Food TOMATO_SLICE = new Food.Builder().hunger(1).saturation(0.3F).fastToEat().build();
    //public static final Food CHEESE_SLICE = new Food.Builder().hunger(1).saturation(1.0F).fastToEat().build();

    //Meats
    public static final Food HAM = new Food.Builder().hunger(1).saturation(0.15F).fastToEat().meat().build();
    public static final Food WING = new Food.Builder().hunger(1).saturation(0.15F).fastToEat().meat().build();
    public static final Food COOKED_WING = new Food.Builder().hunger(3).saturation(0.3F).fastToEat().meat().build();

    //Sauce
    public static final Food OLIVE_OIL = new Food.Builder().hunger(2).saturation(1.2F).effect(() -> new EffectInstance(Effects.ABSORPTION, 240, 1), 0.75F).build();
    public static final Food TOMATO_SAUCE = new Food.Builder().hunger(6).saturation(1.2F).build();

    //Cheese
    public static final Food CHEESE = new Food.Builder().hunger(4).saturation(1.6F).build();

    public static Food.Builder startBuilding(int hunger, float saturation)
    {
        return new Food.Builder().hunger(hunger).saturation(saturation);
    }
}