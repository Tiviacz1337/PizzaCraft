package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.blockentity.MortarAndPestleBlockEntity;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MortarAndPestleBlock extends Block implements EntityBlock
{
    public static final IntegerProperty PESTLE = BlockStateProperties.AGE_3;
    private static final VoxelShape INSIDE = box(5.0D, 1.0D, 5.0D, 11.0D, 4.0D, 11.0D);
    protected static final VoxelShape SHAPE = Shapes.join(box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D), INSIDE, BooleanOp.ONLY_FIRST);

    public MortarAndPestleBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(PESTLE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(level.getBlockEntity(pos) instanceof MortarAndPestleBlockEntity)
        {
            MortarAndPestleBlockEntity mortarTile = (MortarAndPestleBlockEntity)level.getBlockEntity(pos);
            ItemStack itemHeld = player.getItemInHand(handIn);

            if(itemHeld.isEmpty())
            {
                if(player.isCrouching())
                {
                    movePestle(level, pos, state);

                    if(mortarTile.canMix(player))
                    {
                        mortarTile.mix(level, player);
                    }
                    return InteractionResult.SUCCESS;
                }

                else if(!mortarTile.isEmpty(mortarTile.getInventory()))
                {
                    mortarTile.extractStack(Utils.getProperSlotForExtract(mortarTile.getInventory()), player);
                    return InteractionResult.SUCCESS;
                }
            }
            else if(mortarTile.getInventory().getStackInSlot(3).isEmpty())
            {
                mortarTile.insertStack(Utils.getProperSlotForInsert(itemHeld, mortarTile.getInventory()), player, handIn);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void movePestle(Level level, BlockPos pos, BlockState state)
    {
        // Cycle property
        level.setBlockAndUpdate(pos, state.cycle(PESTLE));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(level.getBlockEntity(pos) instanceof MortarAndPestleBlockEntity)
            {
                IItemHandlerModifiable inventory = ((MortarAndPestleBlockEntity)level.getBlockEntity(pos)).getInventory();

                for(int i = 0; i < inventory.getSlots(); i++)
                {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                }
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PESTLE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new MortarAndPestleBlockEntity(pos, state);
    }
}