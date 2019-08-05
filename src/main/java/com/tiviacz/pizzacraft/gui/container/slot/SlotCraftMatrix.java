package com.tiviacz.pizzacraft.gui.container.slot;

import com.tiviacz.pizzacraft.gui.container.ContainerBakeware;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotCraftMatrix extends Slot
{
	private final ContainerBakeware container;
	private final IInventory inventory;
	
	public SlotCraftMatrix(ContainerBakeware containerBakeware, IInventory inventoryIn, int index, int xPosition, int yPosition) 
	{
		super(inventoryIn, index, xPosition, yPosition);
		
		this.container = containerBakeware;
		this.inventory = inventoryIn;
	}
	
	@Override
	public void onSlotChanged()
    {
		this.container.onCraftMatrixChanged(inventory);
    }
}