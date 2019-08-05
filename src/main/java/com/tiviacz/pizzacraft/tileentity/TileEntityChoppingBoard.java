package com.tiviacz.pizzacraft.tileentity;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.crafting.chopping.ChoppingBoardRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityChoppingBoard extends TileEntity
{	
	public ItemStackHandler inventory = new ItemStackHandler(3);
	
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
	
	public boolean chop()
	{
		if(canChop())
		{
			ItemStack input = this.inventory.getStackInSlot(0);
			ItemStack result = this.inventory.getStackInSlot(1);
			ItemStack choppingResult = ChoppingBoardRecipes.instance().getChoppingResult(input);
			
			if(result.isEmpty())
			{
				this.inventory.setStackInSlot(1, choppingResult.copy());
				input.shrink(1);
				return true;
			}
			
			else if(result.getItem() == choppingResult.getItem())
			{
				result.grow(choppingResult.getCount());
				input.shrink(1);
				return true;
			}
		}
		return false;
	}
	
	private boolean canChop()
    {
        if((this.inventory.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        
        else
        {
            ItemStack itemstack = ChoppingBoardRecipes.instance().getChoppingResult(this.inventory.getStackInSlot(0));

            if(itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.inventory.getStackInSlot(1);

                if(itemstack1.isEmpty())
                {
                    return true;
                }
                else if(!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if(itemstack1.getCount() + itemstack.getCount() <= this.inventory.getSlotLimit(1) && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); 
                }
            }
        }
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

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
	    return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
	    handleUpdateTag(pkt.getNbtCompound());
	}
 
	@Override
	public NBTTagCompound getUpdateTag()
	{
	    NBTTagCompound tag = super.getUpdateTag();
	    tag.setTag("inventory", inventory.serializeNBT());
	    return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag)
    {
		super.handleUpdateTag(tag);
		inventory.deserializeNBT(tag.getCompoundTag("inventory"));
    } 
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() 
	{
		return new AxisAlignedBB(getPos(), getPos().add(1, 1, 1));
	}
}