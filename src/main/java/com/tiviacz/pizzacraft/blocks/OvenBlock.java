package com.tiviacz.pizzacraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class OvenBlock extends Block
{
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public OvenBlock(Properties properties)
    {
        super(properties);
    }

    public void setLit(BlockState state, PlayerEntity player, World worldIn, BlockPos pos, ItemStack stack, Hand handIn)
    {
        worldIn.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.random.nextFloat() * 0.4F + 0.8F);
        worldIn.setBlock(pos, state.setValue(LIT, true), 11);
        stack.hurtAndBreak(1, player, action -> action.broadcastBreakEvent(handIn));
    }

    public void extinguish(BlockState state, World worldIn, BlockPos pos)
    {
        worldIn.setBlock(pos, state.setValue(LIT, false), 2);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = pos.getY();
        double d2 = (double) pos.getZ() + 0.5D;
        worldIn.playLocalSound(d0, d1, d2, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 1.0F, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if(stateIn.getValue(LIT))
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.3;
            double d2 = (double)pos.getZ() + 0.5D;

            if(rand.nextDouble() < 0.1D)
            {
                worldIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = stateIn.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, true);
    }

    @Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn)
    {
        boolean isLit = worldIn.getBlockState(pos).getValue(LIT);
        if(isLit && !entityIn.fireImmune() && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn))
        {
            entityIn.hurt(DamageSource.HOT_FLOOR, 1.0F);
        }

        super.stepOn(worldIn, pos, entityIn);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);

        if(state.getValue(LIT))
        {
            if(itemstack.getItem() instanceof BucketItem)
            {
                if(((BucketItem)itemstack.getItem()).getFluid().is(FluidTags.WATER))
                {
                    extinguish(state, worldIn, pos);
                    player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
                    return ActionResultType.SUCCESS;
                }
            }
        }
        else
        {
            if(itemstack.getItem() instanceof FlintAndSteelItem)
            {
                setLit(state, player, worldIn, pos, itemstack, handIn);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }
}