package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.blockentity.PizzaBagBlockEntity;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PizzaBagBlock extends Block implements EntityBlock
{
    public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;

    public PizzaBagBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(PROPERTY_OPEN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
    {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof PizzaBagBlockEntity)
        {
            ((PizzaBagBlockEntity)blockEntity).recheckOpen();
        }

    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBagBlockEntity)
        {
            ((PizzaBagBlockEntity)level.getBlockEntity(pos)).openGUI(player, (PizzaBagBlockEntity)level.getBlockEntity(pos), pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBagBlockEntity)
        {
            if(stack.getTag() != null)
            {
                ((PizzaBagBlockEntity)level.getBlockEntity(pos)).readFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBagBlockEntity)
        {
            ItemStack stack = this.getCloneItemStack(level, pos, state);
            ((PizzaBagBlockEntity)level.getBlockEntity(pos)).writeToItemStack(stack);
            return stack;
        }
        return this.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn)
    {
        if(stack.getTag() != null)
        {
            ItemStackHandler handler = Utils.createHandlerFromStack(stack, 6);

            for(int i = 0; i < handler.getSlots(); i++)
            {
                if(!handler.getStackInSlot(i).isEmpty())
                {
                    tooltip.add(handler.getStackInSlot(i).getDisplayName());
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PROPERTY_OPEN);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PizzaBagBlockEntity(pos, state);
    }
}