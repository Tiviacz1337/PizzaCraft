package com.tiviacz.pizzacraft.util.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventsHandler
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
				PizzaCraft.proxy.registerItemRenderer(item, 0, "inventory");
			}
		}
			
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				PizzaCraft.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
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
		    
		if(event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_BOX))
		{
			event.setBurnTime(800);
		}
		    
		if(event.getItemStack().getItem() == ModItems.PIZZA_BOARD_SHIELD || event.getItemStack().getItem() == ModItems.PIZZA_BURNT_SHIELD)
		{
		    event.setBurnTime(200);
		}
		    
		if(event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_BOARD) || event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_BURNT))
		{
			event.setBurnTime(400);
		}
	}
		
	@SubscribeEvent
	public static void checkLeaves(BlockEvent.BreakEvent event)
	{	
		int o = event.getWorld().rand.nextInt(500) + 1;
			
		if(!event.getWorld().isRemote && !event.getPlayer().capabilities.isCreativeMode)
		{
			if(ConfigHandler.dropOlives)
			{
				if(event.getState() == Blocks.LEAVES.getDefaultState() && o > 90)
				{
					event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ModItems.OLIVE)));
				}
			}
				
			if(ConfigHandler.dropBlackOlives)
			{
				if(event.getState() == Blocks.LEAVES.getDefaultState() && o < 10)
				{
					event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ModItems.BLACK_OLIVE)));
				}
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
				    if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.MILK_BOTTLE, 1)))
				    {
				        player.world.spawnEntity(new EntityItem(player.world, entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ(), new ItemStack(ModItems.MILK_BOTTLE, 1)));
				    }
				}
			}
		}
	}
}