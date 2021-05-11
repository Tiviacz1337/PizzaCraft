package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Hand;

public class PizzaBoardTileEntity extends BaseTileEntity
{
    private BlockState storedBlockState = Blocks.AIR.getDefaultState();
    private final String STORED_BLOCKSTATE = "StoredBlockState";

    public PizzaBoardTileEntity()
    {
        super(ModTileEntityTypes.PIZZA.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        this.storedBlockState = NBTUtil.readBlockState(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put(STORED_BLOCKSTATE, NBTUtil.writeBlockState(storedBlockState));
        return compound;
    }

    public void onBlockActivated(PlayerEntity player, Hand hand)
    {
        if(hand == Hand.MAIN_HAND)
        {
            ItemStack stack = player.getHeldItem(hand);

            if(!stack.isEmpty() && player.isSneaking())
            {
                if(this.storedBlockState != Blocks.AIR.getDefaultState())
                {
                    world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.storedBlockState.getBlock())));
                    this.storedBlockState = Blocks.AIR.getDefaultState();
                }
            }
            else if(stack.getItem() == ModItems.RAW_PIZZA.get())
            {
                this.storedBlockState = ModBlocks.RAW_PIZZA.get().getDefaultState();
                stack.shrink(1);
            }
            else if(stack.getItem() == ModItems.PIZZA.get())
            {
                this.storedBlockState = ModBlocks.RAW_PIZZA.get().getDefaultState();
                stack.shrink(1);
            }
        }
    }
}