package com.tiviacz.pizzacraft;

import java.io.File;
import java.util.List;

import com.tiviacz.pizzacraft.proxy.CommonProxy;
import com.tiviacz.pizzacraft.tab.PizzaCraftTab;
import com.tiviacz.pizzacraft.util.Reference;
import com.tiviacz.pizzacraft.util.handlers.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class PizzaCraft 
{
	
	public static File config;

	public static CreativeTabs PizzaCraftTab = new PizzaCraftTab();
	
	@Instance
	public static PizzaCraft instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.PreInitRegistries(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		RegistryHandler.initRegistries(event);
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		RegistryHandler.PostInitRegistries(event);
	}
}
