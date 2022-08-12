package com.tiviacz.pizzacraft.blockentity;

import com.tiviacz.pizzacraft.blocks.PizzaBagBlock;
import com.tiviacz.pizzacraft.container.PizzaBagMenu;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PizzaBagBlockEntity extends BaseBlockEntity implements MenuProvider
{
    private final ItemStackHandler inventory = createHandler();
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter()
    {
        protected void onOpen(Level level, BlockPos pos, BlockState state)
        {
            PizzaBagBlockEntity.this.playSound();
            PizzaBagBlockEntity.this.updateBlockState(state, true);
        }

        protected void onClose(Level level, BlockPos pos, BlockState state)
        {
            PizzaBagBlockEntity.this.playSound();
            PizzaBagBlockEntity.this.updateBlockState(state, false);
        }

        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155069_, int p_155070_) {}

        protected boolean isOwnContainer(Player player)
        {
            //Container container = ((PizzaBagMenu)player.containerMenu).;
            return player.containerMenu instanceof PizzaBagMenu;
        }
    };

    public PizzaBagBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.PIZZA_BAG.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        //return compound;
    }

    public void writeToItemStack(ItemStack stack)
    {
        CompoundTag compound = new CompoundTag();
        if(isEmpty(inventory))
        {
            return;
        }
        compound.put(INVENTORY, this.inventory.serializeNBT());
        stack.setTag(compound);
    }

    public void readFromStack(ItemStack stack)
    {
        if(stack.getTag() != null)
        {
            this.inventory.deserializeNBT(stack.getTag().getCompound(INVENTORY));
        }
    }

    public void startOpen(Player player)
    {
        if(!this.remove && !player.isSpectator())
        {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player)
    {
        if(!this.remove && !player.isSpectator())
        {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen()
    {
        if(!this.remove)
        {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    private void updateBlockState(BlockState state, boolean bol)
    {
        this.level.setBlock(this.getBlockPos(), state.setValue(PizzaBagBlock.PROPERTY_OPEN, bol), 3);
    }

    private void playSound()
    {
        double d0 = (double) this.getBlockPos().getX() + 0.5D;
        double d1 = (double) this.getBlockPos().getY() + 0.5D;
        double d2 = (double) this.getBlockPos().getZ() + 0.5D;
        this.level.playSound(null, d0, d1, d2, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    // ======== ITEMHANDLER ========

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(6) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == ModItems.PIZZA.get() || stack.getItem() == ModItems.RAW_PIZZA.get();
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }

    public IItemHandlerModifiable getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new PizzaBagMenu(id, playerInventory, this);
    }

    public void openGUI(Player player, MenuProvider containerSupplier, BlockPos pos)
    {
        if(!player.level.isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer)player, containerSupplier, pos);
        }
    }
}