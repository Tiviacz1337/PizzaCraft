package com.tiviacz.pizzacraft.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;

import java.util.ArrayList;
import java.util.List;

public class NBTUtils
{
    public static final String PROBABILITY = "Probability";

    public static CompoundNBT writeEffectsToTag(List<Pair<EffectInstance, Float>> effects)
    {
        CompoundNBT compound = new CompoundNBT();

        if(!effects.isEmpty())
        {
            int i = 0;

            for(Pair<EffectInstance, Float> pair : effects)
            {
                CompoundNBT effect = new CompoundNBT();
                pair.getFirst().writeCurativeItems(effect);
                effect.putFloat(PROBABILITY, pair.getSecond());
                compound.put(String.valueOf(i), effect);
                i++;
            }
        }
        return compound;
    }

    public static List<Pair<EffectInstance, Float>> readEffectsFromTag(CompoundNBT effectsNBT)
    {
        List<Pair<EffectInstance, Float>> effects = new ArrayList<>();

        if(!effectsNBT.isEmpty())
        {
            for(int i = 0; i < effectsNBT.size(); i++)
            {
                CompoundNBT effect = effectsNBT.getCompound(String.valueOf(i));
                if(!effect.isEmpty())
                {
                    Pair<EffectInstance, Float> effectPair = Pair.of(EffectInstance.load(effect), effect.getFloat(PROBABILITY));
                    effects.add(effectPair);
                }
            }
        }
        return effects;
    }

    public static void writeToItemStack(ItemStack stack, CompoundNBT compound)
    {

    }
}