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
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D)};
    private final Supplier<Item> seedItemSupplier;

    public DoubleCropBlock(Properties properties, Supplier<Item> seedItemSupplier)
    {
        super(properties);
        this.seedItemSupplier = seedItemSupplier;
        this.registerDefaultState(getStateDefinition().any().setValue(getAgeProperty(), 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos)
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
        return state.getValue(HALF);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        if(!worldIn.isAreaLoaded(pos, 1))
        {
            return;
        }

        if(worldIn.getRawBrightness(pos, 0) >= 9)
        {
            int age = getAge(state);
            if(age < getMaxAge())
            {
                float f = getGrowthSpeed(this, worldIn, pos);
                if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
                {
                    if(age == getMaxAge() - 1 && worldIn.getBlockState(pos).getValue(HALF) == DoubleBlockHalf.LOWER)
                    {
                        if(worldIn.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmlandBlock)
                        {
                            worldIn.setBlock(pos, this.getStateForAge(getMaxAge()).setValue(HALF, DoubleBlockHalf.LOWER), Constants.BlockFlags.BLOCK_UPDATE);
                            worldIn.setBlock(pos.above(), this.getStateForAge(0).setValue(HALF, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                        }
                    }
                    else
                    {
                        worldIn.setBlock(pos, this.getStateForAge(age + 1).setValue(HALF, worldIn.getBlockState(pos).getValue(HALF)), Constants.BlockFlags.BLOCK_UPDATE);
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.nextInt(worldIn.random, 1, 2);
    }

    @Override
    protected IItemProvider getBaseSeedId()
    {
        return seedItemSupplier.get();
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return (worldIn.getRawBrightness(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && placementChecker(worldIn, pos);
    }

    private boolean placementChecker(IWorldReader worldIn, BlockPos pos)
    {
        BlockState testState = worldIn.getBlockState(pos.below());

        if(testState.getBlock() instanceof FarmlandBlock)
        {
            return true;
        }
        return testState == this.getStateForAge(getMaxAge()).setValue(HALF, DoubleBlockHalf.LOWER) && worldIn.getBlockState(pos.below(2)).getBlock() instanceof FarmlandBlock;
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
    {
        return ((!isMaxAge(state) || worldIn.getBlockState(pos.above()).getBlock() == Blocks.AIR) && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmlandBlock) || (!isMaxAge(state) && worldIn.getBlockState(pos.below()).getBlock() instanceof DoubleCropBlock);
    }

    @Override
    public void growCrops(World worldIn, BlockPos pos, BlockState state)
    {
        int newAge = getAge(state) + getBonemealAgeIncrease(worldIn);
        int maxAge = getMaxAge();

        if(newAge >= maxAge && worldIn.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmlandBlock)
        {
            int remainingAge = newAge - maxAge;

            worldIn.setBlock(pos, getStateForAge(getMaxAge()), Constants.BlockFlags.BLOCK_UPDATE);
            worldIn.setBlock(pos.above(), getStateForAge(remainingAge).setValue(DoubleCropBlock.HALF, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
            return;
        }
        else if(newAge > maxAge)
        {
            newAge = maxAge;
        }
        worldIn.setBlock(pos, getStateForAge(newAge).setValue(HALF, worldIn.getBlockState(pos).getValue(HALF)), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if(!worldIn.isClientSide)
        {
            Block below = worldIn.getBlockState(pos.below()).getBlock();

            if(state.getBlock() == this && below == this)
            {
                DoubleCropBlock crop = (DoubleCropBlock)worldIn.getBlockState(pos).getBlock();
                if(crop.getAge(state) == getMaxAge() && crop.getHalf(state) == DoubleBlockHalf.UPPER)
                {
                    worldIn.setBlock(pos.below(), crop.defaultBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                }
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(AGE, HALF);
    }
}