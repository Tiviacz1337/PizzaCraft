package com.tiviacz.pizzacraft.common;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PizzaCraftCreativeTab extends CreativeTabs
{
	public PizzaCraftCreativeTab(String label) 
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(ModBlocks.PIZZA_9);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
		add(list, ModBlocks.OLIVE_LOG);
		add(list, ModBlocks.OLIVE_PLANKS);
		add(list, ModBlocks.OLIVE_LEAVES);
		add(list, ModBlocks.OLIVE_LEAVES_GROWING);
		add(list, ModBlocks.OLIVE_SAPLING);
		
		add(list, ModItems.CARDBOARD);
        add(list, ModItems.PAPER_MASS);
        add(list, ModItems.RED_CLOTH);
        add(list, ModItems.WHITE_CLOTH);
        add(list, ModItems.KNIFE);
        add(list, ModItems.PEEL);
        
        add(list, ModItems.PIZZA_DELIVER_HAT);
        add(list, ModItems.PIZZA_DELIVER_SHIRT);
        add(list, ModItems.PIZZA_DELIVER_LEGGINGS);
        add(list, ModItems.PIZZA_DELIVER_BOOTS);
        
        add(list, ModItems.CHEF_HAT);
        add(list, ModItems.CHEF_SHIRT);
        add(list, ModItems.CHEF_LEGGINGS);
        add(list, ModItems.CHEF_BOOTS);
        
        add(list, ModItems.PIZZA_BOARD_SHIELD);
        add(list, ModItems.PIZZA_BURNT_SHIELD);
        
        add(list, ModBlocks.PIZZA_OVEN);
        add(list, ModBlocks.BURNING_PIZZA_OVEN);
        add(list, ModBlocks.BAKEWARE);
        add(list, ModBlocks.CHOPPING_BOARD);
        add(list, ModBlocks.MORTAR_AND_PESTLE);
        
        add(list, ModItems.HAM);
        add(list, ModItems.CHEESE);
        add(list, ModBlocks.DOUGH);
        add(list, ModItems.PIZZA_DOUGH);
        add(list, ModItems.FLOUR);
        add(list, ModItems.FLOUR_CORN);
        add(list, ModItems.BLACK_OLIVE);
        add(list, ModItems.BROCCOLI);
        add(list, ModItems.CORN);
        add(list, ModItems.CUCUMBER);
        add(list, ModItems.CUCUMBER_SLICE);
        add(list, ModItems.OLIVE);
        add(list, ModItems.ONION);
        add(list, ModItems.ONION_SLICE);
        add(list, ModItems.PEPPER);
        add(list, ModItems.PINEAPPLE);
        add(list, ModItems.TOMATO);
        add(list, ModItems.TOMATO_SLICE);
        add(list, ModItems.MILK_BOTTLE);
        add(list, ModItems.OLIVE_OIL);
        
        add(list, ModItems.SEED_BROCCOLI);
        add(list, ModItems.SEED_CORN);
        add(list, ModItems.SEED_CUCUMBER);
        add(list, ModItems.SEED_ONION);
        add(list, ModItems.SEED_PEPPER);
        add(list, ModItems.SEED_PINEAPPLE);
        add(list, ModItems.SEED_TOMATO);
        
        add(list, ModItems.SLICE_0);
        add(list, ModItems.SLICE_1);
        add(list, ModItems.SLICE_2);
        add(list, ModItems.SLICE_3);
        add(list, ModItems.SLICE_4);
        add(list, ModItems.SLICE_5);
        add(list, ModItems.SLICE_6);
        add(list, ModItems.SLICE_7);
        add(list, ModItems.SLICE_8);
        add(list, ModItems.SLICE_9);
        add(list, ModItems.SLICE_10);
        
        add(list, ModBlocks.RAW_PIZZA_0);
        add(list, ModBlocks.RAW_PIZZA_1);
        add(list, ModBlocks.RAW_PIZZA_2);
        add(list, ModBlocks.RAW_PIZZA_3);
        add(list, ModBlocks.RAW_PIZZA_4);
        add(list, ModBlocks.RAW_PIZZA_5);
        add(list, ModBlocks.RAW_PIZZA_6);
        add(list, ModBlocks.RAW_PIZZA_7);
        add(list, ModBlocks.RAW_PIZZA_8);
        add(list, ModBlocks.RAW_PIZZA_9);
        add(list, ModBlocks.RAW_PIZZA_10);
        
        add(list, ModBlocks.PIZZA_0);
        add(list, ModBlocks.PIZZA_1);
        add(list, ModBlocks.PIZZA_2);
        add(list, ModBlocks.PIZZA_3);
        add(list, ModBlocks.PIZZA_4);
        add(list, ModBlocks.PIZZA_5);
        add(list, ModBlocks.PIZZA_6);
        add(list, ModBlocks.PIZZA_7);
        add(list, ModBlocks.PIZZA_8);
        add(list, ModBlocks.PIZZA_9);
        add(list, ModBlocks.PIZZA_10);
        add(list, ModBlocks.PIZZA_BURNT);
        
        add(list, ModBlocks.PIZZA_BOARD);
        add(list, ModBlocks.PIZZA_BOARD_0);
        add(list, ModBlocks.PIZZA_BOARD_1);
        add(list, ModBlocks.PIZZA_BOARD_2);
        add(list, ModBlocks.PIZZA_BOARD_3);
        add(list, ModBlocks.PIZZA_BOARD_4);
        add(list, ModBlocks.PIZZA_BOARD_5);
        add(list, ModBlocks.PIZZA_BOARD_6);
        add(list, ModBlocks.PIZZA_BOARD_7);
        add(list, ModBlocks.PIZZA_BOARD_8);
        add(list, ModBlocks.PIZZA_BOARD_9);
        add(list, ModBlocks.PIZZA_BOARD_10);
        
        add(list, ModBlocks.PIZZA_BAG);
        add(list, ModBlocks.PIZZA_BOX);
        add(list, ModBlocks.PIZZA_BOX_0);
        add(list, ModBlocks.PIZZA_BOX_1);
        add(list, ModBlocks.PIZZA_BOX_2);
        add(list, ModBlocks.PIZZA_BOX_3);
        add(list, ModBlocks.PIZZA_BOX_4);
        add(list, ModBlocks.PIZZA_BOX_5);
        add(list, ModBlocks.PIZZA_BOX_6);
        add(list, ModBlocks.PIZZA_BOX_7);
        add(list, ModBlocks.PIZZA_BOX_8);
        add(list, ModBlocks.PIZZA_BOX_9);
        add(list, ModBlocks.PIZZA_BOX_10);
    }
	
	public void add(NonNullList<ItemStack> list, Item item)
	{
		list.add(new ItemStack(item));
	}
	
	public void add(NonNullList<ItemStack> list, Block block)
	{
		list.add(new ItemStack(block));
	}
	
	public void add(NonNullList<ItemStack> list, ItemStack stack)
	{
		list.add(stack);
	}
}