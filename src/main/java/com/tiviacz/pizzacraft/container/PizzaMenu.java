package com.tiviacz.pizzacraft.container;

import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.container.slots.UnaccessibleSlot;
import com.tiviacz.pizzacraft.init.ModMenuTypes;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PizzaMenu extends AbstractContainerMenu
{
    public Inventory inv;
    public PizzaBlockEntity blockEntity;

    public PizzaMenu(int windowID, Inventory inv, FriendlyByteBuf data)
    {
        this(windowID, inv, getBlockEntity(inv, data));
    }

    public PizzaMenu(int windowID, Inventory inv, BlockEntity tile)
    {
        super(ModMenuTypes.PIZZA.get(), windowID);

        this.inv = inv;
        this.blockEntity = (PizzaBlockEntity)tile;

        if(blockEntity.isRaw())
        {
            this.addIngredientsSlotsRaw();
        }
        else
        {
            this.addIngredientsSlots();
        }

        this.addPlayerInventoryAndHotbar(inv);
    }

    public void addPlayerInventoryAndHotbar(Inventory inv)
    {
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 76 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(inv, x, 8 + x*18, 134));
        }
    }

    public void addIngredientsSlotsRaw()
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(), j + i * 3, 62 + j * 18, 10 + i * 18)
                {
                    @Override
                    public int getMaxStackSize(@Nonnull ItemStack stack)
                    {
                        return PizzaLayers.getMaxStackSizeForStack(stack);
                    }
                });
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
                this.addSlot(new UnaccessibleSlot(blockEntity.getInventory(), j + i * 3, 62 + j * 18, 10 + i * 18));
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
    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if(index < 9)
            {
                if(!this.moveItemStackTo(itemstack1, 9, this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(itemstack1, 0, 9, false))
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

    public void removed(Player player)
    {
        super.removed(player);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    private static PizzaBlockEntity getBlockEntity(final Inventory inv, final FriendlyByteBuf data)
    {
        Objects.requireNonNull(inv, "inv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity blockEntity = inv.player.level.getBlockEntity(data.readBlockPos());

        if(blockEntity instanceof PizzaBlockEntity)
        {
            return (PizzaBlockEntity)blockEntity;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }
}