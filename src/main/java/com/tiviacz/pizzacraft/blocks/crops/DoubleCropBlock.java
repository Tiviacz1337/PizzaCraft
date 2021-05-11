package com.tiviacz.pizzacraft.blocks.crops;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Supplier;

public class DoubleCropBlock extends CropsBlock
{
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D)};
    private final Supplier<Item> seedItemSupplier;

    public DoubleCropBlock(Properties properties, Supplier<Item> seedItemSupplier)
    {
        super(properties);
        this.seedItemSupplier = seedItemSupplier;
        this.setDefaultState(stateContainer.getBaseState().with(getAgeProperty(), 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return state.getBlock() instanceof FarmlandBlock;
    }

    @Override
    public IntegerProperty getAgeProperty()
    {
        return AGE;
    }

    @Override
    public int getMaxAge()
    {
        return 3;
    }

    public DoubleBlockHalf getHalf(BlockState state)
    {
        return state.get(HALF);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        if(!worldIn.isAreaLoaded(pos, 1))
        {
            return;
        }

        if(worldIn.getLightSubtracted(pos, 0) >= 9)
        {
            int age = getAge(state);
            if(age < getMaxAge())
            {
                float f = getGrowthChance(this, worldIn, pos);
                if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
                {
                    if(age == getMaxAge() - 1 && worldIn.getBlockState(pos).get(HALF) == DoubleBlockHalf.LOWER)
                    {
                        if(worldIn.getBlockState(pos.up()) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock)
                        {
                            worldIn.setBlockState(pos, this.withAge(getMaxAge()).with(HALF, DoubleBlockHalf.LOWER), Constants.BlockFlags.BLOCK_UPDATE);
                            worldIn.setBlockState(pos.up(), this.withAge(0).with(HALF, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                        }
                    }
                    else
                    {
                        worldIn.setBlockState(pos, this.withAge(age + 1).with(HALF, worldIn.getBlockState(pos).get(HALF)), Constants.BlockFlags.BLOCK_UPDATE);
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.nextInt(worldIn.rand, 1, 2);
    }

    @Override
    protected IItemProvider getSeedsItem()
    {
        return seedItemSupplier.get();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && placementChecker(worldIn, pos);
    }

    private boolean placementChecker(IWorldReader worldIn, BlockPos pos)
    {
        BlockState testState = worldIn.getBlockState(pos.down());

        if(testState.getBlock() instanceof FarmlandBlock)
        {
            return true;
        }
        return testState == this.withAge(getMaxAge()).with(HALF, DoubleBlockHalf.LOWER) && worldIn.getBlockState(pos.down(2)).getBlock() instanceof FarmlandBlock;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
    {
        return ((!isMaxAge(state) || worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) && worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock) || (!isMaxAge(state) && worldIn.getBlockState(pos.down()).getBlock() instanceof DoubleCropBlock);
    }

    @Override
    public void grow(World worldIn, BlockPos pos, BlockState state)
    {
        int newAge = getAge(state) + getBonemealAgeIncrease(worldIn);
        int maxAge = getMaxAge();

        if(newAge >= maxAge && worldIn.getBlockState(pos.up()) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock)
        {
            int remainingAge = newAge - maxAge;

            worldIn.setBlockState(pos, withAge(getMaxAge()), Constants.BlockFlags.BLOCK_UPDATE);
            worldIn.setBlockState(pos.up(), withAge(remainingAge).with(DoubleCropBlock.HALF, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
            return;
        }
        else if(newAge > maxAge)
        {
            newAge = maxAge;
        }
        worldIn.setBlockState(pos, withAge(newAge).with(HALF, worldIn.getBlockState(pos).get(HALF)), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if(!worldIn.isRemote)
        {
            Block below = worldIn.getBlockState(pos.down()).getBlock();

            if(state.getBlock() == this && below == this)
            {
                DoubleCropBlock crop = (DoubleCropBlock)worldIn.getBlockState(pos).getBlock();
                if(crop.getAge(state) == getMaxAge() && crop.getHalf(state) == DoubleBlockHalf.UPPER)
                {
                    worldIn.setBlockState(pos.down(), crop.getDefaultState(), Constants.BlockFlags.BLOCK_UPDATE);
                }
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(AGE, HALF);
    }
}