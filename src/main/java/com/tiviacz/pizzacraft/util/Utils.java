package com.tiviacz.pizzacraft.util;

import com.tiviacz.pizzacraft.blockentity.PizzaBagBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaBagMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class Utils
{
    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> getTicker(BlockEntityType<A> type, BlockEntityType<E> targetType, BlockEntityTicker<? super E> ticker) {
        return targetType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    public static void dropContents(Level pLevel, BlockPos pPos, ItemStackHandler handler)
    {
        IntStream.range(0, handler.getSlots() - 1).forEach(i -> Containers.dropItemStack(pLevel, (double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), handler.getStackInSlot(i)));
    }

    public static void spawnItemStackInWorld(Level level, BlockPos pos, ItemStack stack)
    {
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    public static boolean isEmpty(ItemStackHandler handler)
    {
        for(int i = 0; i < handler.getSlots(); i++)
        {
            if(!handler.getStackInSlot(i).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    public static int calculatePlayersUsing(Level level, PizzaBagBlockEntity tile, int p_213976_2_, int p_213976_3_, int p_213976_4_)
    {
        int i = 0;

        for(Player player : level.getEntitiesOfClass(Player.class, new AABB((double)((float)p_213976_2_ - 5.0F), (double)((float)p_213976_3_ - 5.0F), (double)((float)p_213976_4_ - 5.0F), (double)((float)(p_213976_2_ + 1) + 5.0F), (double)((float)(p_213976_3_ + 1) + 5.0F), (double)((float)(p_213976_4_ + 1) + 5.0F))))
        {
            if(player.containerMenu instanceof PizzaBagMenu)
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

    public static boolean isShiftPressed()
    {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
    }
}