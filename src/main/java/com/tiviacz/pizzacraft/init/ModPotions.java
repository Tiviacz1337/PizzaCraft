package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.potion.EyeIrritationPotion;

import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;

public class ModPotions 
{
	public static final Potion EYE_IRRITATION_EFFECT = new EyeIrritationPotion();
	
	public static final PotionType EYE_IRRITATION_POTION = new PotionType("eye_irritation", new PotionEffect[] { new PotionEffect(EYE_IRRITATION_EFFECT, 2400)}).setRegistryName("eye_irritation");
	public static final PotionType LONG_EYE_IRRITATION_POTION = new PotionType("eye_irritation", new PotionEffect[] { new PotionEffect(EYE_IRRITATION_EFFECT, 4800)}).setRegistryName("long_eye_irritation");
	
	public static void registerPotionMixes()
	{
		PotionHelper.addMix(EYE_IRRITATION_POTION, ModItems.ONION, LONG_EYE_IRRITATION_POTION);
		PotionHelper.addMix(PotionTypes.AWKWARD, ModItems.ONION_SLICE, EYE_IRRITATION_POTION);
	}
}