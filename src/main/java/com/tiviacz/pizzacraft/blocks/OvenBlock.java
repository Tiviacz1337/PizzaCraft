package com.tiviacz.pizzacraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class OvenBlock extends Block
{
   // protected static final VoxelShape SHAPE = makeCuboidShape(0.0D, 0.0D, 0.0D, )
    //public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public OvenBlock(Properties properties)
    {
        super(properties);
        //this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
    }

  ////  @Override
   // public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
  //  {
  //      return SHAPE;
   // }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.3;
        double d2 = (double)pos.getZ() + 0.5D;

        if(rand.nextDouble() < 0.1D)
        {
            worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        Direction direction = stateIn.get(FACING);
        Direction.Axis direction$axis = direction.getAxis();
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        double d5 = direction$axis == Direction.Axis.X ? (double)direction.getXOffset() * 0.52D : d4;
        double d6 = rand.nextDouble() * 6.0D / 16.0D;
        double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getZOffset() * 0.52D : d4;
        worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
   // {
  /*      if(stateIn.get(LIT))
        {
            double d0 = pos.getX();
            double d1 = pos.getY();
            double d2 = pos.getZ();

            if(rand.nextDouble() < 0.1D)
            {
                worldIn.playSound(d0 + 0.5D, d1, d2 + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            for(int i = 0; i < 5; i++)
            {
                worldIn.addParticle(ParticleTypes.SMOKE, (d0 + 0.25) + rand.nextDouble() * 0.5, d1 + 0.3, (d2 + 0.25) + rand.nextDouble() * 0.5, 0.0D, 0.0D, 0.0D);
                worldIn.addParticle(ParticleTypes.FLAME, (d0 + 0.25) + rand.nextDouble() * 0.5, d1 + 0.3, (d2 + 0.25) + rand.nextDouble() * 0.5, 0.0D, 0.0D, 0.0D);
            }
        } */
  //  }

   /* @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        TileEntity tile = worldIn.getTileEntity(pos);

        if(tile instanceof OvenTileEntity)
        {
            return ((OvenTileEntity)tile).onBlockActivated(player, handIn);
        }
        return ActionResultType.FAIL;
    } */

 /*  @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(world.getTileEntity(pos) instanceof OvenTileEntity)
            {
                IItemHandler handler = ((OvenTileEntity)world.getTileEntity(pos)).getInventory();
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(0));
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    } */

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

  /*  @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    } */

  /*  @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new OvenTileEntity();
    } */
}