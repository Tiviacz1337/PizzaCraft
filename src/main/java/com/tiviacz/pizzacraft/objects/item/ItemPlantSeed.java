package com.tiviacz.pizzacraft.objects.item;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.ItemBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemPlantSeed extends ItemBase implements IPlantable
{
	private int type;
	
	public ItemPlantSeed(String name, int type)
	{
		super(name);
		this.type = type;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{		
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state = worldIn.getBlockState(pos);
		
		if(facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
		{
			worldIn.setBlockState(pos.up(), this.getType(), 3);
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		
		else return EnumActionResult.FAIL;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}
	
	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return this.getType();
	}
	
	public IBlockState getType()
	{
		if(type == 0)
		{
			return ModBlocks.ONION_PLANT.getDefaultState();
		}
		if(type == 1)
		{
			return ModBlocks.PEPPER_PLANT.getDefaultState();
		}
		if(type == 2)
		{
			return ModBlocks.PINEAPPLE_PLANT.getDefaultState();
		}
		if(type == 3)
		{
			return ModBlocks.TOMATO_PLANT.getDefaultState();
		}
		if(type == 4)
		{
			return ModBlocks.CUCUMBER_PLANT.getDefaultState();
		}
		if(type == 5)
		{
			return ModBlocks.CORN_PLANT.getDefaultState();
		}
		if(type == 6)
		{
			return ModBlocks.BROCCOLI_PLANT.getDefaultState();
		}
		else
		{
			System.out.println("##THIS IS BUG!!! REPORT IT TO MOD'S AUTHOR##");
			return null;
		}
	}
}