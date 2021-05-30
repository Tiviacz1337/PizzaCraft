package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.KnifeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
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
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

public class CheeseBlock extends Block
{
    private static final IntegerProperty BITES = BlockStateProperties.AGE_0_2;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.makeCuboidShape(6.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.makeCuboidShape(10.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D)};

    public CheeseBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.get(BITES)];
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemstack = player.getHeldItem(handIn);

        if(worldIn.isRemote)
        {
            if(this.eatSlice(worldIn, pos, state, player).isSuccessOrConsume())
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
            worldIn.addEntity(new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModItems.CHEESE.get().getDefaultInstance()));
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FUNGUS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if(itemstack.attemptDamageItem(1, worldIn.rand, player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null))
            {
                player.getHeldItem(handIn).shrink(1);
            }

            int i = state.get(BITES);
            if(i < 2)
            {
                worldIn.setBlockState(pos, state.with(BITES, i + 1), 3);
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
            player.getFoodStats().addStats(4, 4.0F);
            int i = state.get(BITES);
            if (i < 2)
            {
                world.setBlockState(pos, state.with(BITES, i + 1), 3);
            }
            else
            {
                world.removeBlock(pos, false);
            }
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(BITES);
    }
}