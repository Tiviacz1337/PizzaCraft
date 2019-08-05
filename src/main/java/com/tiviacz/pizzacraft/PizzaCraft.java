package com.tiviacz.pizzacraft;

import com.tiviacz.pizzacraft.common.PizzaCraftCreativeTab;
import com.tiviacz.pizzacraft.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PizzaCraft.MODID, name = PizzaCraft.NAME, version = PizzaCraft.VERSION, updateJSON = "https://gist.githubusercontent.com/Tiviacz1337/b916e3981957f1e6f2de99ea0aa328fa/raw/4a1b4e905a3a8fd10922e9ccd97abac3f282e222/PizzaCraftUpdateJSON.json")
public class PizzaCraft 
{
	public static final String MODID = "pizzacraft";
	public static final String NAME = "pizzacraft";
	public static final String VERSION = "2.0.16.16";

	public static CreativeTabs PIZZACRAFTTAB = new PizzaCraftCreativeTab("pizzacrafttab");
	
	@Instance
	public static PizzaCraft instance;
	
	@SidedProxy(clientSide = "com.tiviacz.pizzacraft.proxy.ClientProxy", serverSide = "com.tiviacz.pizzacraft.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		proxy.preInitRegistries(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.initRegistries(event);
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		proxy.postInitRegistries(event);
	}
}