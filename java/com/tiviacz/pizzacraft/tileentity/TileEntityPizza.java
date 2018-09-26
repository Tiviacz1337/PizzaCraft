package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPizza extends TileEntity implements ITickable
{
	int ticks = 0;

	@Override
	public void update() 
	{
		Block oven = world.getBlockState(pos.down()).getBlock();
		
		if(oven == ModBlocks.burning_pizza_oven)
		{
			ticks++;
			
			if(ticks == 610)
			{
				world.setBlockState(pos, ModBlocks.pizza_burnt.getDefaultState());
			}
			
			if(oven == ModBlocks.pizza_oven)
			{
				ticks = 0;
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("PizzaTicksValue", ticks);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.ticks = compound.getInteger("PizzaTicksValue");
	}
	
	
}
