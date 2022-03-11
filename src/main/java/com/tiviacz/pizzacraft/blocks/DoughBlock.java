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
    public static final IntegerProperty KNEEDING = BlockStateProperties.AGE_5;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
            Block.box(3.0D, 0.0D, 4.0D, 11.0D, 4.0D, 11.0D),
            Block.box(4.0D, 0.0D, 5.0D, 10.0D, 5.0D, 12.0D),
            Block.box(5.0D, 0.0D, 2.0D, 13.0D, 3.0D, 11.0D),
            Block.box(3.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D)};

    public DoughBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(KNEEDING, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.getValue(KNEEDING)];
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getItemInHand(handIn);

        if(player.getItemInHand(handIn).getItem() == ModItems.ROLLING_PIN.get())
        {
            proceedKneeding(state, worldIn, pos, player);
            stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(handIn));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    public void proceedKneeding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
    {
        int i = state.getValue(KNEEDING);

        if(i < 5)
        {
            worldIn.setBlock(pos, state.setValue(KNEEDING, i + 1), 3);
        }
        else
        {
            worldIn.setBlock(pos, ModBlocks.RAW_PIZZA.get().defaultBlockState(), 3);
        }
        worldIn.playSound(player, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 0.7F, 0.8F + worldIn.random.nextFloat());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(KNEEDING);
    }

    @Override
    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        return (blockState.getValue(KNEEDING) + 1) * 2;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

}