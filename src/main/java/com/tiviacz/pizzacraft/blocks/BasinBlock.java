package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.tileentity.BasinTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BasinBlock extends Block
{
    //private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    private static final VoxelShape SHAPE = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    //protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), INSIDE, IBooleanFunction.ONLY_FIRST);

    public BasinBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(worldIn.getTileEntity(pos) instanceof BasinTileEntity)
        {
            return ((BasinTileEntity)worldIn.getTileEntity(pos)).onBlockActivated(player, handIn);
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(worldIn.getTileEntity(pos) instanceof BasinTileEntity && entityIn instanceof PlayerEntity)
        {
            ((BasinTileEntity)worldIn.getTileEntity(pos)).crush((PlayerEntity)entityIn);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(world.getTileEntity(pos) instanceof BasinTileEntity)
            {
                IItemHandler inventory = ((BasinTileEntity)world.getTileEntity(pos)).getInventory();

                for(int i = 0; i < inventory.getSlots(); i++)
                {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                }
                ((BasinTileEntity)world.getTileEntity(pos)).setSquashedStackCount(0);
                world.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
    {
        if(worldIn.getTileEntity(pos) instanceof BasinTileEntity)
        {
            return ((BasinTileEntity)worldIn.getTileEntity(pos)).getComparatorOutput();
        }
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new BasinTileEntity();
    }
}
