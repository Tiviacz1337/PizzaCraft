package com.tiviacz.pizzacraft.util.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.tileentity.TileEntityBurningPizzaOven;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizza;
import com.tiviacz.pizzacraft.tileentity.TileEntityRawPizza;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler 
{
	public static void registerTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntityBurningPizzaOven.class, new ResourceLocation(PizzaCraft.MODID + ":TileEntityBurningPizzaOven"));
		GameRegistry.registerTileEntity(TileEntityPizza.class, new ResourceLocation(PizzaCraft.MODID + ":TileEntityPizza"));
		GameRegistry.registerTileEntity(TileEntityRawPizza.class, new ResourceLocation(PizzaCraft.MODID + ":TileEntityRawPizza"));
	}
}
