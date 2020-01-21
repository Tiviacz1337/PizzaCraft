package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModPotions;
import com.tiviacz.pizzacraft.items.ItemPizzaShield;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class CommonEventHandler
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
	public static void onPotionRegister(RegistryEvent.Register<Potion> event)
	{
		event.getRegistry().register(ModPotions.EYE_IRRITATION_EFFECT);
	}
	
	@SubscribeEvent
	public static void onPotionTypeRegister(RegistryEvent.Register<PotionType> event)
	{
		event.getRegistry().register(ModPotions.EYE_IRRITATION_POTION);
		event.getRegistry().register(ModPotions.LONG_EYE_IRRITATION_POTION);
	}
	
/*	@SubscribeEvent
	public static void onVillagerProfessionRegister(RegistryEvent.Register<VillagerProfession> event)
	{
	//	ModVillagers.init();
		
		if(ConfigHandler.enableChefVillager)
		{
			event.getRegistry().register(ModVillagers.chef);
		}
	} */

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{			
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModel();
			}
		}
			
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModel();
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
		Item item = event.getItemStack().getItem();
		
		if(item == ModItems.PAPER_MASS)
		{
			event.setBurnTime(50);
		}
			
		if(item == ModItems.CARDBOARD || item == ModItems.RED_CLOTH)
		{
			event.setBurnTime(100);
		}
		    
		if(item == Item.getItemFromBlock(ModBlocks.PIZZA_BOX))
		{
			event.setBurnTime(800);
		}
		    
		if(item == ModItems.PIZZA_BOARD_SHIELD || item == ModItems.PIZZA_BURNT_SHIELD)
		{
		    event.setBurnTime(200);
		}
		    
		if(item == Item.getItemFromBlock(ModBlocks.PIZZA_BOARD) || item == Item.getItemFromBlock(ModBlocks.PIZZA_BURNT))
		{
			event.setBurnTime(400);
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
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		if(event.getEntityLiving().isPotionActive(ModPotions.EYE_IRRITATION_EFFECT))
		{
			Vec3d eyesPosition = event.getEntityLiving().getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks());
			Vec3d normalizedVec = event.getEntityLiving().getLookVec().normalize().scale(0.25D);
			Vec3d newVec = eyesPosition.add(normalizedVec);
			
			if(event.getEntityLiving().world instanceof WorldServer)
			{
				WorldServer worldServer = (WorldServer)event.getEntityLiving().world;
				worldServer.spawnParticle(EnumParticleTypes.WATER_SPLASH, newVec.x, newVec.y, newVec.z, 3, 0, 0, 0, 1, new int[] {1});
			}
		}
	}
	
	@SubscribeEvent
	public static void onShieldBlock(LivingAttackEvent event)
	{
		ItemStack activeItemStack;
		EntityPlayer player;
		
		if(!(event.getEntityLiving() instanceof EntityPlayer)) 
		{
			return;
		}
		
		player = (EntityPlayer)event.getEntityLiving();
		
		if(player.getActiveItemStack() == null) 
		{
			return;
		}
		
		activeItemStack = player.getActiveItemStack();
		float damage = event.getAmount();

		if(damage > 0.0F && activeItemStack != null && activeItemStack.getItem() instanceof ItemPizzaShield) 
		{
			int i = 1 + MathHelper.floor(damage);
			activeItemStack.damageItem(i, player);

			if(activeItemStack.getCount() <= 0) 
			{
				EnumHand enumhand = player.getActiveHand();
				ForgeEventFactory.onPlayerDestroyItem(player, activeItemStack, enumhand);

				if(enumhand == EnumHand.MAIN_HAND) 
				{
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
				}
				else 
				{
					player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
				}

				activeItemStack = ItemStack.EMPTY;
				
				if(player.world.isRemote) 
				{
					player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerBlockColourHandlers(final ColorHandlerEvent.Block event) 
	{
		final BlockColors blockColors = event.getBlockColors();
		
		final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> 
		{
			if (blockAccess != null && pos != null) {
				return BiomeColorHelper.getGrassColorAtPos(blockAccess, pos);
			}

			return ColorizerGrass.getGrassColor(0.5D, 1.0D);
		};

		blockColors.registerBlockColorHandler(grassColourHandler, ModBlocks.OLIVE_LEAVES);
		blockColors.registerBlockColorHandler(grassColourHandler, ModBlocks.OLIVE_LEAVES_GROWING);
	} 
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) 
	{
		final BlockColors blockColors = event.getBlockColors();
		final ItemColors itemColors = event.getItemColors();

		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> 
		{
			final IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			return blockColors.colorMultiplier(state, null, null, tintIndex);
		};

		itemColors.registerItemColorHandler(itemBlockColourHandler, ModBlocks.OLIVE_LEAVES);
		itemColors.registerItemColorHandler(itemBlockColourHandler, ModBlocks.OLIVE_LEAVES_GROWING);
	}
}