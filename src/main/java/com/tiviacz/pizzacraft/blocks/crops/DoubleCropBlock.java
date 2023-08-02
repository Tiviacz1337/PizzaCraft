package com.tiviacz.pizzacraft.blocks.crops;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

import java.util.function.Supplier;

public class DoubleCropBlock extends CropBlock
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.getBlock() instanceof FarmBlock;
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if(!level.isAreaLoaded(pos, 1))
        {
            return;
        }

        if(level.getRawBrightness(pos, 0) >= 9)
        {
            int age = getAge(state);
            if(age < getMaxAge())
            {
                float f = getGrowthSpeed(this, level, pos);
                if(ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
                {
                    if(age == getMaxAge() - 1 && level.getBlockState(pos).getValue(HALF) == DoubleBlockHalf.LOWER)
                    {
                        if(level.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && level.getBlockState(pos.below()).getBlock() instanceof FarmBlock)
                        {
                            level.setBlock(pos, this.getStateForAge(getMaxAge()).setValue(HALF, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                            level.setBlock(pos.above(), this.getStateForAge(0).setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                            ForgeHooks.onCropsGrowPost(level, pos, state);
                        }
                    }
                    else
                    {
                        level.setBlock(pos, this.getStateForAge(age + 1).setValue(HALF, level.getBlockState(pos).getValue(HALF)), Block.UPDATE_CLIENTS);
                    }
                    ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level)
    {
        return Mth.nextInt(level.random, 1, 2);
    }

    @Override
    protected ItemLike getBaseSeedId()
    {
        return seedItemSupplier.get();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos)) && placementChecker(level, pos);
    }

    private boolean placementChecker(LevelReader level, BlockPos pos)
    {
        BlockState testState = level.getBlockState(pos.below());

        if(testState.getBlock() instanceof FarmBlock)
        {
            return true;
        }
        return testState == this.getStateForAge(getMaxAge()).setValue(HALF, DoubleBlockHalf.LOWER) && level.getBlockState(pos.below(2)).getBlock() instanceof FarmBlock;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient)
    {
        return ((!isMaxAge(state) || level.getBlockState(pos.above()).getBlock() == Blocks.AIR) && level.getBlockState(pos.below()).getBlock() instanceof FarmBlock) || (!isMaxAge(state) && level.getBlockState(pos.below()).getBlock() instanceof DoubleCropBlock);
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state)
    {
        int newAge = getAge(state) + getBonemealAgeIncrease(level);
        int maxAge = getMaxAge();

        if(newAge >= maxAge && level.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && level.getBlockState(pos.below()).getBlock() instanceof FarmBlock)
        {
            int remainingAge = newAge - maxAge;

            level.setBlock(pos, getStateForAge(getMaxAge()), Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(), getStateForAge(remainingAge).setValue(DoubleCropBlock.HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
            return;
        }
        else if(newAge > maxAge)
        {
            newAge = maxAge;
        }
        level.setBlock(pos, getStateForAge(newAge).setValue(HALF, level.getBlockState(pos).getValue(HALF)), Block.UPDATE_CLIENTS);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if(!level.isClientSide)
        {
            Block below = level.getBlockState(pos.below()).getBlock();

            if(state.getBlock() == this && below == this)
            {
                DoubleCropBlock crop = (DoubleCropBlock)level.getBlockState(pos).getBlock();
                if(crop.getAge(state) == getMaxAge() && crop.getHalf(state) == DoubleBlockHalf.UPPER)
                {
                    level.setBlock(pos.below(), crop.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AGE, HALF);
    }
}