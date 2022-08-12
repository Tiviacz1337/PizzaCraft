package com.tiviacz.pizzacraft.blocks;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.blockentity.PizzaHungerSystem;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.KnifeItem;
import com.tiviacz.pizzacraft.items.PizzaPeelItem;
import com.tiviacz.pizzacraft.util.FoodUtils;
import com.tiviacz.pizzacraft.util.RenderUtils;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

public class PizzaBlock extends Block implements EntityBlock
{
    public static final IntegerProperty BITES = BlockStateProperties.BITES;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(3.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(5.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(7.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(9.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(11.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(13.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D)};

    public PizzaBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(BITES)];
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand) //#TODO add particle for cooked pizza
    {
        if(rand.nextInt(3) == 0 && level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            if(((PizzaBlockEntity)level.getBlockEntity(pos)).isFresh())
            {
                double[] particlePos = RenderUtils.getPosRandomAboveBlockHorizontal(level, pos);
                //worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                for(int i = 0; i < 2; i++)
                {
                    //worldIn.addParticle(ParticleTypes.POOF, pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D, 0D, 0.025D, 0D);
                    level.addParticle(ParticleTypes.POOF, particlePos[0], pos.getY() + 0.4D, particlePos[1], 0D, 0.025D, 0D);
                }
                level.addParticle(ParticleTypes.HAPPY_VILLAGER, particlePos[0], pos.getY() + 0.3D, particlePos[1], 0D, 3.0D + rand.nextDouble(), 0.0D);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);

        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            if(itemstack.getItem() instanceof PizzaPeelItem && state.getValue(BITES) == 0)
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

            if(itemstack.getItem() instanceof KnifeItem)
            {
                int i = state.getValue(BITES);

                ItemStack stack = ModItems.PIZZA_SLICE.get().getDefaultInstance();
                PizzaBlockEntity tile = (PizzaBlockEntity)level.getBlockEntity(pos);
                tile.writeToSliceItemStack(stack, i);

                if(i < 6)
                {
                    level.setBlock(pos, state.setValue(BITES, i + 1), 3);
                }
                else
                {
                    level.removeBlock(pos, false);
                }

                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D, stack);
                itemEntity.setDefaultPickUpDelay();

                level.addFreshEntity(itemEntity);

                tile.requestModelDataUpdate();

                itemstack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));

                return InteractionResult.SUCCESS;
            }

            if(itemstack.isEmpty() && player.isCrouching())
            {
                ((PizzaBlockEntity)level.getBlockEntity(pos)).openGUI(player, (PizzaBlockEntity)level.getBlockEntity(pos), pos);
                return InteractionResult.SUCCESS;
            }
        }

        if(this.eatPizza(level, pos, state, player) == InteractionResult.SUCCESS)
        {
            return InteractionResult.SUCCESS;
        }

        if(itemstack.isEmpty())
        {
            return InteractionResult.CONSUME;
        }
        return this.eatPizza(level, pos, state, player);
    }

    private InteractionResult eatPizza(LevelAccessor level, BlockPos pos, BlockState state, Player player)
    {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if(blockEntity instanceof PizzaBlockEntity)
        {
            blockEntity.requestModelDataUpdate();
        }

        if(!player.canEat(false))
        {
            return InteractionResult.PASS;
        }
        else
        {
            int i = state.getValue(BITES);

            if(blockEntity instanceof PizzaBlockEntity)
            {
                //player.addStat(Stats.EAT_CAKE_SLICE);
                ((PizzaBlockEntity)blockEntity).setHungerAndSaturationRefillment();

                for(Pair<MobEffectInstance, Float> pair : ((PizzaBlockEntity) blockEntity).getEffects())
                {
                    if(!level.isClientSide() && pair.getFirst() != null && level.getRandom().nextFloat() < pair.getSecond())
                    {
                        player.addEffect(new MobEffectInstance(pair.getFirst()));
                    }
                }

                player.getFoodData().eat(((PizzaBlockEntity)blockEntity).getHungerForSlice(i), ((PizzaBlockEntity)blockEntity).getSaturationForSlice());
            }

            if(i < 6)
            {
                level.setBlock(pos, state.setValue(BITES, i + 1), 3);
            }
            else
            {
                level.removeBlock(pos, false);
            }

            return InteractionResult.SUCCESS;
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
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
    {
        if(world.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            ItemStack stack = this.getCloneItemStack(world, pos, state);
            ((PizzaBlockEntity)world.getBlockEntity(pos)).writeToItemStack(stack);
            return stack;
        }
        return this.getCloneItemStack(state, target, world, pos, player);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.isViewBlocking(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter level, BlockPos pos, Direction side)
    {
        return (7 - blockState.getValue(BITES)) * 2;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
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
        addInformationForPizza(stack, tooltip);
      /*  ItemStackHandler handler = Utils.createHandlerFromStack(stack, 9);

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
        tooltip.add(new StringTextComponent("Restores: " + FoodUtils.getHungerForSlice(instance.getHunger(), false) + ((instance.getHunger() % 7 != 0) ? " (+" + instance.getHunger() % 7 + ")" : "") + " Hunger per Slice").mergeStyle(TextFormatting.BLUE));
        tooltip.add(new StringTextComponent("Restores: " + (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0) + " Saturation per Slice").mergeStyle(TextFormatting.BLUE)); */
    }

    public static void addInformationForPizza(ItemStack stack, List<Component> tooltip)
    {
        ItemStackHandler handler = Utils.createHandlerFromStack(stack, 9);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            if(!handler.getStackInSlot(i).isEmpty())
            {
                ItemStack stackInSlot = handler.getStackInSlot(i);
                TranslatableComponent translatedText = new TranslatableComponent(stackInSlot.getDescriptionId());
                TextComponent textComponent = new TextComponent(stackInSlot.getCount() > 1 ? stackInSlot.getCount() + "x " : "");
                tooltip.add(textComponent.append(translatedText).withStyle(ChatFormatting.BLUE));
            }
        }
        PizzaHungerSystem instance = new PizzaHungerSystem(handler);
        tooltip.add(new TranslatableComponent("information.pizzacraft.hunger", FoodUtils.getHungerForSlice(instance.getHunger(), false), ((instance.getHunger() % 7 != 0) ? " (+" + instance.getHunger() % 7 + ")" : ""), instance.getHunger()).withStyle(ChatFormatting.BLUE));
        tooltip.add(new TranslatableComponent("information.pizzacraft.saturation", (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0), (float)(Math.round(instance.getSaturation() * 100.0) / 100.0)).withStyle(ChatFormatting.BLUE));

        if(!instance.getEffects().isEmpty())
        {
            tooltip.add(new TranslatableComponent("information.pizzacraft.effects").withStyle(ChatFormatting.GOLD));

            for(Pair<MobEffectInstance, Float> pair : instance.getEffects())
            {
                tooltip.add(new TranslatableComponent(pair.getFirst().getDescriptionId()).withStyle(pair.getFirst().getEffect().getCategory().getTooltipFormatting()));
            }
        }
        //tooltip.add(new StringTextComponent("Restores: " + FoodUtils.getHungerForSlice(instance.getHunger(), false) + ((instance.getHunger() % 7 != 0) ? " (+" + instance.getHunger() % 7 + ")" : "") + " Hunger per Slice").mergeStyle(TextFormatting.BLUE));
        //tooltip.add(new StringTextComponent("Restores: " + (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0) + " Saturation per Slice").mergeStyle(TextFormatting.BLUE));
    }
}
