package com.tiviacz.pizzacraft.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;

public class BaseBlockEntity extends BlockEntity
{
    protected final String INVENTORY = "Inventory";

    public BaseBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state)
    {
        super(tileEntityTypeIn, pos, state);
    }

    public boolean isEmpty(IItemHandlerModifiable inventory)
    {
        for(int i = 0; i < inventory.getSlots(); i++)
        {
            if(!inventory.getStackInSlot(i).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    public ItemStack decrStackSize(IItemHandlerModifiable inventory, int index, int count)
    {
        ItemStack itemstack = index >= 0 && index < inventory.getSlots() && !inventory.getStackInSlot(index).isEmpty() && count > 0 ? inventory.getStackInSlot(index).split(count) : ItemStack.EMPTY;

        if(!itemstack.isEmpty())
        {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        notifyBlockUpdate();
    }

    private void notifyBlockUpdate()
    {
        BlockState blockstate = getLevel().getBlockState(getBlockPos());
        level.setBlocksDirty(getBlockPos(), blockstate, blockstate);
        level.sendBlockUpdated(getBlockPos(), blockstate, blockstate, Block.UPDATE_CLIENTS);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        //this.load(pkt.getTag());
        super.onDataPacket(net, pkt);
        this.handleUpdateTag(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.save(new CompoundTag());
    }
}