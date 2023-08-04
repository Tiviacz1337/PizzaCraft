package com.tiviacz.pizzacraft.common;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class PizzaBlockCalculator extends PizzaCalculator
{
    public PizzaBlockCalculator(ItemStackHandler ingredients)
    {
        super(ItemStack.EMPTY, ingredients.getStackInSlot(9), ingredients);

        resetStats();
    }

    public void process()
    {
        //Base
        this.hunger += 7;
        this.saturation += 2.8F;

        for(int i = 0; i < ingredients.getSlots(); i++)
        {
            processFood(ingredients.getStackInSlot(i).copy());
        }
    }

    public int getHunger()
    {
        return this.hunger;
    }

    public float getSaturation()
    {
        return this.saturation;
    }

    public int getUniqueness()
    {
        return this.uniqueness;
    }

    public List<Pair<MobEffectInstance, Float>> getEffects()
    {
        return this.effects;
    }
}