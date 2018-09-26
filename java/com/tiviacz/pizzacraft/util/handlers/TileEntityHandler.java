package com.tiviacz.pizzacraft.util.handlers;

import com.tiviacz.pizzacraft.tileentity.TileEntityBurningPizzaOven;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizza;
import com.tiviacz.pizzacraft.tileentity.TileEntityRawPizza;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;

public class TileEntityHandler 
{
	public static void registerTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntityBurningPizzaOven.class, new ResourceLocation(Reference.MODID + ":TileEntityBurningPizzaOven"));
		GameRegistry.registerTileEntity(TileEntityPizza.class, new ResourceLocation(Reference.MODID + ":TileEntityPizza"));
		GameRegistry.registerTileEntity(TileEntityRawPizza.class, new ResourceLocation(Reference.MODID + ":TileEntityRawPizza"));
	}
}
