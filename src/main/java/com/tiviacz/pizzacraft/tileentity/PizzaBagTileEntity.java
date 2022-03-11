package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.blocks.PizzaBagBlock;
import com.tiviacz.pizzacraft.container.PizzaBagContainer;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModTileEntityTypes;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PizzaBagTileEntity extends BaseTileEntity implements INamedContainerProvider {
    private final ItemStackHandler inventory = createHandler();
    private int numPlayersUsing;
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    public PizzaBagTileEntity() {
        super(ModTileEntityTypes.PIZZA_BAG.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        return compound;
    }

    public void writeToItemStack(ItemStack stack) {
        CompoundNBT compound = new CompoundNBT();
        if (isEmpty(inventory)) {
            return;
        }
        compound.put(INVENTORY, this.inventory.serializeNBT());
        stack.setTag(compound);
    }

    public void readFromStack(ItemStack stack) {
        if (stack.getTag() != null) {
            this.inventory.deserializeNBT(stack.getTag().getCompound(INVENTORY));
        }
    }

    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            BlockState blockstate = this.getBlockState();
            boolean flag = blockstate.getValue(PizzaBagBlock.PROPERTY_OPEN);

            if (!flag) {
                this.playSound(SoundEvents.WOOL_PLACE);
                this.setOpenProperty(blockstate, true);
            }

            this.scheduleTick();
        }

    }

    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void scheduleTick() {
        this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
    }

    public void pizzaBagTick() {
        int i = this.getBlockPos().getX();
        int j = this.getBlockPos().getY();
        int k = this.getBlockPos().getZ();

        int numPlayersUsing = Utils.calculatePlayersUsing(this.level, this, i, j, k);
        if (numPlayersUsing > 0) {
            this.scheduleTick();
        } else {
            BlockState blockstate = this.getBlockState();

            boolean flag = blockstate.getValue(PizzaBagBlock.PROPERTY_OPEN);
            if (flag) {
                this.playSound(SoundEvents.WOOL_PLACE);
                this.setOpenProperty(blockstate, false);
            }
        }
    }

    private void setOpenProperty(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(BarrelBlock.OPEN, open), 3);
    }

    private void playSound(SoundEvent sound) {
        double d0 = (double) this.getBlockPos().getX() + 0.5D;
        double d1 = (double) this.getBlockPos().getY() + 0.5D;
        double d2 = (double) this.getBlockPos().getZ() + 0.5D;
        this.level.playSound(null, d0, d1, d2, sound, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
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
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new PizzaBagContainer(id, playerInventory, this);
    }

    public void openGUI(PlayerEntity player, INamedContainerProvider containerSupplier, BlockPos pos) {
        if (!player.level.isClientSide) {
            NetworkHooks.openGui((ServerPlayerEntity) player, containerSupplier, pos);
        }
    }
}