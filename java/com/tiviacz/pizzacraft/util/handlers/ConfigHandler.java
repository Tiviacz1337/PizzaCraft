package com.tiviacz.pizzacraft.util.handlers;

import java.io.File;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler 
{
	public static Configuration config;
	
	public static int cookingTimeMargherita = 610;
	public static int cookingTimeFunghi = 610;
	public static int cookingTimeProsciutto = 610;
	public static int cookingTimeMeat = 610;
	public static int cookingTimeClassic = 610;
	public static int cookingTimeCapriciosa = 610;
	public static int cookingTimeHawaiian = 610;
	public static int cookingTimeToscana = 610;
	public static int cookingTimeRustica = 610;
	public static int cookingTimeVegetarian = 610;
	public static int cookingTimePompea = 610;
	public static int burningTimeOven = 610;
	
	public static boolean dropOlive;
	public static boolean dropBlackOlive;
	
	public static boolean dropOnionSeeds;
	public static boolean dropPepperSeeds;
	public static boolean dropPineappleSeeds;
	public static boolean dropTomatoSeeds;
	public static boolean dropCucumberSeeds;
	public static boolean dropCornSeeds;
	public static boolean dropBroccoliSeeds;
	public static boolean dropAllSeeds;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		
		String category;
		
		category = "Cooking Time";
		config.addCustomCategoryComment(category, "Cooking Time [610 == 30seconds]");
		
		cookingTimeMargherita = config.getInt("Cooking Time for Raw Pizza Margherita", category, 610, 1, 12200, "Cooking Time for Raw Pizza Margherita");
		cookingTimeFunghi = config.getInt("Cooking Time for Raw Pizza Funghi", category, 610, 1, 12200, "Cooking Time for Raw Pizza Funghi");
		cookingTimeProsciutto = config.getInt("Cooking Time for Raw Pizza Prosciutto", category, 610, 1, 12200, "Cooking Time for Raw Pizza Prosciutto");
		cookingTimeMeat = config.getInt("Cooking Time for Raw Meat Pizza", category, 610, 1, 12200, "Cooking Time for Raw Meat Pizza");
		cookingTimeClassic = config.getInt("Cooking Time for Raw Classic Pizza", category, 610, 1, 12200, "Cooking Time for Raw Classic Pizza");
		cookingTimeCapriciosa = config.getInt("Cooking Time for Raw Pizza Capriciosa", category, 610, 1, 12200, "Cooking Time for Raw Pizza Capriciosa");
		cookingTimeHawaiian = config.getInt("Cooking Time for Raw Hawaiian Pizza", category, 610, 1, 12200, "Cooking Time for Raw Hawaiian Pizza");
		cookingTimeToscana = config.getInt("Cooking Time for Raw Pizza Toscana", category, 610, 1, 12200, "Cooking Time for Raw Pizza Toscana");
		cookingTimeRustica = config.getInt("Cooking Time for Raw Pizza Rustica", category, 610, 1, 12200, "Cooking Time for Raw Pizza Rustica");
		cookingTimeVegetarian = config.getInt("Cooking Time for Raw Vegan Pizza", category, 610, 1, 12200, "Cooking Time for Raw Vegan Pizza");
		cookingTimePompea = config.getInt("Cooking Time for Raw Pizza Pompea", category, 610, 1, 12200, "Cooking Time for Raw Pizza Pompea");
		burningTimeOven = config.getInt("Burning Time for 1 Stick in Pizza Oven", category, 610, 1, 12200, "Burning Time for 1 Stick in Pizza Oven");
		
		category = "Seeds";
		config.addCustomCategoryComment(category, "Seeds drop");
		
		dropOlive = config.getBoolean("Leaves drop olives", category, true, "Leaves drop olives true/false");
		dropBlackOlive = config.getBoolean("Leaves drop black olives", category, true, "Leaves drop black olives true/false");
		dropOnionSeeds = config.getBoolean("Grass drop onion seeds", category, true, "Grass drop onion seeds true/false");
		dropPepperSeeds = config.getBoolean("Grass drop pepper seeds", category, true, "Grass drop pepper seeds true/false");
		dropPineappleSeeds = config.getBoolean("Grass drop pineapple seeds", category, true, "Grass drop pineapple seeds true/false");
		dropTomatoSeeds = config.getBoolean("Grass drop tomato seeds", category, true, "Grass drop tomato seeds true/false");
		dropCucumberSeeds = config.getBoolean("Grass drop cucumber seeds", category, true, "Grass drop cucumber seeds true/false");
		dropCornSeeds = config.getBoolean("Grass drop corn seeds", category, true, "Grass drop corn seeds true/false");
		dropBroccoliSeeds = config.getBoolean("Grass drop broccoli seeds", category, true, "Grass drop broccoli seeds true/false");
		dropAllSeeds = config.getBoolean("Grass drop seeds", category, true, "Grass drop seeds true/false");
		
		config.save();	
	}
	
	public static void registerConfig(FMLPreInitializationEvent event)
	{
		PizzaCraft.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		PizzaCraft.config.mkdirs();
		init(new File(PizzaCraft.config.getPath(), Reference.MODID + ".cfg"));
	}
}	
