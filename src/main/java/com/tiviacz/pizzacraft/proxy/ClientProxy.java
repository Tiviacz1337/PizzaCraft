package com.tiviacz.pizzacraft.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	public void preInitRegistries(FMLPreInitializationEvent event)
	{
		super.preInitRegistries(event);
	}
	
	public void initRegistries(FMLInitializationEvent event)
	{
		super.initRegistries(event);
	}
	
	public void postInitRegistries(FMLPostInitializationEvent event)
	{
		super.postInitRegistries(event);
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
}