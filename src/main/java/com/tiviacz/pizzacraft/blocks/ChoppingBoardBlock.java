package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.KnifeItem;
import com.tiviacz.pizzacraft.tileentity.ChoppingBoardTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;

public class ChoppingBoardBlock extends Block //HorizontalBlock
{
    //public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public ChoppingBoardBlock(Properties properties)
    {
        super(properties);
        //this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack[] possibleDrops = new ItemStack[]{
                ModItems.BROCCOLI_SEEDS.get().getDefaultInstance(),
                ModItems.CUCUMBER_SEEDS.get().getDefaultInstance(),
                ModItems.PEPPER_SEEDS.get().getDefaultInstance(),
                ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance(),
                ModItems.TOMATO_SEEDS.get().getDefaultInstance(),
                ModItems.CORN.get().getDefaultInstance(),
                ModItems.ONION.get().getDefaultInstance()};

       // System.out.println(possibleDrops.length - 1);
        //System.out.println(possibleDrops.size());
        //System.out.println(possibleDrops.get(s));
        System.out.println(worldIn.getRandom().nextInt(possibleDrops.length));

        if(worldIn.getBlockEntity(pos) instanceof ChoppingBoardTileEntity)
        {
            ChoppingBoardTileEntity choppingBoardTile = (ChoppingBoardTileEntity)worldIn.getBlockEntity(pos);
            ItemStack itemHeld = player.getItemInHand(handIn);
            ItemStack itemOffhand = player.getOffhandItem();

            // Placing items on the board. It should prefer off-hand placement, unless it's a BlockItem (since it never passes to off-hand...)
            if(choppingBoardTile.isEmpty())
            {
                if(!itemOffhand.isEmpty() && handIn == Hand.MAIN_HAND && !(itemHeld.getItem() instanceof BlockItem))
                {
                    return ActionResultType.PASS; // main-hand passes to off-hand
                }

                if(itemHeld.isEmpty())
                {
                    return ActionResultType.PASS;
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
                    worldIn.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.random.nextFloat());
                    return ActionResultType.SUCCESS;
                }
            }
            // Processing the item with the held tool
            else if(!itemHeld.isEmpty())
            {
                if(choppingBoardTile.canChop(itemHeld))
                {
                    choppingBoardTile.chop(itemHeld, player);
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.PASS;
            }
            // Removing the board's item
            else if(handIn == Hand.MAIN_HAND && !choppingBoardTile.removeItem().isEmpty())
            {
                if(!player.isCreative())
                {
                    InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), choppingBoardTile.getInventory().extractItem(0, 64, false));
                }
                else
                {
                    choppingBoardTile.getInventory().extractItem(0, 64, false);
                }
                worldIn.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.random.nextFloat());
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;

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
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(world.getBlockEntity(pos) instanceof ChoppingBoardTileEntity)
            {
                IItemHandler inventory = ((ChoppingBoardTileEntity)world.getBlockEntity(pos)).getInventory();

                for(int i = 0; i < inventory.getSlots(); i++)
                {
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                }
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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
    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        if(blockAccess.getBlockEntity(pos) instanceof ChoppingBoardTileEntity)
        {
            ItemStack storedStack = ((ChoppingBoardTileEntity)blockAccess.getBlockEntity(pos)).getStoredStack();
            return !storedStack.isEmpty() ? 15 : 0;
        }
        return 0;
    }

   // @Override
    //protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) { builder.add(FACING); }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ChoppingBoardTileEntity();
    }

    @Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ToolCarvingEvent
    {
        @SubscribeEvent
        public static void onSneakPlaceTool(PlayerInteractEvent.RightClickBlock event)
        {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            PlayerEntity player = event.getPlayer();
            ItemStack heldItem = player.getMainHandItem();
            TileEntity tile = world.getBlockEntity(event.getPos());

            if(player.isSecondaryUseActive() && !heldItem.isEmpty() && tile instanceof ChoppingBoardTileEntity)
            {
                if(heldItem.getItem() instanceof KnifeItem || heldItem.getItem() instanceof TieredItem || heldItem.getItem() instanceof TridentItem || heldItem.getItem() instanceof ShearsItem)
                {
                    boolean success = ((ChoppingBoardTileEntity)tile).carveToolOnBoard(player.abilities.instabuild ? heldItem.copy() : heldItem);

                    if(success)
                    {
                        ((ChoppingBoardTileEntity)tile).setFacing(player.getDirection().getOpposite());
                        world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
                        event.setCanceled(true);
                        event.setCancellationResult(ActionResultType.SUCCESS);
                    }
                }
            }
        }
    }
}