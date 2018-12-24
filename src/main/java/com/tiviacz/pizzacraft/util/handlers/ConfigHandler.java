package com.tiviacz.pizzacraft.util.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

@Config(modid=PizzaCraft.MODID)
public class ConfigHandler 
{
	@Config.Comment("Grass drop seeds")
	public static boolean dropAllSeeds = true;
	
	@Config.Comment("Leaves drop olives")
	public static boolean dropOlives = true;
	
	@Config.Comment("Leaves drop black olives")
	public static boolean dropBlackOlives = true;
	
	@Config.Comment("Pizza can be cooked in furnace")
	public static boolean furnaceCooking = false;
	
	@Config.Comment("Burning Time for 1 Stick in Pizza Oven")
	public static int burningTimeOven = 800;
	
	@Config.Comment("Cooking Time for Raw Pizza Margherita")
	public static int cookingTimeMargherita = 610;
	
	@Config.Comment("Cooking Time for Raw Pizza Funghi")
	public static int cookingTimeFunghi = 610;
	
	@Config.Comment("Cooking Time for Raw Pizza Prosciutto")
	public static int cookingTimeProsciutto = 610;
	
	@Config.Comment("Cooking Time for Raw Meat Pizza")
	public static int cookingTimeMeat = 610;
	
	@Config.Comment("Cooking Time for Raw Classic Pizza")
	public static int cookingTimeClassic = 610;
	
	@Config.Comment("Cooking Time for Raw Pizza Capriciosa")
	public static int cookingTimeCapriciosa = 610;
	
	@Config.Comment("Cooking Time for Raw Hawaiian Pizza")
	public static int cookingTimeHawaiian = 610;
	
	@Config.Comment("Cooking Time for Raw Pizza Toscana")
	public static int cookingTimeToscana = 610;
	
	@Config.Comment("Cooking Time for Raw Pizza Rustica")
	public static int cookingTimeRustica = 610;
	
	@Config.Comment("Cooking Time for Raw Vegan Pizza")
	public static int cookingTimeVegan = 610;
	
	@Config.Comment("Cooking Time for Raw Pizza Pompea")
	public static int cookingTimePompea = 610;
}