package com.tiviacz.pizzacraft.container;

import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.init.ModMenuTypes;
import com.tiviacz.pizzacraft.items.SauceItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PizzaMenu extends AbstractContainerMenu
{
    public Inventory inv;
    public PizzaBlockEntity blockEntity;
    public ItemStackHandler transistentHandler = createTransistentHandler();

    public PizzaMenu(int windowID, Inventory inv, FriendlyByteBuf data)
    {
        this(windowID, inv, getBlockEntity(inv, data));
    }

    public PizzaMenu(int windowID, Inventory inv, BlockEntity tile)
    {
        super(ModMenuTypes.PIZZA.get(), windowID);

        this.inv = inv;
        this.blockEntity = (PizzaBlockEntity)tile;

        this.addIngredientsSlots(); //0-8
        this.addSauceSlot(); //9
        this.addTransistentSlots(); //10, 11

        this.addPlayerInventoryAndHotbar(inv);
    }

    public void addPlayerInventoryAndHotbar(Inventory inv)
    {
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 76 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(inv, x, 8 + x*18, 134));
        }
    }

    public void addTransistentSlots()
    {
        this.addSlot(new SlotItemHandler(transistentHandler, 0, 35, 10)
        {
            @Override
            public int getMaxStackSize(@Nonnull ItemStack stack)
            {
                return 1;
            }
        });

        this.addSlot(new SlotItemHandler(transistentHandler, 1, 35, 46)
        {
            @Override
            public int getMaxStackSize(@Nonnull ItemStack stack)
            {
                return 1;
            }
        });
    }

    public void addIngredientsSlots()
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(), j + i * 3, 62 + j * 18, 10 + i * 18)
                {
                    @Override
                    public int getMaxStackSize(@Nonnull ItemStack stack)
                    {
                        return 1;
                    }
                });
            }
        }
    }

    public void addSauceSlot()
    {
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 9, 125, 20)
        {
            @Override
            public int getMaxStackSize(@Nonnull ItemStack stack)
            {
                return 1;
            }

            @Override
            public boolean mayPickup(Player playerIn)
            {
                return false;
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return false;
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if(index < 12)
            {
                if(!this.moveItemStackTo(itemstack1, 12, this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(itemstack1, 0, 12, false))
            {
                return ItemStack.EMPTY;
            }

            if(itemstack1.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    public void removed(Player player)
    {
        super.removed(player);
        this.clearContainer(player, new RecipeWrapper(transistentHandler));
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    private static PizzaBlockEntity getBlockEntity(final Inventory inv, final FriendlyByteBuf data)
    {
        Objects.requireNonNull(inv, "inv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity blockEntity = inv.player.getLevel().getBlockEntity(data.readBlockPos());

        if(blockEntity instanceof PizzaBlockEntity)
        {
            return (PizzaBlockEntity)blockEntity;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }

    private ItemStackHandler createTransistentHandler()
    {
        return new ItemStackHandler(2)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                PizzaMenu.this.transferStack();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack)
            {
                return stack.getItem() instanceof PotionItem || stack.getItem() instanceof SauceItem;
            }
        };
    }

    private void transferStack()
    {
        if(!transistentHandler.getStackInSlot(0).isEmpty())
        {
            ItemStack sauceStack = transistentHandler.getStackInSlot(0);
            ItemStack container = getItemStack(sauceStack);

            blockEntity.getInventory().setStackInSlot(9, sauceStack);
            transistentHandler.setStackInSlot(0, ItemStack.EMPTY);
            blockEntity.setChanged();
            transistentHandler.setStackInSlot(1, container);
        }
    }

    public static ItemStack getItemStack(ItemStack sauceStack)
    {
        ItemStack container = sauceStack.getCraftingRemainingItem();

        boolean isPotion = sauceStack.getItem() instanceof PotionItem;
        boolean isSoup = sauceStack.getItem() instanceof BowlFoodItem;

        if(container.isEmpty())
        {
            if(isPotion)
            {
                container = new ItemStack(Items.GLASS_BOTTLE);
            }
            else if(isSoup)
            {
                container = new ItemStack(Items.BOWL);
            }
        }
        return container;
    }
}