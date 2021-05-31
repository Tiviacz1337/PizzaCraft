package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DoughBlock extends Block
{
    public static final IntegerProperty KNEEDING = BlockStateProperties.AGE_0_5;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
            Block.makeCuboidShape(3.0D, 0.0D, 4.0D, 11.0D, 4.0D, 11.0D),
            Block.makeCuboidShape(4.0D, 0.0D, 5.0D, 10.0D, 5.0D, 12.0D),
            Block.makeCuboidShape(5.0D, 0.0D, 2.0D, 13.0D, 3.0D, 11.0D),
            Block.makeCuboidShape(3.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D),
            Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D)};

    public DoughBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(KNEEDING, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.get(KNEEDING)];
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getHeldItem(handIn);

        if(player.getHeldItem(handIn).getItem() == ModItems.ROLLING_PIN.get())
        {
            proceedKneeding(state, worldIn, pos, player);
            stack.damageItem(1, player, e -> e.sendBreakAnimation(handIn));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    public void proceedKneeding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
    {
        int i = state.get(KNEEDING);

        if(i < 5)
        {
            worldIn.setBlockState(pos, state.with(KNEEDING, i + 1), 3);
        }
        else
        {
            worldIn.setBlockState(pos, ModBlocks.RAW_PIZZA.get().getDefaultState(), 3);
        }
        worldIn.playSound(player, pos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.rand.nextFloat());
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(KNEEDING);
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
    {
        return (blockState.get(KNEEDING) + 1) * 2;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

}