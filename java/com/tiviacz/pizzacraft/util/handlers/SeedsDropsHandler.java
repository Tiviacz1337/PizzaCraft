package com.tiviacz.pizzacraft.util.handlers;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid="pizzacraft")
public class SeedsDropsHandler 
{	
	@SubscribeEvent
	public static void checkLeaves(BlockEvent.HarvestDropsEvent event)
	{
		Random chance = new Random();		
		int o = chance.nextInt(100) + 1;
		
		if(ConfigHandler.dropOlive)
		{
			if(event.getState() == Blocks.LEAVES.getDefaultState() && o == 25)
			{
				event.getDrops().add(new ItemStack(ModItems.olive));
			}
		}
		if(ConfigHandler.dropBlackOlive)
		{
			if(event.getState() == Blocks.LEAVES.getDefaultState() && o == 75)
			{
				event.getDrops().add(new ItemStack(ModItems.black_olive));
			}
		}
	}
	
		public static void PreInitOnion()
		{	
			forgeOnionSeeds();
		}
		
		public static void PreInitPepper()
		{
			forgePepperSeeds();
		}
		
		public static void PreInitPineapple()
		{
			forgePineappleSeeds();
		}
		
		public static void PreInitTomato()
		{ 
			forgeTomatoSeeds();
		}
		
		public static void PreInitCucumber()
		{
			forgeCucumberSeeds();
		}
		
		public static void PreInitCorn()
		{
			forgeCornSeeds();
		}
		
		public static void PreInitBroccoli()
		{
			forgeBroccoliSeeds();
		}

		private static void forgeOnionSeeds() 
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_onion), 1);
		}
		
		private static void forgePepperSeeds()
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_pepper), 1);
		}
		
		private static void forgePineappleSeeds()
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_pineapple), 1);
		}
		
		private static void forgeTomatoSeeds()
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_tomato), 1);
		}
		
		private static void forgeCucumberSeeds()
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_cucumber), 1);
		}
		
		private static void forgeCornSeeds()
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_corn), 1);
		}
		
		private static void forgeBroccoliSeeds()
		{
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_broccoli), 1);
		}	
}	
