package com.tiviacz.pizzacraft.common;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tags.ModTags;
import com.tiviacz.pizzacraft.util.NBTUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class PizzaCalculator
{
    protected ItemStack base;
    protected ItemStack sauce;
    protected ItemStackHandler ingredients;

    protected NonNullList<ItemStack> processedFoods = NonNullList.create();
    protected List<Pair<MobEffectInstance, Float>> effects = new ArrayList<>();

    protected int uniqueness = 0;
    protected int hunger = 0;
    protected float saturation = 0;

    public PizzaCalculator(ItemStack base, ItemStack sauce, ItemStackHandler ingredients)
    {
        this.base = base;
        this.sauce = sauce;
        this.ingredients = ingredients;

        resetStats();
    }

    public ItemStack getResultStack()
    {
        ItemStack result = ModItems.RAW_PIZZA.get().getDefaultInstance();
        ingredients.setStackInSlot(9, sauce);

        //Base
        this.hunger += 7;
        this.saturation += 2.8F;

        for(int i = 0; i < ingredients.getSlots(); i++)
        {
            processFood(ingredients.getStackInSlot(i).copy());
        }

        NBTUtils.saveInventoryToStack(result, this.ingredients);
        NBTUtils.setUniqueness(result, this.uniqueness);
        NBTUtils.setHunger(result, this.hunger);
        NBTUtils.setSaturation(result, this.saturation);
        //NBTUtils.setEffects(result, this.effects);

        return result;
    }

    public void processFood(ItemStack stack)
    {
        if(!stack.isEdible()) return;

        FoodProperties food = stack.getItem().getFoodProperties(stack, null);

        int nutrition = food.getNutrition();
        float saturation = food.getSaturationModifier();

        if(stack.is(ModTags.SLICED_INGREDIENTS))
        {
            nutrition += 4;
            saturation += 0.25F;
        }

        this.hunger += nutrition;
        this.saturation += saturation;

        if(!food.getEffects().isEmpty())
        {
            List<Pair<MobEffectInstance, Float>> foodEffects = food.getEffects();

            for(Pair<MobEffectInstance, Float> pair : foodEffects)
            {
                if(!effects.contains(pair))
                {
                    effects.add(pair);
                }
            }
        }

        if(processedFoods.stream().noneMatch(s -> ItemStack.isSameItemSameTags(s, stack)))
        {
            this.uniqueness += 1;
        }
        processedFoods.add(stack);
    }

    public void resetStats()
    {
        this.uniqueness = 0;
        this.hunger = 0;
        this.saturation = 0;
        processedFoods.clear();
    }
}