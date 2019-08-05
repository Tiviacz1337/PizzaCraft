package com.tiviacz.pizzacraft.tileentity;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityPizzaBag extends TileEntity
{	
	public ItemStackHandler inventory = (new ItemStackHandler(6)
	{
		@Override
        protected int getStackLimit(int slot, ItemStack stack)
        {
            return Reference.getValidBagItems().contains(stack.getItem()) ? 64 : 0;
        }
	});
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) 
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) 
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
	}
	
	public String getName()
	{
		return "container.pizza_bag";
	}
	
	public boolean isEmpty()
	{
		ItemStack stack1 = inventory.getStackInSlot(0);
		ItemStack stack2 = inventory.getStackInSlot(1);
		ItemStack stack3 = inventory.getStackInSlot(2);
		ItemStack stack4 = inventory.getStackInSlot(3);
		ItemStack stack5 = inventory.getStackInSlot(4);
		ItemStack stack6 = inventory.getStackInSlot(5);
		
		if(stack1.isEmpty() && stack2.isEmpty() && stack3.isEmpty() && stack4.isEmpty() && stack5.isEmpty() && stack6.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		 super.writeToNBT(compound);
		 compound.setTag("inventory", inventory.serializeNBT());
		 return compound;
	} 

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}
	
	public void loadData(NBTTagCompound compound)
	{
		if(compound == null)
		{
			return;
		}
		
		if(compound.hasKey("inventory"))
		{
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		}
	}
	
	public void dropAsItem(World world, BlockPos pos)
	{
		ItemStack stack = new ItemStack(ModBlocks.PIZZA_BAG);
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("inventory", inventory.serializeNBT());
		
		if(!isEmpty())
		{
			stack.setTagCompound(compound);
		}
		
        Block.spawnAsEntity(world, pos, stack);
	}
}