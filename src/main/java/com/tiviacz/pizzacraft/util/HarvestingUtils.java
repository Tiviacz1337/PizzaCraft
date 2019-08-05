package com.tiviacz.pizzacraft.util;

import java.util.Iterator;
import java.util.List;

import com.tiviacz.pizzacraft.blocks.plants.CornPlantBottom;
import com.tiviacz.pizzacraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class HarvestingUtils 
{
	public static void harvestPlant(IBlockState blockState, EntityPlayer player, World world, BlockPos pos) 
	{
		BlockCrops crops = (BlockCrops)blockState.getBlock();
		ItemStack stack = player.getHeldItemMainhand();
		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

		List<ItemStack> drops = crops.getDrops(world, pos, blockState, fortune);

		Item seedItem = crops.getItemDropped(blockState, world.rand, fortune);
		
		if(seedItem != null)
		{
			for(Iterator<ItemStack> iterator = drops.iterator(); iterator.hasNext();) 
			{
				ItemStack drop = iterator.next();

				if(drop.getItem() != seedItem) 
				{
					iterator.remove();
					break;
				}
			}
		}

		ForgeEventFactory.fireBlockHarvesting(drops, world, pos, blockState, fortune, 1.0F, false, player);

		for(ItemStack drop : drops) 
		{
			Block.spawnAsEntity(world, pos, drop);
		}
		
		if(blockState.getBlock() == ModBlocks.CORN_PLANT_TOP)
		{
			IBlockState bottom = world.getBlockState(pos.down());
			CornPlantBottom corn = (CornPlantBottom)bottom.getBlock();
			
			world.setBlockToAir(pos);
			world.setBlockState(pos.down(), corn.withAge(0));
		}
		else
		{
			world.setBlockState(pos, crops.withAge(0));
		}
	}
}
