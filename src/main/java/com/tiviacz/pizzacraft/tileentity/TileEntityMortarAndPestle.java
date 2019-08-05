package com.tiviacz.pizzacraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.crafting.mortar.MortarRecipeManager;
import com.tiviacz.pizzacraft.crafting.mortar.MortarRecipeUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMortarAndPestle extends TileEntity
{	
	private ItemStackHandler inventory = (new ItemStackHandler(4)
			{
				@Override
				protected void onContentsChanged(int slot)
				{
					resetProgress();
				}
			});
	
	public int craftingProgress;
	
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
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		 super.writeToNBT(compound);
		 compound.setTag("inventory", inventory.serializeNBT());
		 compound.setInteger("craftingProgress", this.craftingProgress);
		 return compound;
	} 

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		this.craftingProgress = compound.getInteger("craftingProgress");
	}
	
	public ItemStackHandler getInventory()
	{
		return this.inventory;
	}
	
	public int getSizeInventory()
	{
		return 4;
	}
	
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.getActualInputs(), index, count);
        return itemstack;
    }
	
	public int getProperSlotForStack()
	{
		for(int x = 0; x < inventory.getSlots(); x++)
		{
			if(inventory.getStackInSlot(x).isEmpty())
			{
				return x;
			}
		}
		return 0;
	}
	
	public int getProperSlotForTake()
	{
		for(int x = 3; x > -1; x--)
		{
			if(!inventory.getStackInSlot(x).isEmpty())
			{
				return x;
			}
		}
		return 0;
	}
	
	public void resetProgress()
	{
		this.craftingProgress = 0;
	}
	
	public boolean canCraft(MortarRecipeManager manager)
	{
		if(!manager.findMatchingRecipe(this, world).isEmpty())
		{
			return true;
		}
		return false;
	}
	
	public void craft()
	{
		MortarRecipeManager manager = MortarRecipeManager.getMortarManagerInstance();
		
		if(canCraft(manager))
		{
			ItemStack result = manager.findMatchingRecipe(this, world);
			int duration = manager.getRecipeDuration(this, world);
			
			this.craftingProgress++;
				
			if(this.craftingProgress == duration)
			{
				this.craftingProgress = 0; 
				
				List<ItemStack> list = manager.getRemainingItems(this, world);
				MortarRecipeUtils.onTake(this, list);
				
				if(!world.isRemote)
				{
					world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, result));
				}
			}
		}
	}
	
	public List<ItemStack> getActualInputs()
	{
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(!stack.isEmpty())
			{
				inputs.add(stack);
			}
		}
		return inputs;
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
	    tag.setInteger("craftingProgress", this.craftingProgress);
	    return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag)
    {
		super.handleUpdateTag(tag);
		inventory.deserializeNBT(tag.getCompoundTag("inventory"));
		this.craftingProgress = tag.getInteger("craftingProgress");
		
    } 
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() 
	{
		return new AxisAlignedBB(getPos(), getPos().add(1, 1, 1));
	}
}