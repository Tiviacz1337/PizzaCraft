package com.tiviacz.pizzacraft.util.handlers;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModSmeltery;
import com.tiviacz.pizzacraft.init.OreDictInit;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class RegistryHandler
{
		@SubscribeEvent
		public static void onItemRegister(RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
		}
		
		@SubscribeEvent
		public static void onBlockRegister(RegistryEvent.Register<Block> event)
		{
			event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		}
		
		@SubscribeEvent
		public static void onModelRegister(ModelRegistryEvent event)
		{
			for(Item item : ModItems.ITEMS)
			{
				if(item instanceof IHasModel)
				{
					((IHasModel)item).registerModels();
				}
			}
			for(Block block : ModBlocks.BLOCKS)
			{
				if(block instanceof IHasModel)
				{
					((IHasModel)block).registerModels();
				}
			}
		}
		
		@SubscribeEvent
		public static void furnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event)
		{
			if(event.getItemStack().getItem() == ModItems.paper_mass)
			{
				event.setBurnTime(50);
			}
			
		    if(event.getItemStack().getItem() == ModItems.cardboard || event.getItemStack().getItem() == ModItems.red_cloth)
		    {
		    	event.setBurnTime(100);
		    }
		    
		    if(event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.pizza_box) || event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.pizza_board))
		    {
		    	event.setBurnTime(800);
		    }
		}
		
		public static void PreInitRegistries(FMLPreInitializationEvent event)
		{
			ConfigHandler.registerConfig(event);
			
			if(ConfigHandler.dropOnionSeeds)
			{
				SeedsDropsHandler.PreInitOnion();
			}
			
			if(ConfigHandler.dropPepperSeeds)
			{
				SeedsDropsHandler.PreInitPepper();
			}
			
			if(ConfigHandler.dropPineappleSeeds)
			{
				SeedsDropsHandler.PreInitPineapple();
			}
			
			if(ConfigHandler.dropTomatoSeeds)
			{
				SeedsDropsHandler.PreInitTomato();
			}
			
			if(ConfigHandler.dropCucumberSeeds)
			{
				SeedsDropsHandler.PreInitCucumber();
			}
			
			if(ConfigHandler.dropCornSeeds)
			{
				SeedsDropsHandler.PreInitCorn();
			}
			
			if(ConfigHandler.dropBroccoliSeeds)
			{
				SeedsDropsHandler.PreInitBroccoli();
			}
		}
		
		public static void initRegistries(FMLInitializationEvent event)
		{
			ModSmeltery.init();			
			TileEntityHandler.registerTileEntity();
			OreDictInit.registerOres();
		//	NetworkRegistry.INSTANCE.registerGuiHandler(PizzaCraft.instance, new GuiHandler());
		}
		
		public static void PostInitRegistries(FMLPostInitializationEvent event)
		{
			
		}
}
