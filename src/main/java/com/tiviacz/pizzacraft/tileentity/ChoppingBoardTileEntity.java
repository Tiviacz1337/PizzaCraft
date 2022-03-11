package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModAdvancements;
import com.tiviacz.pizzacraft.init.ModTileEntityTypes;
import com.tiviacz.pizzacraft.items.KnifeItem;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.TieredItem;
import net.minecraft.item.TridentItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ChoppingBoardTileEntity extends BaseTileEntity
{
    private final ItemStackHandler inventory = createHandler();
    private boolean isItemCarvingBoard = false;
    private Direction facing = Direction.NORTH;
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private final String IS_ITEM_CARVING_BOARD = "IsItemCarvingBoard";
    private final String FACING = "Facing";

    public ChoppingBoardTileEntity()
    {
        super(ModTileEntityTypes.CHOPPING_BOARD.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.facing = Direction.from2DDataValue(compound.getInt(FACING));
        this.isItemCarvingBoard = compound.getBoolean(IS_ITEM_CARVING_BOARD);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(FACING, this.facing.get2DDataValue());
        compound.putBoolean(IS_ITEM_CARVING_BOARD, this.isItemCarvingBoard);
        return compound;
    }

    public Direction getFacing()
    {
        return this.facing;
    }

    public void setFacing(Direction direction)
    {
        this.facing = direction;
    }

    // ======== CHOPPING ========

    public boolean canChop(ItemStack stack)
    {
        Optional<ChoppingRecipe> match = level.getRecipeManager().getRecipeFor(ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE, new RecipeWrapper(getInventory()), level);
        boolean matchTool = stack.getItem() instanceof KnifeItem || stack.getItem() instanceof TieredItem || stack.getItem() instanceof TridentItem || stack.getItem() instanceof ShearsItem;
        return matchTool && match.isPresent();
    }

    public void chop(ItemStack stack, @Nullable PlayerEntity player)
    {
        Optional<ChoppingRecipe> match = level.getRecipeManager().getRecipeFor(ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE, new RecipeWrapper(getInventory()), level);

        if(match.isPresent())
        {
            ItemStack result = match.get().getResultItem().copy();
            level.addParticle(new ItemParticleData(ParticleTypes.ITEM, getStoredStack()), getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.3D, getBlockPos().getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            level.playSound(player, getBlockPos(), SoundEvents.PUMPKIN_CARVE, SoundCategory.BLOCKS, 0.7F, 0.8F);

            Direction direction = facing.getCounterClockWise(); //#TODO
            ItemEntity entity = new ItemEntity(level, getBlockPos().getX() + 0.5 + (direction.getStepX() * 0.2), getBlockPos().getY() + 0.2, getBlockPos().getZ() + 0.5 + (direction.getStepZ() * 0.2), result.copy());
            entity.setDeltaMovement(direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);
            level.addFreshEntity(entity);

            if(player != null)
            {
                stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(Hand.MAIN_HAND));
            }
            else
            {
                if(stack.hurt(1, level.random, null))
                {
                    stack.setCount(0);
                }
            }

            if(player instanceof ServerPlayerEntity)
            {
                ModAdvancements.CHOPPING_BOARD.trigger((ServerPlayerEntity)player);
            }

            getStoredStack().shrink(1);
            this.setChanged();
        }
    }

    // ======== ITEMHANDLER ========

    public IItemHandlerModifiable getInventory()
    {
        return this.inventory;
    }

    public ItemStack getStoredStack()
    {
        return this.inventory.getStackInSlot(0);
    }

    public boolean isEmpty()
    {
        return this.inventory.getStackInSlot(0).isEmpty();
    }

    public boolean isItemCarvingBoard()
    {
        return this.isItemCarvingBoard;
    }

    public boolean carveToolOnBoard(ItemStack tool)
    {
        if(this.addItem(tool))
        {
            this.isItemCarvingBoard = true;
            this.inventory.insertItem(0, tool, false);
            return true;
        }
        return false;
    }

    public boolean addItem(ItemStack stack)
    {
        if(this.isEmpty() && !stack.isEmpty())
        {
            this.isItemCarvingBoard = false;
            this.setChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem()
    {
        if(!this.isEmpty())
        {
            this.isItemCarvingBoard = false;
            ItemStack item = this.getStoredStack();
            this.setChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler()
        {
            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot)
            {
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