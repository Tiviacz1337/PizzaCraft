package com.tiviacz.pizzacraft.blockentity;

import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PizzaBoardBlockEntity extends BaseBlockEntity
{
    private BlockState storedBlockState = Blocks.AIR.defaultBlockState();
    private final String STORED_BLOCKSTATE = "StoredBlockState";

    public PizzaBoardBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.PIZZA.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.storedBlockState = NbtUtils.readBlockState(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        super.save(compound);
        compound.put(STORED_BLOCKSTATE, NbtUtils.writeBlockState(storedBlockState));
        return compound;
    }

    public void use(Player player, InteractionHand hand)
    {
        if(hand == InteractionHand.MAIN_HAND)
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