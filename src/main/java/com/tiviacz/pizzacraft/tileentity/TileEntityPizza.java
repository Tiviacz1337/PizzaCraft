package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPizza extends TileEntity implements ITickable
{
	private int ticks = 0;
	private boolean isCooking = false;
	private boolean isFresh = false;
	private int freshTicks = 1220;

	@Override
	public void update() 
	{
		Block oven = world.getBlockState(pos.down()).getBlock();
		
		if(oven == ModBlocks.BURNING_PIZZA_OVEN)
		{
			ticks++;
			isCooking = true;
			
			if(ticks == 610)
			{
				world.setBlockState(pos, ModBlocks.PIZZA_BURNT.getDefaultState(), 3);
			}
			
			if(oven == ModBlocks.PIZZA_OVEN)
			{
				ticks = 0;
			}
		}
		else
		{
			isCooking = false;
		}
		
		if(isFresh)
		{
			freshTicks--;
			
			if(freshTicks == 0)
			{
				freshTicks = 1220;
				isFresh = false;
			}
		}
	}
	
	public boolean isCooking()
	{
		return this.isCooking;
	}
	
	public void setFresh(boolean value)
	{
		this.isFresh = value;
	}
	
	public boolean isFresh()
	{
		return this.isFresh;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("PizzaTicksValue", this.ticks);
		compound.setInteger("FreshTicks", this.freshTicks);
		compound.setBoolean("IsFresh", this.isFresh);
		compound.setBoolean("IsCooking", this.isCooking);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.ticks = compound.getInteger("PizzaTicksValue");
		this.freshTicks = compound.getInteger("FreshTicks");
		this.isFresh = compound.getBoolean("IsFresh");
		this.isCooking = compound.getBoolean("IsCooking");
	}	
}