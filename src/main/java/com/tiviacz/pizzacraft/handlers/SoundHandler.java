package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler 
{
	public static SoundEvent PIZZA_SIZZLING;
	
	public static void registerSounds()
	{
		PIZZA_SIZZLING = registerSound("pizza_sizzling");
	}
	
	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(PizzaCraft.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}