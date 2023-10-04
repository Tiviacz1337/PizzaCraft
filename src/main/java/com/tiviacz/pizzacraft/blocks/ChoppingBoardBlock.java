package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.ChoppingBoardBlockEntity;
import com.tiviacz.pizzacraft.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;

public class ChoppingBoardBlock extends Block implements EntityBlock
{
    public ChoppingBoardBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(level.getBlockEntity(pos) instanceof ChoppingBoardBlockEntity blockEntity)
        {
            ItemStack itemHeld = player.getItemInHand(handIn);
            ItemStack itemOffhand = player.getOffhandItem();

            // Placing items on the board. It should prefer off-hand placement, unless it's a BlockItem (since it never passes to off-hand...)
            if(blockEntity.isEmpty())
            {
                if(!itemOffhand.isEmpty() && handIn == InteractionHand.MAIN_HAND && !(itemHeld.getItem() instanceof BlockItem))
                {
                    return InteractionResult.PASS; // main-hand passes to off-hand
                }

                if(itemHeld.isEmpty())
                {
                    return InteractionResult.PASS;
                }

                else if(blockEntity.addItem(itemHeld))
                {
                    if(!player.isCreative())
                    {
                        blockEntity.setFacing(player.getDirection().getOpposite());
                        player.setItemInHand(handIn, blockEntity.getInventory().insertItem(0, itemHeld, false));
                    }
                    else
                    {
                        blockEntity.setFacing(player.getDirection().getOpposite());
                        blockEntity.getInventory().insertItem(0, itemHeld.copy(), false);
                    }
                    level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                    return InteractionResult.SUCCESS;
                }
            }
            // Processing the item with the held tool
            else if(!itemHeld.isEmpty())
            {
                if(blockEntity.canChop(itemHeld))
                {
                    blockEntity.chop(itemHeld, player);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
            // Removing the board's item
            else if(handIn == InteractionHand.MAIN_HAND && !blockEntity.removeItem().isEmpty())
            {
                if(!player.isCreative())
                {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), blockEntity.getInventory().extractItem(0, 64, false));
                }
                else
                {
                    blockEntity.getInventory().extractItem(0, 64, false);
                }
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(level.getBlockEntity(pos) instanceof ChoppingBoardBlockEntity blockEntity)
            {
                IItemHandler inventory = blockEntity.getInventory();

                for(int i = 0; i < inventory.getSlots(); i++)
                {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                }
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean isSignalSource(BlockState state)
    {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        if(level.getBlockEntity(pos) instanceof ChoppingBoardBlockEntity blockEntity)
        {
            ItemStack storedStack = blockEntity.getStoredStack();
            return !storedStack.isEmpty() ? 15 : 0;
        }
        return 0;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChoppingBoardBlockEntity(pos, state);
    }

    @Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ToolCarvingEvent
    {
        @SubscribeEvent
        public static void onSneakPlaceTool(PlayerInteractEvent.RightClickBlock event)
        {
            Level level = event.getWorld();
            BlockPos pos = event.getPos();
            Player player = event.getPlayer();
            ItemStack heldItem = player.getMainHandItem();

            if(player.isSecondaryUseActive() && !heldItem.isEmpty() && level.getBlockEntity(event.getPos()) instanceof ChoppingBoardBlockEntity blockEntity)
            {
                if(heldItem.is(ModTags.KNIVES) || heldItem.getItem() instanceof TieredItem || heldItem.getItem() instanceof TridentItem || heldItem.getItem() instanceof ShearsItem)
                {
                    boolean success = blockEntity.carveToolOnBoard(player.getAbilities().instabuild ? heldItem.copy() : heldItem);

                    if(success)
                    {
                        blockEntity.setFacing(player.getDirection().getOpposite());
                        level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }
}