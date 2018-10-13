package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.util.handlers.ConfigHandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModSmeltery 
{
			
	 public static void init()
	 {
		 if(ConfigHandler.furnaceCooking)
		 {
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_0), new ItemStack(ModBlocks.PIZZA_0), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_1), new ItemStack(ModBlocks.PIZZA_1), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_2), new ItemStack(ModBlocks.PIZZA_2), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_3), new ItemStack(ModBlocks.PIZZA_3), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_4), new ItemStack(ModBlocks.PIZZA_4), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_5), new ItemStack(ModBlocks.PIZZA_5), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_6), new ItemStack(ModBlocks.PIZZA_6), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_7), new ItemStack(ModBlocks.PIZZA_7), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_8), new ItemStack(ModBlocks.PIZZA_8), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_9), new ItemStack(ModBlocks.PIZZA_9), 0.1F);
			 GameRegistry.addSmelting(new ItemStack(ModBlocks.RAW_PIZZA_10), new ItemStack(ModBlocks.PIZZA_10), 0.1F); 
		 }
	 }
}

