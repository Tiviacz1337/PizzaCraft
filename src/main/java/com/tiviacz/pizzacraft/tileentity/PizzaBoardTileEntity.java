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
    private BlockState storedBlockState = Blocks.AIR.defaultBlockState();
    private final String STORED_BLOCKSTATE = "StoredBlockState";

    public PizzaBoardTileEntity()
    {
        super(ModTileEntityTypes.PIZZA.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.storedBlockState = NBTUtil.readBlockState(compound);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.put(STORED_BLOCKSTATE, NBTUtil.writeBlockState(storedBlockState));
        return compound;
    }

    public void use(PlayerEntity player, Hand hand)
    {
        if(hand == Hand.MAIN_HAND)
        {
            ItemStack stack = player.getItemInHand(hand);

            if(!stack.isEmpty() && player.isCrouching())
            {
                if(this.storedBlockState != Blocks.AIR.defaultBlockState())
                {
                    level.addFreshEntity(new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), new ItemStack(this.storedBlockState.getBlock())));
                    this.storedBlockState = Blocks.AIR.defaultBlockState();
                }
            }
            else if(stack.getItem() == ModItems.RAW_PIZZA.get())
            {
                this.storedBlockState = ModBlocks.RAW_PIZZA.get().defaultBlockState();
                stack.shrink(1);
            }
            else if(stack.getItem() == ModItems.PIZZA.get())
            {
                this.storedBlockState = ModBlocks.RAW_PIZZA.get().defaultBlockState();
                stack.shrink(1);
            }
        }
    }
}