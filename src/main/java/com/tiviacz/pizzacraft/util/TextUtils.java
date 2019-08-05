package com.tiviacz.pizzacraft.util;

import com.tiviacz.pizzacraft.PizzaCraft;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class TextUtils 
{
	public static ResourceLocation setResourceLocation(String path) 
    {
        return new ResourceLocation(PizzaCraft.MODID, path);
    }
	
	public static String setLocation(String path) 
    {
        return String.format("%s:%s", PizzaCraft.MODID, path);
    }
	
	public static String translatedText(String key, Object... args) 
    {
        return translate(key, args).getFormattedText();
    }
    
    public static TextComponentTranslation translate(String key, Object... args) 
    {
        return new TextComponentTranslation(key, args);
    }
}