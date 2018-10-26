package com.tiviacz.pizzacraft.util.handlers;

import java.util.Random;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModSmeltery;
import com.tiviacz.pizzacraft.init.OreDictInit;
import com.tiviacz.pizzacraft.objects.items.ItemMilkBottle;
import com.tiviacz.pizzacraft.proxy.ClientProxy;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class EventsHandler
{
		public static void PreInitRegistries(FMLPreInitializationEvent event)
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
		
		public static void initRegistries(FMLInitializationEvent event)
		{
			ModSmeltery.init();			
			TileEntityHandler.registerTileEntity();
			OreDictInit.registerOres();
		}
		
		public static void PostInitRegistries(FMLPostInitializationEvent event){}
	
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
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if(event.getModID().equals(PizzaCraft.MODID))
            {
                ConfigManager.sync(PizzaCraft.MODID, Config.Type.INSTANCE);
            }
        }
		
		@SubscribeEvent
		public static void furnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event)
		{
			if(event.getItemStack().getItem() == ModItems.PAPER_MASS)
			{
				event.setBurnTime(50);
			}
			
		    if(event.getItemStack().getItem() == ModItems.CARDBOARD || event.getItemStack().getItem() == ModItems.RED_CLOTH)
		    {
		    	event.setBurnTime(100);
		    }
		    
		    if(event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_BOX) || event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_BOARD))
		    {
		    	event.setBurnTime(800);
		    }
		}
		
		@SubscribeEvent
		public static void checkLeaves(BlockEvent.HarvestDropsEvent event)
		{
			Random chance = new Random();		
			int o = chance.nextInt(100) + 1;
			
			if(ConfigHandler.dropOlives)
			{
				if(event.getState() == Blocks.LEAVES.getDefaultState() && o > 90)
				{
					event.getDrops().add(new ItemStack(ModItems.OLIVE));
				}
			}
			if(ConfigHandler.dropBlackOlives)
			{
				if(event.getState() == Blocks.LEAVES.getDefaultState() && o < 10)
				{
					event.getDrops().add(new ItemStack(ModItems.BLACK_OLIVE));
				}
			}
		}
		
		@SubscribeEvent
		public static void getMilk(PlayerInteractEvent.EntityInteract event)
		{
			Entity entity = event.getTarget();
			EntityPlayer player = event.getEntityPlayer();
			ItemStack helditem = player.getHeldItemMainhand();
			
			if(entity instanceof EntityCow)
			{
				if(helditem.getItem() == Items.GLASS_BOTTLE)
			    {
					player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
					
					if(!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND)
					{
						helditem.shrink(1);   
				        player.inventory.addItemStackToInventory(new ItemStack(ModItems.MILK_BOTTLE, 1));
					}
			    }
			}
		}
}
