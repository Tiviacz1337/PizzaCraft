package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.ChoppingBoardBlockEntity;
import com.tiviacz.pizzacraft.items.KnifeItem;
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
    //public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public ChoppingBoardBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        //this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);
    /*    switch(state.get(HORIZONTAL_FACING))
        {
            case NORTH:
            case SOUTH:
                return Block.makeCuboidShape(2.0D, 0.0D, 4.0D, 14.0D, 0.75D, 12.0D);
            default:
                return Block.makeCuboidShape(4.0D, 0.0D, 2.0D, 12.0D, 0.75D, 14.0D);
        } */
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(level.getBlockEntity(pos) instanceof ChoppingBoardBlockEntity)
        {
            ChoppingBoardBlockEntity choppingBoardTile = (ChoppingBoardBlockEntity)level.getBlockEntity(pos);
            ItemStack itemHeld = player.getItemInHand(handIn);
            ItemStack itemOffhand = player.getOffhandItem();

            // Placing items on the board. It should prefer off-hand placement, unless it's a BlockItem (since it never passes to off-hand...)
            if(choppingBoardTile.isEmpty())
            {
                if(!itemOffhand.isEmpty() && handIn == InteractionHand.MAIN_HAND && !(itemHeld.getItem() instanceof BlockItem))
                {
                    return InteractionResult.PASS; // main-hand passes to off-hand
                }

                if(itemHeld.isEmpty())
                {
                    return InteractionResult.PASS;
                }

                else if(choppingBoardTile.addItem(itemHeld))
                {
                    if(!player.isCreative())
                    {
                        choppingBoardTile.setFacing(player.getDirection().getOpposite());
                        player.setItemInHand(handIn, choppingBoardTile.getInventory().insertItem(0, itemHeld, false));
                    }
                    else
                    {
                        choppingBoardTile.setFacing(player.getDirection().getOpposite());
                        choppingBoardTile.getInventory().insertItem(0, itemHeld.copy(), false);
                    }
                    level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                    return InteractionResult.SUCCESS;
                }
            }
            // Processing the item with the held tool
            else if(!itemHeld.isEmpty())
            {
                if(choppingBoardTile.canChop(itemHeld))
                {
                    choppingBoardTile.chop(itemHeld, player);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
            // Removing the board's item
            else if(handIn == InteractionHand.MAIN_HAND && !choppingBoardTile.removeItem().isEmpty())
            {
                if(!player.isCreative())
                {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), choppingBoardTile.getInventory().extractItem(0, 64, false));
                }
                else
                {
                    choppingBoardTile.getInventory().extractItem(0, 64, false);
                }
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;

     /*   if(worldIn.getTileEntity(pos) instanceof ChoppingBoardTileEntity)
        {
            ChoppingBoardTileEntity tile = (ChoppingBoardTileEntity)worldIn.getTileEntity(pos);

            if(tile.canChop(stack))
            {
                tile.chop(player, handIn);
                return ActionResultType.SUCCESS;
            }

            if(tile.canInsert(stack))
            {
                tile.getInventory().insertItem(0, stack, false);
                worldIn.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.rand.nextFloat());
                return ActionResultType.SUCCESS;
            }

            if(tile.canExtract(player, handIn))
            {
                tile.getInventory().extractItem(0, 64, false);
                return ActionResultType.SUCCESS;
            }
        ItemStack stack = player.getHeldItem(handIn);

        if(worldIn.getTileEntity(pos) instanceof ChoppingBoardTileEntity && handIn == Hand.MAIN_HAND)
        {
            ChoppingBoardTileEntity tile = (ChoppingBoardTileEntity)worldIn.getTileEntity(pos);

            if(tile.canChop(stack) && canHit(state.get(FACING), hit, pos, 0))
            {
                tile.chop(player, handIn);
                if(!worldIn.isRemote)
                {
                    stack.damageItem(1, player, e -> e.sendBreakAnimation(handIn));
                }
                return ActionResultType.SUCCESS;
            }

            if(tile.insert(stack, 0, canHit(state.get(FACING), hit, pos, 0)))
            {
             //   worldIn.playSound(player, pos.getX() + 0.5, pos.getY() + 0.3D, pos.getZ() + 0.5D, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.rand.nextFloat());
                worldIn.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.rand.nextFloat());
                return ActionResultType.SUCCESS;
            }

            if(tile.extract(0, canHit(state.get(FACING), hit, pos, 0), player, handIn) || tile.extract(1, canHit(state.get(FACING), hit, pos, 1), player, handIn))
            {
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS; */
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(level.getBlockEntity(pos) instanceof ChoppingBoardBlockEntity)
            {
                IItemHandler inventory = ((ChoppingBoardBlockEntity)level.getBlockEntity(pos)).getInventory();

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

   // @Override
   // public BlockState getStateForPlacement(BlockItemUseContext context)
   // {
        //return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
  //  }

    @Override
    public boolean isSignalSource(BlockState state)
    {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        if(level.getBlockEntity(pos) instanceof ChoppingBoardBlockEntity)
        {
            ItemStack storedStack = ((ChoppingBoardBlockEntity)level.getBlockEntity(pos)).getStoredStack();
            return !storedStack.isEmpty() ? 15 : 0;
        }
        return 0;
    }

   // @Override
    //protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) { builder.add(FACING); }

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
            BlockEntity blockEntity = level.getBlockEntity(event.getPos());

            if(player.isSecondaryUseActive() && !heldItem.isEmpty() && blockEntity instanceof ChoppingBoardBlockEntity)
            {
                if(heldItem.getItem() instanceof KnifeItem || heldItem.getItem() instanceof TieredItem || heldItem.getItem() instanceof TridentItem || heldItem.getItem() instanceof ShearsItem)
                {
                    boolean success = ((ChoppingBoardBlockEntity)blockEntity).carveToolOnBoard(player.getAbilities().instabuild ? heldItem.copy() : heldItem);

                    if(success)
                    {
                        ((ChoppingBoardBlockEntity)blockEntity).setFacing(player.getDirection().getOpposite());
                        level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }
}