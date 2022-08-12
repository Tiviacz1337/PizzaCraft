package com.tiviacz.pizzacraft.blockentity;

import com.tiviacz.pizzacraft.init.ModAdvancements;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class MortarAndPestleBlockEntity extends BaseBlockEntity
{
    private final ItemStackHandler inventory = createHandler();
    private int craftingProgress = 0;

    private final String CRAFTING_PROGRESS = "CraftingProgress";

    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    public MortarAndPestleBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.MORTAR_AND_PESTLE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.craftingProgress = compound.getInt(CRAFTING_PROGRESS);
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        super.save(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(CRAFTING_PROGRESS, this.craftingProgress);
        return compound;
    }

    // ======== MIXING ========

    public boolean canMix(Player player)
    {
        this.craftingProgress++;

        if(craftingProgress % 2 == 0)
        {
            //world.playSound(player, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, SoundEvents.BLOCK_STONE_STEP, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
            level.playSound(player, getBlockPos(), SoundEvents.STONE_STEP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
        }

        if(craftingProgress >= 50) resetProgress();

        return level.getRecipeManager().getRecipeFor(MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE, new RecipeWrapper(getInventory()), level).isPresent();
    }

    public void mix(Level level, Player player)
    {
        Optional<MortarRecipe> match = level.getRecipeManager().getRecipeFor(MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE, new RecipeWrapper(getInventory()), level);

        if(match.isPresent())
        {
            int duration = match.get().getDuration();

            if(this.craftingProgress == duration)
            {
                ItemStack result = match.get().getResultItem().copy();
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, getInventory().getStackInSlot(0)), getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.3D, getBlockPos().getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

                for(int i = 0; i < match.get().getInputs().size(); i++)
                {
                    decrStackSize(getInventory(), i, 1);  //¯\_( ͡° ͜ʖ ͡°)_/¯
                }
                //world.playSound(player, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
                level.playSound(player, getBlockPos(), SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 0.7F, 0.8F); // + world.rand.nextFloat());
                //world.addEntity(new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, result));
                ItemEntity entity = new ItemEntity(level, getBlockPos().getX() + 0.5 + (Direction.NORTH.getStepX() * 0.2), getBlockPos().getY() + 0.2, getBlockPos().getZ() + 0.5 + (Direction.NORTH.getStepZ() * 0.2), result.copy());
                entity.setDeltaMovement(Direction.NORTH.getStepX() * 0.2F, 0.0F, Direction.NORTH.getStepZ() * 0.2F);
                level.addFreshEntity(entity);

                if(player instanceof ServerPlayer)
                {
                    ModAdvancements.MORTAR_AND_PESTLE.trigger((ServerPlayer)player);
                }

                resetProgress();
            }
        }
    }

    public void resetProgress()
    {
        this.craftingProgress = 0;
    }

    // ======== ITEMHANDLER ========

    public IItemHandlerModifiable getInventory()
    {
        return this.inventory;
    }

    public void insertStack(int slot, Player player, InteractionHand hand)
    {
        if(!player.isCreative())
        {
            player.setItemInHand(hand, getInventory().insertItem(slot, player.getItemInHand(hand), false));
        }
        else
        {
            getInventory().insertItem(slot, player.getItemInHand(hand).copy(), false);
        }
        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    public void extractStack(int slot, Player player)
    {
        if(!player.isCreative())
        {
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), getInventory().extractItem(slot, 64, false));
        }
        else
        {
            getInventory().extractItem(slot, 64, false);
        }
        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(4)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                resetProgress();
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
