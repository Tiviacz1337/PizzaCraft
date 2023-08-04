package com.tiviacz.pizzacraft.blockentity;

import com.tiviacz.pizzacraft.container.PizzaStationMenu;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.items.SauceItem;
import com.tiviacz.pizzacraft.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PizzaStationBlockEntity extends BaseBlockEntity implements MenuProvider
{
    private final ItemStackHandler inventory = createHandler();
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    public PizzaStationBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.PIZZA_STATION.get(), pos, state);
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
    }

    public ItemStackHandler getInventory()
    {
        return inventory;
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
    {
        return new PizzaStationMenu(id, playerInventory, this);
    }

    public void openGUI(Player player, MenuProvider containerSupplier, BlockPos pos)
    {
        if(!player.getLevel().isClientSide)
        {
            NetworkHooks.openGui((ServerPlayer)player, containerSupplier, pos);
        }
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(13)
        {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack)
            {
                if(slot == 0) return false;
                if(slot == 1) return stack.is(ModTags.DOUGH);
                if(slot == 2) return (stack.getItem() instanceof SauceItem || stack.is(ModTags.SAUCE)) || stack.getItem() instanceof PotionItem;
                else return stack.isEdible() || stack.is(ModTags.INGREDIENTS);
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                setChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side)
    {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }
}