package com.tiviacz.pizzacraft.container;

import com.tiviacz.pizzacraft.init.ModContainerTypes;
import com.tiviacz.pizzacraft.tileentity.PizzaBagTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class PizzaBagContainer extends Container
{
    public PlayerInventory playerInventory;
    public PizzaBagTileEntity tileEntity;

    public PizzaBagContainer(int windowID, PlayerInventory playerInventory, PacketBuffer data)
    {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    public PizzaBagContainer(int windowID, PlayerInventory playerInventory, TileEntity tile)
    {
        super(ModContainerTypes.PIZZA_BAG.get(), windowID);

        this.playerInventory = playerInventory;
        this.tileEntity = (PizzaBagTileEntity)tile;

        this.addInventory();
        this.addPlayerInventoryAndHotbar(playerInventory);

        this.tileEntity.openInventory(playerInventory.player);
    }

    public void addPlayerInventoryAndHotbar(PlayerInventory playerInv)
    {
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInv, x, 8 + x*18, 109));
        }
    }

    public void addInventory()
    {
        for(int j = 0; j < 6; ++j)
        {
            this.addSlot(new SlotItemHandler(this.tileEntity.getInventory(), j, 36 + j * 18, 20));
        }
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if(index < 6)
            {
                if(!this.moveItemStackTo(itemstack1, 6, this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(itemstack1, 0, 6, false))
            {
                return ItemStack.EMPTY;
            }

            if(itemstack1.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(PlayerEntity playerIn)
    {
        super.removed(playerIn);
        this.tileEntity.closeInventory(playerIn);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn)
    {
        return true;
    }

    private static PizzaBagTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data)
    {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());

        if(tileAtPos instanceof PizzaBagTileEntity)
        {
            return (PizzaBagTileEntity)tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
}