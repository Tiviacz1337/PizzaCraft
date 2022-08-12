package com.tiviacz.pizzacraft.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NBTUtils
{
    public static final String PROBABILITY = "Probability";

    public static CompoundTag writeEffectsToTag(List<Pair<MobEffectInstance, Float>> effects)
    {
        CompoundTag compound = new CompoundTag();

        if(!effects.isEmpty())
        {
            int i = 0;

            for(Pair<MobEffectInstance, Float> pair : effects)
            {
                CompoundTag effect = new CompoundTag();
                pair.getFirst().writeCurativeItems(effect);
                effect.putFloat(PROBABILITY, pair.getSecond());
                compound.put(String.valueOf(i), effect);
                i++;
            }
        }
        return compound;
    }

    public static List<Pair<MobEffectInstance, Float>> readEffectsFromTag(CompoundTag effectsNBT)
    {
        List<Pair<MobEffectInstance, Float>> effects = new ArrayList<>();

        if(!effectsNBT.isEmpty())
        {
            for(int i = 0; i < effectsNBT.size(); i++)
            {
                CompoundTag effect = effectsNBT.getCompound(String.valueOf(i));
                if(!effect.isEmpty())
                {
                    Pair<MobEffectInstance, Float> effectPair = Pair.of(MobEffectInstance.load(effect), effect.getFloat(PROBABILITY));
                    effects.add(effectPair);
                }
            }
        }
        return effects;
    }

    public static void writeToItemStack(ItemStack stack, CompoundTag compound)
    {

    }
}