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
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            if(((PizzaBlockEntity)level.getBlockEntity(pos)).isBaking())
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
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if(player.getItemInHand(handIn).getItem() instanceof PizzaPeelItem && level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            ItemStack stack = asItem().getDefaultInstance();
            ((PizzaBlockEntity)level.getBlockEntity(pos)).writeToItemStack(stack);

            if(!level.isClientSide)
            {
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
            }

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            return InteractionResult.SUCCESS;
        }

        if(blockEntity instanceof PizzaBlockEntity)
        {
            return ((PizzaBlockEntity)blockEntity).onBlockActivated(player, handIn);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity && !player.isCreative() && !(player.getMainHandItem().getItem() instanceof PizzaPeelItem))
        {
            PizzaBlockEntity tileEntity = (PizzaBlockEntity)level.getBlockEntity(pos);

            for(int i = 0; i < tileEntity.getInventory().getSlots(); i++)
            {
                if(!tileEntity.getInventory().getStackInSlot(i).isEmpty())
                {
                    Utils.spawnItemStackInWorld(level, pos, tileEntity.getInventory().getStackInSlot(i));
                    //world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getInventory().getStackInSlot(i)));
                }
            }

            Utils.spawnItemStackInWorld(level, pos, new ItemStack(ModItems.DOUGH.get()));
           // world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.DOUGH.get())));
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
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            if(stack.getTag() != null)
            {
                ((PizzaBlockEntity)level.getBlockEntity(pos)).readFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult result, BlockGetter level, BlockPos pos, Player player)
    {
        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            ItemStack stack = this.getCloneItemStack(level, pos, state);
            ((PizzaBlockEntity)level.getBlockEntity(pos)).writeToItemStack(stack);
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
 /*       ItemStackHandler handler = Utils.createHandlerFromStack(stack, 9);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            if(!handler.getStackInSlot(i).isEmpty())
            {
                ItemStack stackInSlot = handler.getStackInSlot(i);
                TranslationTextComponent translatedText = new TranslationTextComponent(stackInSlot.getTranslationKey());
                StringTextComponent textComponent = new StringTextComponent(stackInSlot.getCount() > 1 ? stackInSlot.getCount() + "x " : "");
                tooltip.add(textComponent.append(translatedText).mergeStyle(TextFormatting.BLUE));
            }
        }
        PizzaHungerSystem instance = new PizzaHungerSystem(handler);
        tooltip.add(new TranslationTextComponent("information.pizzacraft.hunger", FoodUtils.getHungerForSlice(instance.getHunger(), false), ((instance.getHunger() % 7 != 0) ? "(+" + instance.getHunger() % 7 + ")" : "")).mergeStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("information.pizzacraft.saturation", (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0)).mergeStyle(TextFormatting.BLUE));
        //tooltip.add(new StringTextComponent("Restores: " + FoodUtils.getHungerForSlice(instance.getHunger(), false) + ((instance.getHunger() % 7 != 0) ? " (+" + instance.getHunger() % 7 + ")" : "") + " Hunger per Slice").mergeStyle(TextFormatting.BLUE));
        //tooltip.add(new StringTextComponent("Restores: " + (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0) + " Saturation per Slice").mergeStyle(TextFormatting.BLUE)); */
    }
}