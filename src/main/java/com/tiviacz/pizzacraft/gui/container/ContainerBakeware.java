package com.tiviacz.pizzacraft.gui.container;

import com.tiviacz.pizzacraft.crafting.bakeware.PizzaCraftingManager;
import com.tiviacz.pizzacraft.gui.container.slot.SlotCraftMatrix;
import com.tiviacz.pizzacraft.gui.container.slot.SlotPizzaCrafting;
import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBakeware extends Container
{
	public EntityPlayer player;
	public TileEntityBakeware inventory;
	public InventoryCraftingImproved craftMatrix;
	public InventoryCraftResult craftResult = new InventoryCraftResult();
	public World world;
	
	public ContainerBakeware(World world, EntityPlayer player, TileEntityBakeware inventory)
	{
		this.craftMatrix = new InventoryCraftingImproved(inventory, this, 3, 3);
		this.world = world;
		this.player = player;
		this.inventory = inventory;
		
		this.addCraftMatrix();
		this.addCraftResult(player);
		
		this.addPlayerInventoryAndHotbar(player.inventory);
		
		this.onCraftMatrixChanged(inventory);
	}
	
	public void addCraftMatrix()
	{
		for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new SlotCraftMatrix(this, this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
	}
	
	public void addCraftResult(EntityPlayer player)
	{
		this.addSlotToContainer(new SlotPizzaCrafting(PizzaCraftingManager.getPizzaCraftingInstance(), player, this.craftMatrix, this.craftResult, 0, 124, 35));
	}
	
	public void addPlayerInventoryAndHotbar(InventoryPlayer playerInv)
	{
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x*18, 142));
		}		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index == 0)
            {
                itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

                if(!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(index >= 10 && index < 37)
            {
                if(!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index >= 37 && index < 46)
            {
                if(!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return ItemStack.EMPTY;
            }

            if(itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if(itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if(index == 0)
            {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
    {
		this.inventory.markDirty();
        super.onContainerClosed(playerIn);
    }
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
    {
		craftResult.setInventorySlotContents(0, PizzaCraftingManager.getPizzaCraftingInstance().findMatchingRecipe(this.craftMatrix, this.world));
    }
	
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}
