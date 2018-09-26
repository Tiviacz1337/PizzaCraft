package com.tiviacz.pizzacraft.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModSmeltery 
{
			
	 public static void init()
	    {
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_0), new ItemStack(ModBlocks.pizza_0), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_1), new ItemStack(ModBlocks.pizza_1), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_2), new ItemStack(ModBlocks.pizza_2), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_3), new ItemStack(ModBlocks.pizza_3), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_4), new ItemStack(ModBlocks.pizza_4), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_5), new ItemStack(ModBlocks.pizza_5), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_6), new ItemStack(ModBlocks.pizza_6), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_7), new ItemStack(ModBlocks.pizza_7), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_8), new ItemStack(ModBlocks.pizza_8), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_9), new ItemStack(ModBlocks.pizza_9), 0.1F);
	        GameRegistry.addSmelting(new ItemStack(ModBlocks.raw_pizza_10), new ItemStack(ModBlocks.pizza_10), 0.1F);
	    }
}

