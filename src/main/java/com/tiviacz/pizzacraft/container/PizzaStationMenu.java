package com.tiviacz.pizzacraft.container;

import com.tiviacz.pizzacraft.blockentity.PizzaStationBlockEntity;
import com.tiviacz.pizzacraft.common.PizzaCalculator;
import com.tiviacz.pizzacraft.container.slots.PizzaStationResultSlot;
import com.tiviacz.pizzacraft.init.ModMenuTypes;
import com.tiviacz.pizzacraft.items.SauceItem;
import com.tiviacz.pizzacraft.tags.ModTags;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class PizzaStationMenu extends AbstractContainerMenu
{
    public Inventory inv;
    public PizzaStationBlockEntity blockEntity;
    private String itemName;

    public PizzaStationMenu(int windowID, Inventory inv, FriendlyByteBuf data)
    {
        this(windowID, inv, getBlockEntity(inv, data));
    }

    public PizzaStationMenu(int windowID, Inventory inv, BlockEntity tile)
    {
        super(ModMenuTypes.PIZZA_STATION.get(), windowID);

        this.inv = inv;
        this.blockEntity = (PizzaStationBlockEntity) tile;

        this.addResultSlot();

        this.addBaseSlot();
        this.addSauceSlot();
        this.addIngredientsSlots();
        this.addPlayerInventoryAndHotbar(inv);
    }

    public void addPlayerInventoryAndHotbar(Inventory inv) {
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 30 + 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(inv, x, 8 + x * 18, 30 + 142));
        }
    }

    public void addResultSlot()
    {
        this.addSlot(new PizzaStationResultSlot(this, blockEntity.getInventory(), 0, 148, 30 + 35));
    }

    public void addBaseSlot()
    {
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 12, 30 + 47)
        {
            @Override
            public void setChanged() {
                super.setChanged();
                updateOutput();
            }
        });
    }

    public void addSauceSlot()
    {
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 12, 30 + 24)
        {
            @Override
            public void setChanged()
            {
                super.setChanged();
                updateOutput();
            }
        });
    }

    public void addIngredientsSlots()
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 3 + (j + i * 3), 55 + j * 18, 30 + 17 + i * 18)
                {
                    @Override
                    public void setChanged()
                    {
                        super.setChanged();
                        updateOutput();
                    }
                });
            }
        }
    }

    public void updateOutput()
    {
        ItemStackHandler handler = blockEntity.getInventory();
        ItemStack baseStack = handler.getStackInSlot(1);
        ItemStack sauceCopy = handler.getStackInSlot(2).copy();

        if(baseStack.isEmpty())
        {
            resetOutput();
            return;
        }

        ItemStackHandler ingredientsHandler = new ItemStackHandler(10);

        if(!sauceCopy.isEmpty() && !((sauceCopy.getItem() instanceof PotionItem) || (sauceCopy.getItem() instanceof SauceItem || sauceCopy.is(ModTags.SAUCE))))
        {
            resetOutput();
            return;
        }

        int index = 0;

        for(int i = 3; i < handler.getSlots(); i++)
        {
            ItemStack stack = handler.getStackInSlot(i);
            ItemStack copy = stack.copy();
            copy.setCount(1);
            ingredientsHandler.setStackInSlot(index, copy);
            index++;
        }

        if (Utils.isEmpty(ingredientsHandler)) {
            resetOutput();
            return;
        }
        ItemStack baseCopy = baseStack.copy();
        baseCopy.setCount(1);

        PizzaCalculator calc = new PizzaCalculator(baseCopy, sauceCopy, ingredientsHandler);
        ItemStack result = calc.getResultStack();

        if(result.isEmpty())
        {
            resetOutput();
            return;
        }

        if (StringUtils.isBlank(this.itemName)) {
            result.resetHoverName();
        } else if (!this.itemName.equals(result.getHoverName().getString())) {
            result.setHoverName(Component.literal(this.itemName));
        }
        setOutput(result);
    }

    public void setItemName(String name)
    {
        this.itemName = name;
        updateOutput();
    }

    private void setOutput(ItemStack stack)
    {
        this.blockEntity.getInventory().setStackInSlot(0, stack);
        this.broadcastChanges();
    }

    private void resetOutput()
    {
        ItemStack stack = this.blockEntity.getInventory().getStackInSlot(0);

        if (!stack.isEmpty()) {
            this.setOutput(ItemStack.EMPTY);
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

            if(index < 12)
            {
                if(!this.moveItemStackTo(itemstack1, 12, 12 + Inventory.INVENTORY_SIZE, true))
                {
                    return ItemStack.EMPTY;
                }
            }

            else if(!this.moveItemStackTo(itemstack1, 0, 12, false))
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
            slot.onTake(player, itemstack1);
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

    private static PizzaStationBlockEntity getBlockEntity(final Inventory inv, final FriendlyByteBuf data)
    {
        Objects.requireNonNull(inv, "inv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity blockEntity = inv.player.getLevel().getBlockEntity(data.readBlockPos());

        if(blockEntity instanceof PizzaStationBlockEntity)
        {
            return (PizzaStationBlockEntity)blockEntity;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }
}