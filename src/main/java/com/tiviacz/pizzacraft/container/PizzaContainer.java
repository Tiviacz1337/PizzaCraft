package com.tiviacz.pizzacraft.container;

import com.tiviacz.pizzacraft.container.slots.UnaccessibleSlot;
import com.tiviacz.pizzacraft.init.ModContainerTypes;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class PizzaContainer extends Container
{
    public PlayerInventory playerInventory;
    public PizzaTileEntity tileEntity;

    public PizzaContainer(int windowID, PlayerInventory playerInventory, PacketBuffer data)
    {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    public PizzaContainer(int windowID, PlayerInventory playerInventory, TileEntity tile)
    {
        super(ModContainerTypes.PIZZA.get(), windowID);

        this.playerInventory = playerInventory;
        this.tileEntity = (PizzaTileEntity)tile;

        if(tileEntity.isRaw())
        {
            this.addIngredientsSlotsRaw();
        }
        else
        {
            this.addIngredientsSlots();
        }

        this.addPlayerInventoryAndHotbar(playerInventory);
    }

    public void addPlayerInventoryAndHotbar(PlayerInventory playerInv)
    {
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 76 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInv, x, 8 + x*18, 134));
        }
    }

    public void addIngredientsSlotsRaw()
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlot(new SlotItemHandler(tileEntity.getInventory(), j + i * 3, 62 + j * 18, 10 + i * 18));
            }
        }
    }
  /*  public void addIngredientsSlotsRaw()
    {
        //Center
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 80, 28));

        //Upper Row
        for(int i = 1; i < 4; i++)
        {
            this.addSlot(new SlotItemHandler(tileEntity.getInventory(), i, (62 + ((i - 1) * 18)), 10));
        }

        //Middle Row
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 4, 62, 28));
        this.addSlot(new SlotItemHandler(tileEntity.getInventory(), 5, 98, 28));

        //Lower Row
        for(int i = 1; i < 4; i++)
        {
            this.addSlot(new SlotItemHandler(tileEntity.getInventory(), i + 5, (62 + ((i - 1) * 18)), 46));
        }
    } */

    public void addIngredientsSlots()
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlot(new UnaccessibleSlot(tileEntity.getInventory(), j + i * 3, 62 + j * 18, 10 + i * 18));
            }
        }
    }

 /*   public void addIngredientsSlots()
    {
        //Center
        this.addSlot(new UnaccessibleSlot(tileEntity.getInventory(), 0, 80, 28));

        //Upper Row
        for(int i = 1; i < 4; i++)
        {
            this.addSlot(new UnaccessibleSlot(tileEntity.getInventory(), i, (62 + ((i - 1) * 18)), 10));
        }

        //Middle Row
        this.addSlot(new UnaccessibleSlot(tileEntity.getInventory(), 4, 62, 28));
        this.addSlot(new UnaccessibleSlot(tileEntity.getInventory(), 5, 98, 28));

        //Lower Row
        for(int i = 1; i < 4; i++)
        {
            this.addSlot(new UnaccessibleSlot(tileEntity.getInventory(), i + 5, (62 + ((i - 1) * 18)), 46));
        }
    } */

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index < 9)
            {
                if(!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(itemstack1, 0, 9, false))
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
        }
        return itemstack;
    }

    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }

    private static PizzaTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data)
    {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());

        if(tileAtPos instanceof PizzaTileEntity)
        {
            return (PizzaTileEntity)tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
}