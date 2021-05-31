package com.tiviacz.pizzacraft.util;

import com.tiviacz.pizzacraft.container.PizzaBagContainer;
import com.tiviacz.pizzacraft.tileentity.PizzaBagTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Map;

public class Utils
{
    public static ResourceLocation matchRecipeTag(Map<?, ?> recipe, ItemStack stack)
    {
        for(ResourceLocation tagLocation : stack.getItem().getTags())
        {
            if(recipe.containsKey(tagLocation))
            {
                return tagLocation;
            }
        }
        return null;
    }

    public static ItemStackHandler createHandlerFromStack(ItemStack stack, int size)
    {
        ItemStackHandler handler = new ItemStackHandler(size);
        if(stack.getTag() != null)
        {
            handler.deserializeNBT(stack.getTag().getCompound("Inventory"));
        }
        return handler;
    }

    public static void spawnItemStackInWorld(World world, BlockPos pos, ItemStack stack)
    {
        ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
        itemEntity.setDefaultPickupDelay();
        world.addEntity(itemEntity);
    }

    public static int calculatePlayersUsing(World world, PizzaBagTileEntity tile, int p_213976_2_, int p_213976_3_, int p_213976_4_)
    {
        int i = 0;

        for(PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double)((float)p_213976_2_ - 5.0F), (double)((float)p_213976_3_ - 5.0F), (double)((float)p_213976_4_ - 5.0F), (double)((float)(p_213976_2_ + 1) + 5.0F), (double)((float)(p_213976_3_ + 1) + 5.0F), (double)((float)(p_213976_4_ + 1) + 5.0F))))
        {
            if(playerentity.openContainer instanceof PizzaBagContainer)
            {
                ++i;
            }
        }

        return i;
    }

    public static boolean checkCount(ItemStack stack1, ItemStack stack2, int count)
    {
        return stack1.getCount() + stack2.getCount() <= count;
    }

    public static boolean checkItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return (!stack1.isEmpty() && stack1.getItem() == stack2.getItem());
    }

    public static boolean checkItemStacksAndCount(ItemStack stack1, ItemStack stack2, int count)
    {
        return checkItemStacks(stack1, stack2) && checkCount(stack1, stack2, count);
    }

    public static int getProperSlotForInsert(ItemStack stack, IItemHandlerModifiable inv)
    {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            if(inv.getStackInSlot(i).isEmpty()) // || Utils.checkItemStacksAndCount(inv.get(i), stack))
            {
                return i;
            }
        }
        return 0;
    }

    public static int getProperSlotForExtract(IItemHandlerModifiable inv)
    {
        for(int i = inv.getSlots() - 1; i > 0; i--)
        {
            if(!inv.getStackInSlot(i).isEmpty())
            {
                return i;
            }
        }
        return 0;
    }
}