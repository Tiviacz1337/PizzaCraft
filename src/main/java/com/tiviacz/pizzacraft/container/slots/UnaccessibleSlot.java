package com.tiviacz.pizzacraft.container.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class UnaccessibleSlot extends SlotItemHandler
{
    public UnaccessibleSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPickup(PlayerEntity playerIn)
    {
        return false;
    }
}