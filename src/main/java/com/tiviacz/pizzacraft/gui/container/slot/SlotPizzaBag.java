package com.tiviacz.pizzacraft.gui.container.slot;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.tileentity.TileEntityPizzaBag;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPizzaBag extends SlotItemHandler
{
	private final TileEntityPizzaBag tile;
	
	public SlotPizzaBag(TileEntityPizzaBag tile, IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
		
		this.tile = tile;
	}
	
	@Override
	public void onSlotChanged() 
	{
		tile.markDirty();
	}
	
	@Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
		if(Reference.getValidBagItems().contains(stack.getItem()))
       	{
       		return true;
       	}
       	return false;
    }
}