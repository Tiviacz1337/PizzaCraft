package com.tiviacz.pizzacraft.container;

import com.tiviacz.pizzacraft.blockentity.PizzaBagBlockEntity;
import com.tiviacz.pizzacraft.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class PizzaBagMenu extends AbstractContainerMenu
{
    public Inventory inv;
    public PizzaBagBlockEntity blockEntity;

    public PizzaBagMenu(int windowID, Inventory inv, FriendlyByteBuf data)
    {
        this(windowID, inv, getBlockEntity(inv, data));
    }

    public PizzaBagMenu(int windowID, Inventory inv, BlockEntity blockEntity)
    {
        super(ModMenuTypes.PIZZA_BAG.get(), windowID);

        this.inv = inv;
        this.blockEntity = (PizzaBagBlockEntity)blockEntity;

        this.addInventory();
        this.addPlayerInventoryAndHotbar(inv);

        this.blockEntity.startOpen(inv.player);
    }

    public void addPlayerInventoryAndHotbar(Inventory inv)
    {
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(inv, x, 8 + x*18, 109));
        }
    }

    public void addInventory()
    {
        for(int j = 0; j < 6; ++j)
        {
            this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), j, 36 + j * 18, 20));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
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

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.blockEntity.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    private static PizzaBagBlockEntity getBlockEntity(final Inventory inv, final FriendlyByteBuf data)
    {
        Objects.requireNonNull(inv, "inv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());

        if(blockEntity instanceof PizzaBagBlockEntity)
        {
            return (PizzaBagBlockEntity)blockEntity;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }
}