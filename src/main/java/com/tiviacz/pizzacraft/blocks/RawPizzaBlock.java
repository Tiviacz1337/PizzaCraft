package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModSounds;
import com.tiviacz.pizzacraft.items.PizzaPeelItem;
import com.tiviacz.pizzacraft.util.RenderUtils;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RawPizzaBlock extends Block implements EntityBlock
{
    private static final VoxelShape SHAPE = box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

    public RawPizzaBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity blockEntity)
        {
            if(blockEntity.isBaking())
            {
                //worldIn.playSound(null, pos, ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                if(rand.nextDouble() < 0.3D)
                {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.SIZZLING_SOUND.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

                if(rand.nextInt(2) == 0)
                {
                    double[] particlePos = RenderUtils.getPosRandomAboveBlockHorizontal(level, pos);
                    level.addParticle(ParticleTypes.POOF, particlePos[0], pos.getY() + 0.4D, particlePos[1], 0D, 0.025D, 0D);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(player.getItemInHand(handIn).getItem() instanceof PizzaPeelItem && level.getBlockEntity(pos) instanceof PizzaBlockEntity blockEntity)
        {
            ItemStack stack = asItem().getDefaultInstance();
            stack = blockEntity.writeToItemStack(stack);

            if(!level.isClientSide)
            {
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
            }

            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            return InteractionResult.SUCCESS;
        }

        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity blockEntity)
        {
            return blockEntity.onBlockActivated(player, handIn);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity blockEntity && !player.isCreative() && !(player.getMainHandItem().getItem() instanceof PizzaPeelItem))
        {
            for(int i = 0; i < blockEntity.getInventory().getSlots(); i++)
            {
                if(!blockEntity.getInventory().getStackInSlot(i).isEmpty())
                {
                    Utils.spawnItemStackInWorld(level, pos, blockEntity.getInventory().getStackInSlot(i));
                }
            }

            Utils.spawnItemStackInWorld(level, pos, new ItemStack(ModItems.DOUGH.get()));
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!(newState.getBlock() instanceof PizzaBlock))
        {
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity blockEntity)
        {
            if(stack.getTag() != null)
            {
                blockEntity.readFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult result, BlockGetter level, BlockPos pos, Player player)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity blockEntity)
        {
            ItemStack stack = this.getCloneItemStack(level, pos, state);
            stack = blockEntity.writeToItemStack(stack);
            return stack;
        }
        return this.getCloneItemStack(state, result, level, pos, player);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return Utils.getTicker(blockEntityType, ModBlockEntityTypes.PIZZA.get(), PizzaBlockEntity::tick);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PizzaBlockEntity(pos, state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn)
    {
        PizzaBlock.addInformationForPizza(stack, tooltip);
    }
}