package com.tiviacz.pizzacraft;

import java.io.File;
import java.util.List;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.proxy.CommonProxy;
import com.tiviacz.pizzacraft.util.handlers.EventsHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = PizzaCraft.MODID, name = PizzaCraft.NAME, version = PizzaCraft.VERSION)
public class PizzaCraft 
{
	public static final String MODID = "pizzacraft";
	public static final String NAME = "pizzacraft";
	public static final String VERSION = "1.2.5";
	
	public static File config;

	public static CreativeTabs PIZZACRAFTTAB = (new CreativeTabs("pizzacrafttab")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(Item.getItemFromBlock(ModBlocks.PIZZA_9));
		}
	});
	
	@Instance
	public static PizzaCraft instance;
	
	@SidedProxy(clientSide = "com.tiviacz.pizzacraft.proxy.ClientProxy", serverSide = "com.tiviacz.pizzacraft.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		EventsHandler.PreInitRegistries(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		EventsHandler.initRegistries(event);
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		EventsHandler.PostInitRegistries(event);
	}
}
