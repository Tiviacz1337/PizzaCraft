package com.tiviacz.pizzacraft.proxy;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blocks.trees.BlockLeavesBase;
import com.tiviacz.pizzacraft.handlers.ConfigHandler;
import com.tiviacz.pizzacraft.handlers.GuiHandler;
import com.tiviacz.pizzacraft.handlers.SoundHandler;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModPotions;
import com.tiviacz.pizzacraft.init.ModSmeltery;
import com.tiviacz.pizzacraft.init.OreDictInit;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;
import com.tiviacz.pizzacraft.tileentity.TileEntityBurningPizzaOven;
import com.tiviacz.pizzacraft.tileentity.TileEntityChoppingBoard;
import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizza;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizzaBag;
import com.tiviacz.pizzacraft.tileentity.TileEntityRawPizza;
import com.tiviacz.pizzacraft.world.gen.WorldGenTrees;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	public void preInitRegistries(FMLPreInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(PizzaCraft.instance, new GuiHandler());
		ModPotions.registerPotionMixes();
		registerWorldGen();
		
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
	
	public void initRegistries(FMLInitializationEvent event)
	{
		//Added container item for potion for crafting purposes
		PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER).getItem().setContainerItem(Items.GLASS_BOTTLE);
		
		ModSmeltery.initRecipes();
		registerTileEntity();
		registerCustomRecipes();
		OreDictInit.registerOres();
		SoundHandler.registerSounds();
	//	ModVillagers.init();
	}
	
	public void postInitRegistries(FMLPostInitializationEvent event)
	{
		
	}
	
	public void registerItemRenderer(Item item, int meta, String id)
	{
		
	}
	
    public void setGraphicsLevel(BlockLeavesBase block)
    {

    }
	
	public static void registerWorldGen()
	{
		if(ConfigHandler.enableTreesGeneration)
		{
			GameRegistry.registerWorldGenerator(new WorldGenTrees(), 0);
		}
	}
	
	public static void registerTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntityBurningPizzaOven.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityBurningPizzaOven"));
		GameRegistry.registerTileEntity(TileEntityPizza.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityPizza"));
		GameRegistry.registerTileEntity(TileEntityRawPizza.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityRawPizza"));
		GameRegistry.registerTileEntity(TileEntityChoppingBoard.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityChoppingBoard"));
		GameRegistry.registerTileEntity(TileEntityMortarAndPestle.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityMortarAndPestle"));
		GameRegistry.registerTileEntity(TileEntityBakeware.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityBakeware"));
		GameRegistry.registerTileEntity(TileEntityPizzaBag.class, new ResourceLocation(PizzaCraft.MODID, "TileEntityPizzaBag"));
	}
	
	public static void registerCustomRecipes()
	{
		GameRegistry.addShapedRecipe(new ResourceLocation(PizzaCraft.MODID, "dough"), new ResourceLocation("misc"), new ItemStack(ModBlocks.DOUGH), new Object[] 
		{
			" A ", 
			"CBC", 
			"XXX", 
			'A', "foodOliveoil",
			'C', PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
			'B', new ItemStack(Items.SUGAR),
			'X', "foodFlour"
		});
	}
}