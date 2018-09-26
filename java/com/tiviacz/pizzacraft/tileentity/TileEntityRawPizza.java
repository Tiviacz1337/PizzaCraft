package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.util.handlers.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRawPizza extends TileEntity implements ITickable
{
	int ticks = 0;
	
	@Override
	public void update() 
	{
		Block oven = world.getBlockState(pos.down()).getBlock();
		
		if(oven == ModBlocks.burning_pizza_oven)
		{
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_0)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeMargherita)
				{
					world.setBlockState(pos, ModBlocks.pizza_0.getDefaultState());			
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_1)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeFunghi)
				{
					world.setBlockState(pos, ModBlocks.pizza_1.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_2)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeProsciutto)
				{
					world.setBlockState(pos, ModBlocks.pizza_2.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_3)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeMeat)
				{
					world.setBlockState(pos, ModBlocks.pizza_3.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_4)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeClassic)
				{
					world.setBlockState(pos, ModBlocks.pizza_4.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_5)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeCapriciosa)
				{
					world.setBlockState(pos, ModBlocks.pizza_5.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_6)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeHawaiian)
				{
					world.setBlockState(pos, ModBlocks.pizza_6.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_7)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeToscana)
				{
					world.setBlockState(pos, ModBlocks.pizza_7.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_8)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeRustica)
				{
					world.setBlockState(pos, ModBlocks.pizza_8.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_9)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimeVegetarian)
				{
					world.setBlockState(pos, ModBlocks.pizza_9.getDefaultState());
				}
			}
			
			if(world.getBlockState(pos).getBlock() == ModBlocks.raw_pizza_10)
			{
				ticks++; 
				
				if(ticks == ConfigHandler.cookingTimePompea)
				{
					world.setBlockState(pos, ModBlocks.pizza_10.getDefaultState());
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("RawPizzaTicksValue", ticks);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.ticks = compound.getInteger("RawPizzaTicksValue");
	}

}	
