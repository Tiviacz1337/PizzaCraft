package com.tiviacz.pizzacraft.tileentity;

import com.tiviacz.pizzacraft.init.ModAdvancements;
import com.tiviacz.pizzacraft.init.ModTileEntityTypes;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class MortarAndPestleTileEntity extends BaseTileEntity
{
    private final ItemStackHandler inventory = createHandler();
    private int craftingProgress = 0;

    private final String CRAFTING_PROGRESS = "CraftingProgress";

    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    public MortarAndPestleTileEntity()
    {
        super(ModTileEntityTypes.MORTAR_AND_PESTLE.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.craftingProgress = compound.getInt(CRAFTING_PROGRESS);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(CRAFTING_PROGRESS, this.craftingProgress);
        return compound;
    }

    // ======== MIXING ========

    public boolean canMix(PlayerEntity player)
    {
        this.craftingProgress++;

        if(craftingProgress % 2 == 0)
        {
            //world.playSound(player, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, SoundEvents.BLOCK_STONE_STEP, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
            world.playSound(player, pos, SoundEvents.BLOCK_STONE_STEP, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
        }

        if(craftingProgress >= 50) resetProgress();

        return world.getRecipeManager().getRecipe(MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE, new RecipeWrapper(getInventory()), world).isPresent();
    }

    public void mix(World world, PlayerEntity player)
    {
        Optional<MortarRecipe> match = world.getRecipeManager().getRecipe(MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE, new RecipeWrapper(getInventory()), world);

        if(match.isPresent())
        {
            int duration = match.get().getDuration();

            if(this.craftingProgress == duration)
            {
                ItemStack result = match.get().getRecipeOutput().copy();
                world.addParticle(new ItemParticleData(ParticleTypes.ITEM, getInventory().getStackInSlot(0)), pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

                for(int i = 0; i < match.get().getInputs().size(); i++)
                {
                    decrStackSize(getInventory(), i, 1);  //¯\_( ͡° ͜ʖ ͡°)_/¯
                }
                //world.playSound(player, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
                world.playSound(player, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 0.7F, 0.8F); // + world.rand.nextFloat());
                //world.addEntity(new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, result));
                ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5 + (Direction.NORTH.getXOffset() * 0.2), pos.getY() + 0.2, pos.getZ() + 0.5 + (Direction.NORTH.getZOffset() * 0.2), result.copy());
                entity.setMotion(Direction.NORTH.getXOffset() * 0.2F, 0.0F, Direction.NORTH.getZOffset() * 0.2F);
                world.addEntity(entity);

                if(player instanceof ServerPlayerEntity)
                {
                    ModAdvancements.MORTAR_AND_PESTLE.trigger((ServerPlayerEntity)player);
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

    public void insertStack(int slot, PlayerEntity player, Hand hand)
    {
        if(!player.isCreative())
        {
            player.setHeldItem(hand, getInventory().insertItem(slot, player.getHeldItem(hand), false));
        }
        else
        {
            getInventory().insertItem(slot, player.getHeldItem(hand).copy(), false);
        }
        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
    }

    public void extractStack(int slot, PlayerEntity player)
    {
        if(!player.isCreative())
        {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), getInventory().extractItem(slot, 64, false));
        }
        else
        {
            getInventory().extractItem(slot, 64, false);
        }
        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
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
