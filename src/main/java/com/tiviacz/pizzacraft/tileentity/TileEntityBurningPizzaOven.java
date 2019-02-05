package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.objects.block.BlockPizzaOven;
import com.tiviacz.pizzacraft.objects.block.BlockPizzaOvenBurning;
import com.tiviacz.pizzacraft.util.handlers.ConfigHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityBurningPizzaOven extends TileEntity implements ITickable
{
	int ticks = 0;

	@Override
	public void update() 
	{
		if(world.getBlockState(pos) == ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 2))
		{
			ticks++;	
			if(ticks == ConfigHandler.burningTimeOven)
			{
				world.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 1), 3);
			}
		} 
		
		if(world.getBlockState(pos) == ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 1))
		{
			ticks++;	
			if(ticks == ConfigHandler.burningTimeOven)
			{
				world.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 0), 3);
			}
		}
		
		if(world.getBlockState(pos) == ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 0))
		{
			ticks++;	
			if(ticks == ConfigHandler.burningTimeOven)
			{
				world.setBlockState(pos, ModBlocks.PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOven.STATE, 0), 3);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("OvenTicksValue", ticks);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.ticks = compound.getInteger("OvenTicksValue");
	}
}