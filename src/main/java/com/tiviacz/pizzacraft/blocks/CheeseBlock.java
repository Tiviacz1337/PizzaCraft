package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.KnifeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.TieredItem;
import net.minecraft.item.TridentItem;
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

public class CheeseBlock extends Block
{
    private static final IntegerProperty BITES = BlockStateProperties.AGE_2;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(6.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(10.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D)};

    public CheeseBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.getValue(BITES)];
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);

        if(worldIn.isClientSide)
        {
            if(this.eatSlice(worldIn, pos, state, player).consumesAction())
            {
                return ActionResultType.SUCCESS;
            }

            if(itemstack.isEmpty())
            {
                return ActionResultType.CONSUME;
            }
        }

        if(itemstack.getItem() instanceof KnifeItem || itemstack.getItem() instanceof TieredItem || itemstack.getItem() instanceof TridentItem || itemstack.getItem() instanceof ShearsItem)
        {
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModItems.CHEESE.get().getDefaultInstance()));
            worldIn.playSound(null, pos, SoundEvents.FUNGUS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if(itemstack.hurt(1, worldIn.random, player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null))
            {
                player.getItemInHand(handIn).shrink(1);
            }

            int i = state.getValue(BITES);
            if(i < 2)
            {
                worldIn.setBlock(pos, state.setValue(BITES, i + 1), 3);
            }
            else
            {
                worldIn.removeBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }

        return this.eatSlice(worldIn, pos, state, player);
    }

    private ActionResultType eatSlice(IWorld world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if(!player.canEat(false))
        {
            return ActionResultType.PASS;
        }
        else
        {
            player.getFoodData().eat(4, 4.0F);
            int i = state.getValue(BITES);
            if (i < 2)
            {
                world.setBlock(pos, state.setValue(BITES, i + 1), 3);
            }
            else
            {
                world.removeBlock(pos, false);
            }
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(BITES);
    }
}