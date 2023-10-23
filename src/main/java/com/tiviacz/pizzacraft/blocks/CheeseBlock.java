package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CheeseBlock extends Block
{
    private static final IntegerProperty BITES = BlockStateProperties.AGE_2;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(6.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(10.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D)};

    public CheeseBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(BITES)];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);

        if(level.isClientSide)
        {
            if(this.eatSlice(level, pos, state, player).consumesAction())
            {
                return InteractionResult.SUCCESS;
            }

            if(itemstack.isEmpty())
            {
                return InteractionResult.CONSUME;
            }
        }

        if(itemstack.is(ModTags.KNIVES) || itemstack.getItem() instanceof TieredItem || itemstack.getItem() instanceof TridentItem || itemstack.getItem() instanceof ShearsItem)
        {
            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModItems.CHEESE.get().getDefaultInstance()));
            level.playSound(null, pos, SoundEvents.FUNGUS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

            if(itemstack.hurt(1, level.random, player instanceof ServerPlayer ? (ServerPlayer)player : null))
            {
                player.getItemInHand(handIn).shrink(1);
            }

            int i = state.getValue(BITES);
            if(i < 2)
            {
                level.setBlockAndUpdate(pos, state.setValue(BITES, i + 1));
            }
            else
            {
                level.removeBlock(pos, false);
            }

            return InteractionResult.SUCCESS;
        }

        return this.eatSlice(level, pos, state, player);
    }

    private InteractionResult eatSlice(LevelAccessor level, BlockPos pos, BlockState state, Player player)
    {
        if(!player.canEat(false))
        {
            return InteractionResult.PASS;
        }
        else
        {
            player.getFoodData().eat(4, 4.0F);
            level.gameEvent(player, GameEvent.EAT, pos);

            int i = state.getValue(BITES);
            if (i < 2)
            {
                level.setBlock(pos, state.setValue(BITES, i + 1), 3);
            }
            else
            {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(BITES);
    }
}