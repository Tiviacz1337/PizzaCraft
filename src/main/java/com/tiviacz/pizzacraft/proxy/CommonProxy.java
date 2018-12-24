package com.tiviacz.pizzacraft.proxy;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModSmeltery;
import com.tiviacz.pizzacraft.init.OreDictInit;
import com.tiviacz.pizzacraft.util.handlers.ConfigHandler;
import com.tiviacz.pizzacraft.util.handlers.TileEntityHandler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	public void preInitRegistries(FMLPreInitializationEvent event)
	{
		if(ConfigHandler.dropAllSeeds)
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_ONION), 1);
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_PEPPER), 1);
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_PINEAPPLE), 1);
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_TOMATO), 1);
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_CUCUMBER), 1);
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_CORN), 1);
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.SEED_BROCCOLI), 1);
		}  
	}
	
	public void initRegistries(FMLInitializationEvent event)
	{
		ModSmeltery.initRecipes();
		TileEntityHandler.registerTileEntity();
		OreDictInit.registerOres();
	}
	
	public void postInitRegistries(FMLPostInitializationEvent event)
	{
		
	}
	
	public void registerItemRenderer(Item item, int meta, String id)
	{
		
	}
}