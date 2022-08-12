package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.blockentity.BasinBlockEntity;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BasinBlock extends Block implements EntityBlock
{
    //private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    private static final VoxelShape SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    //protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), INSIDE, IBooleanFunction.ONLY_FIRST);

    public BasinBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(worldIn.getBlockEntity(pos) instanceof BasinBlockEntity)
        {
            return ((BasinBlockEntity)worldIn.getBlockEntity(pos)).onBlockActivated(player, handIn);
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(level.getBlockEntity(pos) instanceof BasinBlockEntity && entityIn instanceof Player)
        {
            ((BasinBlockEntity)level.getBlockEntity(pos)).crush((Player)entityIn);
        }
        super.fallOn(level, state, pos, entityIn, fallDistance);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(world.getBlockEntity(pos) instanceof BasinBlockEntity)
            {
                IItemHandler inventory = ((BasinBlockEntity)world.getBlockEntity(pos)).getInventory();

                for(int i = 0; i < inventory.getSlots(); i++)
                {
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                }
                ((BasinBlockEntity)world.getBlockEntity(pos)).setSquashedStackCount(0);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        if(level.getBlockEntity(pos) instanceof BasinBlockEntity)
        {
            return ((BasinBlockEntity)level.getBlockEntity(pos)).getComparatorOutput();
        }
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BasinBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return Utils.getTicker(blockEntityType, ModBlockEntityTypes.BASIN.get(), BasinBlockEntity::tick);
    }

  /*  @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createBasinTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends BasinBlockEntity> blockEntity)
    {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntity, BasinBlockEntity::serverTick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    } */
}
