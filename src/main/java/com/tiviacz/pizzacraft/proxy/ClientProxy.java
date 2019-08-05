package com.tiviacz.pizzacraft.proxy;

import com.tiviacz.pizzacraft.blocks.trees.BlockLeavesBase;
import com.tiviacz.pizzacraft.client.RendererBakeware;
import com.tiviacz.pizzacraft.client.RendererChoppingBoard;
import com.tiviacz.pizzacraft.client.RendererMortarAndPestle;
import com.tiviacz.pizzacraft.handlers.ConfigHandler;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;
import com.tiviacz.pizzacraft.tileentity.TileEntityChoppingBoard;
import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
		registerRenders();
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
	
	public void setGraphicsLevel(BlockLeavesBase block)
    {
    	block.setGraphicsLevel(ConfigHandler.enableFancyLeaves);
    }
	
	public void registerRenders()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBakeware.class, new RendererBakeware());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChoppingBoard.class, new RendererChoppingBoard());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMortarAndPestle.class, new RendererMortarAndPestle());
	}
}