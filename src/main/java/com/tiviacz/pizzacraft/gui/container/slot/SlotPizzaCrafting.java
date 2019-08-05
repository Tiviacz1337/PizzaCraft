package com.tiviacz.pizzacraft.gui.container.slot;

import javax.annotation.Nonnull;

import com.tiviacz.pizzacraft.crafting.bakeware.CraftingUtils;
import com.tiviacz.pizzacraft.crafting.bakeware.PizzaCraftingManager;
import com.tiviacz.pizzacraft.gui.inventory.InventoryCraftingImproved;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;

public class SlotPizzaCrafting extends Slot 
{
	private final InventoryCraftingImproved craftMatrix;
	private final PizzaCraftingManager manager;
	private final EntityPlayer player;
	private int amountCrafted;

	public SlotPizzaCrafting(PizzaCraftingManager manager, EntityPlayer player, InventoryCraftingImproved craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) 
	{
		super(inventoryIn, slotIndex, xPosition, yPosition);
		this.manager = manager;
		this.player = player;
		this.craftMatrix = craftingInventory;
	}

	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return false;
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(int amount) 
	{
		if(this.getHasStack()) 
		{
			this.amountCrafted += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) 
	{
		this.amountCrafted += amount;
		this.onCrafting(stack);
	}

	@Override
	protected void onSwapCraft(int amount) 
	{
		this.amountCrafted += amount;
	}

	@Override
	protected void onCrafting(ItemStack stack) 
	{
		if(this.amountCrafted > 0) 
		{
			stack.onCrafting(this.player.world, this.player, this.amountCrafted);
		}

		this.amountCrafted = 0;
	}

	@Override
	@Nonnull
	public ItemStack onTake(EntityPlayer player, @Nonnull ItemStack stack) 
	{
		this.onCrafting(stack);
		ForgeHooks.setCraftingPlayer(player);
		NonNullList<ItemStack> nonnulllist = manager.getRemainingItems(this.craftMatrix, player.world);
		ForgeHooks.setCraftingPlayer(null);
		CraftingUtils.onTake(player, craftMatrix, nonnulllist);
		return stack;
	}
}