package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.tileentity.MortarAndPestleTileEntity;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MortarAndPestleBlock extends Block
{
    public static final IntegerProperty PESTLE = BlockStateProperties.AGE_0_3;
    private static final VoxelShape INSIDE = makeCuboidShape(5.0D, 1.0D, 5.0D, 11.0D, 4.0D, 11.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D), INSIDE, IBooleanFunction.ONLY_FIRST);

    public MortarAndPestleBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(PESTLE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(worldIn.getTileEntity(pos) instanceof MortarAndPestleTileEntity)
        {
            MortarAndPestleTileEntity mortarTile = (MortarAndPestleTileEntity)worldIn.getTileEntity(pos);
            ItemStack itemHeld = player.getHeldItem(handIn);

            if(itemHeld.isEmpty())
            {
                if(player.isSneaking())
                {
                    movePestle(worldIn, pos, state);

                    if(mortarTile.canMix(player))
                    {
                        mortarTile.mix(worldIn, player);
                    }
                    return ActionResultType.SUCCESS;
                }

                else if(!mortarTile.isEmpty(mortarTile.getInventory()))
                {
                    mortarTile.extractStack(Utils.getProperSlotForExtract(mortarTile.getInventory()), player);
                    return ActionResultType.SUCCESS;
                }
            }
            else if(mortarTile.getInventory().getStackInSlot(3).isEmpty())
            {
                mortarTile.insertStack(Utils.getProperSlotForInsert(itemHeld, mortarTile.getInventory()), player, handIn);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    private void movePestle(World worldIn, BlockPos pos, BlockState state)
    {
        // Cycle property
        worldIn.setBlockState(pos, state.func_235896_a_(PESTLE));
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(world.getTileEntity(pos) instanceof MortarAndPestleTileEntity)
            {
                IItemHandlerModifiable inventory = ((MortarAndPestleTileEntity)world.getTileEntity(pos)).getInventory();

                for(int i = 0; i < inventory.getSlots(); i++)
                {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                }
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(PESTLE);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new MortarAndPestleTileEntity();
    }
}