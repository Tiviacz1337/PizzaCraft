package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class OliveLeavesBlock extends LeavesBlock implements BonemealableBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public OliveLeavesBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(AGE, 3));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(state.getValue(AGE) == getMaxAge())
        {
            ItemStack stack = new ItemStack(ModItems.OLIVE.get(), level.random.nextInt(2) + 1);

            if(!player.addItem(stack))
            {
                ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, stack);
                level.addFreshEntity(entity);
            }

            level.setBlockAndUpdate(pos, state.setValue(AGE, 0));

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if(!level.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

        if(level.getRawBrightness(pos, 0) >= 9 && random.nextInt(5) == 0)
        {
            int i = this.getAge(state);

            if(i < this.getMaxAge())
            {
               // float f = getGrowthChance(this, worldIn, pos);

               // if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0))
               // {
                level.setBlock(pos, this.withAge(state, i + 1), 2);
                //    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
               // }
            }
        }

        if(!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7)
        {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return super.isRandomlyTicking(state) || this.getAge(state) < this.getMaxAge();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
        super.createBlockStateDefinition(builder);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return super.getStateForPlacement(context).setValue(AGE, 3);
    }

    public int getAge(BlockState state)
    {
        return state.getValue(AGE);
    }

    public int getMaxAge()
    {
        return 3;
    }

    public BlockState withAge(BlockState state, int age)
    {
        return state.setValue(AGE, age);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(AGE) < getMaxAge();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state)
    {
        return state.getValue(AGE) < getMaxAge();
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state)
    {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(level);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        level.setBlock(pos, this.withAge(state, i), 2);
    }

    protected int getBonemealAgeIncrease(Level level)
    {
        return Mth.nextInt(level.random, 1, 2);
    }
}