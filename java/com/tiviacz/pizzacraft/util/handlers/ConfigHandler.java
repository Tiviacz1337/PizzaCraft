package com.tiviacz.pizzacraft.util.handlers;

import java.io.File;

import com.tiviacz.pizzacraft.PizzaCraft;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler 
{
	public static Configuration config;
	
	public static int cookingTimeMargherita;
	public static int cookingTimeFunghi;
	public static int cookingTimeProsciutto;
	public static int cookingTimeMeat;
	public static int cookingTimeClassic;
	public static int cookingTimeCapriciosa;
	public static int cookingTimeHawaiian;
	public static int cookingTimeToscana;
	public static int cookingTimeRustica;
	public static int cookingTimeVegetarian;
	public static int cookingTimePompea;
	public static int burningTimeOven;
	
	public static boolean dropOlive;
	public static boolean dropBlackOlive;
	public static boolean dropAllSeeds;
	
	public static boolean furnaceCooking;
	
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
		burningTimeOven = config.getInt("Burning Time for 1 Stick in Pizza Oven", category, 800, 1, 12200, "Burning Time for 1 Stick in Pizza Oven");
		
		category = "Seeds";
		config.addCustomCategoryComment(category, "Seeds drop");
		
		dropOlive = config.getBoolean("Leaves drop olives", category, true, "Leaves drop olives true/false");
		dropBlackOlive = config.getBoolean("Leaves drop black olives", category, true, "Leaves drop black olives true/false");
		dropAllSeeds = config.getBoolean("Grass drop seeds", category, true, "Grass drop seeds true/false");
		
		category = "Furnace";
		config.addCustomCategoryComment(category, "Pizza Cooking");
		
		furnaceCooking = config.getBoolean("Pizza can be cooked in furnace", category, false, "Pizza can be cooked in furnace true/false");
		
		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event)
	{
		PizzaCraft.config = new File(event.getModConfigurationDirectory() + "/" + PizzaCraft.MODID);
		PizzaCraft.config.mkdirs();
		init(new File(PizzaCraft.config.getPath(), PizzaCraft.MODID + ".cfg"));
	}
}	
