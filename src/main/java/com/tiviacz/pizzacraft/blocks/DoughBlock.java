package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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

    public DoughBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(KNEEDING, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(KNEEDING)];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(handIn);

        if(player.getItemInHand(handIn).getItem() == ModItems.ROLLING_PIN.get())
        {
            proceedKneeding(state, level, pos, player);
            stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(handIn));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void proceedKneeding(BlockState state, Level level, BlockPos pos, Player player)
    {
        int i = state.getValue(KNEEDING);

        if(i < 5)
        {
            level.setBlockAndUpdate(pos, state.setValue(KNEEDING, i + 1));
        }
        else
        {
            level.setBlockAndUpdate(pos, ModBlocks.RAW_PIZZA.get().defaultBlockState());
        }
        level.playSound(player, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).isSolid();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KNEEDING);
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter level, BlockPos pos, Direction side)
    {
        return (blockState.getValue(KNEEDING) + 1) * 2;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

}