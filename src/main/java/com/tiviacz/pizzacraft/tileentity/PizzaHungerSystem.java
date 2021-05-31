package com.tiviacz.pizzacraft.tileentity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PizzaHungerSystem //#TODO needs tweaks
{
    public static final int BASE_HUNGER = 7;
    public static final Float BASE_SATURATION = 2.8F;

    private final ItemStackHandler handler;
    private final NonNullList<ItemStack> ingredients;

    private int hunger;
    private float saturation;
    private List<Pair<EffectInstance, Float>> effects;

    public PizzaHungerSystem(ItemStackHandler handler)
    {
        this.handler = handler;
        this.ingredients = setupIngredients();
        this.effects = new ArrayList<>();
        execute();
    }

    public NonNullList<ItemStack> setupIngredients()
    {
        NonNullList<ItemStack> ingredients = NonNullList.create();

        for(int i = 0; i < handler.getSlots(); i++)
        {
            if(!handler.getStackInSlot(i).isEmpty())
            {
                ingredients.add(handler.getStackInSlot(i));
            }
        }
        return ingredients;
    }

    public void execute()
    {
        int baseHunger = BASE_HUNGER;
        float baseSaturation = BASE_SATURATION;
        List<Pair<EffectInstance, Float>> effects = new ArrayList<>();

        for(ItemStack foodStack : ingredients)
        {
            if(foodStack.isFood())
            {
                Food food = foodStack.getItem().getFood();

                baseHunger += (food.getHealing() * foodStack.getCount());
                baseSaturation += (food.getSaturation() * foodStack.getCount());

                if(!food.getEffects().isEmpty())
                {
                    List<Pair<EffectInstance, Float>> foodEffects = food.getEffects();
                    effects.addAll(foodEffects);
                }
            }
        }


        //Little boost, if all ingredients are different :)
  /*      if(ingredients.size() == 9)
        {
            for(int i = 0; i < ingredients.size(); i++)
            {
                for(int j = i + 1; j < ingredients.size(); j++)
                {
                    if(ingredients.get(i).getItem() != ingredients.get(j).getItem())
                    {
                        //this.effects.add(Pair.of(new EffectInstance(Effects.ABSORPTION, 3600, 2, true, false), 1.0F));
                    }
                }
            }
        } */

        //baseHunger += 2;
        //baseSaturation += 1.0F;

        this.hunger = baseHunger;
        this.saturation = baseSaturation;
        this.effects = effects;
    }

    public int getHunger()
    {
        return this.hunger;
    }

    public float getSaturation()
    {
        return this.saturation;
    }

    public List<Pair<EffectInstance, Float>> getEffects()
    {
        return this.effects;
    }

    public void applyModifiersForIngredients(NonNullList<ItemStack> ingredients)
    {

    }
}