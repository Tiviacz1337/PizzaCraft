package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.blocks.ChoppingBoardBlock;
import com.tiviacz.pizzacraft.init.ModAdvancements;
import com.tiviacz.pizzacraft.init.ModItems;
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
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.facing = Direction.byHorizontalIndex(compound.getInt(FACING));
        this.isItemCarvingBoard = compound.getBoolean(IS_ITEM_CARVING_BOARD);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(FACING, this.facing.getHorizontalIndex());
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
        Optional<ChoppingRecipe> match = world.getRecipeManager().getRecipe(ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE, new RecipeWrapper(getInventory()), world);
        boolean matchTool = stack.getItem() instanceof KnifeItem || stack.getItem() instanceof TieredItem || stack.getItem() instanceof TridentItem || stack.getItem() instanceof ShearsItem;
        return matchTool && match.isPresent();
    }

    public void chop(ItemStack stack, @Nullable PlayerEntity player)
    {
        Optional<ChoppingRecipe> match = world.getRecipeManager().getRecipe(ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE, new RecipeWrapper(getInventory()), world);

        if(match.isPresent())
        {
            ItemStack result = match.get().getRecipeOutput().copy();
            world.addParticle(new ItemParticleData(ParticleTypes.ITEM, getStoredStack()), pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            world.playSound(player, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 0.7F, 0.8F);

            Direction direction = facing.rotateYCCW(); //#TODO
            ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5 + (direction.getXOffset() * 0.2), pos.getY() + 0.2, pos.getZ() + 0.5 + (direction.getZOffset() * 0.2), result.copy());
            entity.setMotion(direction.getXOffset() * 0.2F, 0.0F, direction.getZOffset() * 0.2F);
            world.addEntity(entity);

            if(player != null)
            {
                stack.damageItem(1, player, (user) -> user.sendBreakAnimation(Hand.MAIN_HAND));
            }
            else
            {
                if(stack.attemptDamageItem(1, world.rand, null))
                {
                    stack.setCount(0);
                }
            }

            if(player instanceof ServerPlayerEntity)
            {
                ModAdvancements.CHOPPING_BOARD.trigger((ServerPlayerEntity)player);
            }

            getStoredStack().shrink(1);
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
            this.markDirty();
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
            this.markDirty();
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
                markDirty();
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