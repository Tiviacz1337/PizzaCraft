package com.tiviacz.pizzacraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OvenBlock extends Block
{
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public OvenBlock(Properties properties)
    {
        super(properties);
    }

    public void setLit(BlockState state, Player player, Level level, BlockPos pos, ItemStack stack, InteractionHand handIn)
    {
        level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
        level.setBlock(pos, state.setValue(LIT, true), 11);
        stack.hurtAndBreak(1, player, action -> action.broadcastBreakEvent(handIn));
    }

    public void extinguish(BlockState state, Level level, BlockPos pos)
    {
        level.setBlock(pos, state.setValue(LIT, false), 2);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = pos.getY();
        double d2 = (double) pos.getZ() + 0.5D;
        level.playLocalSound(d0, d1, d2, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 1.0F, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, RandomSource rand)
    {
        if(stateIn.getValue(LIT))
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.3;
            double d2 = (double)pos.getZ() + 0.5D;

            if(rand.nextDouble() < 0.1D)
            {
                level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = stateIn.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, true);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn)
    {
        boolean isLit = level.getBlockState(pos).getValue(LIT);
        if(isLit && !entityIn.fireImmune() && entityIn instanceof LivingEntity livingEntity && !EnchantmentHelper.hasFrostWalker(livingEntity))
        {
            entityIn.hurt(entityIn.m_269291_().m_269047_(), 1.0F);
        }

        super.stepOn(level, pos, state, entityIn);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);

        if(state.getValue(LIT))
        {
            if(itemstack.getItem() instanceof BucketItem bucket)
            {
                if(bucket.getFluid().is(FluidTags.WATER))
                {
                    extinguish(state, level, pos);
                    player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        else
        {
            if(itemstack.getItem() instanceof FlintAndSteelItem)
            {
                setLit(state, player, level, pos, itemstack, handIn);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, LIT);
    }
}