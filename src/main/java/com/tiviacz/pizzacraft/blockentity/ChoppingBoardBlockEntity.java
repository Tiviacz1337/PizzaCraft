package com.tiviacz.pizzacraft.blockentity;

import com.tiviacz.pizzacraft.init.ModAdvancements;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ChoppingBoardBlockEntity extends BaseBlockEntity
{
    private final ItemStackHandler inventory = createHandler();
    private boolean isItemCarvingBoard = false;
    private Direction facing = Direction.NORTH;
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private final String IS_ITEM_CARVING_BOARD = "IsItemCarvingBoard";
    private final String FACING = "Facing";

    public ChoppingBoardBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.CHOPPING_BOARD.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.facing = Direction.from2DDataValue(compound.getInt(FACING));
        this.isItemCarvingBoard = compound.getBoolean(IS_ITEM_CARVING_BOARD);
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(FACING, this.facing.get2DDataValue());
        compound.putBoolean(IS_ITEM_CARVING_BOARD, this.isItemCarvingBoard);
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
        boolean matchTool = stack.is(ModTags.KNIVES) || stack.getItem() instanceof TieredItem || stack.getItem() instanceof TridentItem || stack.getItem() instanceof ShearsItem;
        return matchTool && match.isPresent();
    }

    public void chop(ItemStack stack, @Nullable Player player)
    {
        Optional<ChoppingRecipe> match = level.getRecipeManager().getRecipeFor(ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE, new RecipeWrapper(getInventory()), level);

        if(match.isPresent())
        {
            ItemStack result = match.get().getResultItem(player.getLevel().m_9598_()).copy();
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, getStoredStack()), getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.3D, getBlockPos().getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            level.playSound(player, getBlockPos(), SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 0.7F, 0.8F);

            Direction direction = facing.getCounterClockWise(); //#TODO
            ItemEntity entity = new ItemEntity(level, getBlockPos().getX() + 0.5 + (direction.getStepX() * 0.2), getBlockPos().getY() + 0.2, getBlockPos().getZ() + 0.5 + (direction.getStepZ() * 0.2), result.copy());
            entity.setDeltaMovement(direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);
            level.addFreshEntity(entity);

            if(player != null)
            {
                stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            }
            else
            {
                if(stack.hurt(1, level.random, null))
                {
                    stack.setCount(0);
                }
            }

            if(player instanceof ServerPlayer)
            {
                ModAdvancements.CHOPPING_BOARD.trigger((ServerPlayer)player);
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
        if(cap == ForgeCapabilities.ITEM_HANDLER)
            return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }
}