package com.tiviacz.pizzacraft.blocks;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.KnifeItem;
import com.tiviacz.pizzacraft.items.PizzaPeelItem;
import com.tiviacz.pizzacraft.tileentity.PizzaHungerSystem;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import com.tiviacz.pizzacraft.util.FoodUtils;
import com.tiviacz.pizzacraft.util.RenderUtils;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PizzaBlock extends Block
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPES[state.getValue(BITES)];
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) //#TODO add particle for cooked pizza
    {
        if(rand.nextInt(3) == 0 && worldIn.getBlockEntity(pos) instanceof PizzaTileEntity)
        {
            if(((PizzaTileEntity)worldIn.getBlockEntity(pos)).isFresh())
            {
                double[] particlePos = RenderUtils.getPosRandomAboveBlockHorizontal(worldIn, pos);
                //worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                for(int i = 0; i < 2; i++)
                {
                    //worldIn.addParticle(ParticleTypes.POOF, pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D, 0D, 0.025D, 0D);
                    worldIn.addParticle(ParticleTypes.POOF, particlePos[0], pos.getY() + 0.4D, particlePos[1], 0D, 0.025D, 0D);
                }
                worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, particlePos[0], pos.getY() + 0.3D, particlePos[1], 0D, 3.0D + rand.nextDouble(), 0.0D);
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);

        if(worldIn.getBlockEntity(pos) instanceof PizzaTileEntity)
        {
            if(itemstack.getItem() instanceof PizzaPeelItem && state.getValue(BITES) == 0)
            {
                ItemStack stack = asItem().getDefaultInstance();
                ((PizzaTileEntity)worldIn.getBlockEntity(pos)).writeToItemStack(stack);

                if(!worldIn.isClientSide)
                {
                    worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
                }

                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                return ActionResultType.SUCCESS;
            }

            if(itemstack.getItem() instanceof KnifeItem)
            {
                int i = state.getValue(BITES);

                ItemStack stack = ModItems.PIZZA_SLICE.get().getDefaultInstance();
                PizzaTileEntity tile = (PizzaTileEntity)worldIn.getBlockEntity(pos);
                tile.writeToSliceItemStack(stack, i);

                if(i < 6)
                {
                    worldIn.setBlock(pos, state.setValue(BITES, i + 1), 3);
                }
                else
                {
                    worldIn.removeBlock(pos, false);
                }

                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D, stack);
                itemEntity.setDefaultPickUpDelay();

                worldIn.addFreshEntity(itemEntity);

                tile.requestModelDataUpdate();

                itemstack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));

                return ActionResultType.SUCCESS;
            }

            if(itemstack.isEmpty() && player.isCrouching())
            {
                ((PizzaTileEntity)worldIn.getBlockEntity(pos)).openGUI(player, (PizzaTileEntity)worldIn.getBlockEntity(pos), pos);
                return ActionResultType.SUCCESS;
            }
        }

        if(this.eatPizza(worldIn, pos, state, player) == ActionResultType.SUCCESS)
        {
            return ActionResultType.SUCCESS;
        }

        if(itemstack.isEmpty())
        {
            return ActionResultType.CONSUME;
        }
        return this.eatPizza(worldIn, pos, state, player);
    }

    private ActionResultType eatPizza(IWorld worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);

        if(tileEntity instanceof PizzaTileEntity)
        {
            tileEntity.requestModelDataUpdate();
        }

        if(!player.canEat(false))
        {
            return ActionResultType.PASS;
        }
        else
        {
            int i = state.getValue(BITES);

            if(tileEntity instanceof PizzaTileEntity)
            {
                //player.addStat(Stats.EAT_CAKE_SLICE);
                ((PizzaTileEntity)tileEntity).setHungerAndSaturationRefillment();

                for(Pair<EffectInstance, Float> pair : ((PizzaTileEntity) tileEntity).getEffects())
                {
                    if(!worldIn.isClientSide() && pair.getFirst() != null && worldIn.getRandom().nextFloat() < pair.getSecond())
                    {
                        player.addEffect(new EffectInstance(pair.getFirst()));
                    }
                }

                player.getFoodData().eat(((PizzaTileEntity)tileEntity).getHungerForSlice(i), ((PizzaTileEntity)tileEntity).getSaturationForSlice());
            }

            if(i < 6)
            {
                worldIn.setBlock(pos, state.setValue(BITES, i + 1), 3);
            }
            else
            {
                worldIn.removeBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(worldIn.getBlockEntity(pos) instanceof PizzaTileEntity)
        {
            if(stack.getTag() != null)
            {
                ((PizzaTileEntity)worldIn.getBlockEntity(pos)).readFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        if(world.getBlockEntity(pos) instanceof PizzaTileEntity)
        {
            ItemStack stack = this.getBlock().getCloneItemStack(world, pos, state);
            ((PizzaTileEntity)world.getBlockEntity(pos)).writeToItemStack(stack);
            return stack;
        }
        return this.getBlock().getPickBlock(state, target, world, pos, player);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.isViewBlocking(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        return (7 - blockState.getValue(BITES)) * 2;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new PizzaTileEntity();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
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

    public static void addInformationForPizza(ItemStack stack, List<ITextComponent> tooltip)
    {
        ItemStackHandler handler = Utils.createHandlerFromStack(stack, 9);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            if(!handler.getStackInSlot(i).isEmpty())
            {
                ItemStack stackInSlot = handler.getStackInSlot(i);
                TranslationTextComponent translatedText = new TranslationTextComponent(stackInSlot.getDescriptionId());
                StringTextComponent textComponent = new StringTextComponent(stackInSlot.getCount() > 1 ? stackInSlot.getCount() + "x " : "");
                tooltip.add(textComponent.append(translatedText).withStyle(TextFormatting.BLUE));
            }
        }
        PizzaHungerSystem instance = new PizzaHungerSystem(handler);
        tooltip.add(new TranslationTextComponent("information.pizzacraft.hunger", FoodUtils.getHungerForSlice(instance.getHunger(), false), ((instance.getHunger() % 7 != 0) ? " (+" + instance.getHunger() % 7 + ")" : ""), instance.getHunger()).withStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("information.pizzacraft.saturation", (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0), (float)(Math.round(instance.getSaturation() * 100.0) / 100.0)).withStyle(TextFormatting.BLUE));

        if(!instance.getEffects().isEmpty())
        {
            tooltip.add(new TranslationTextComponent("information.pizzacraft.effects").withStyle(TextFormatting.GOLD));

            for(Pair<EffectInstance, Float> pair : instance.getEffects())
            {
                tooltip.add(new TranslationTextComponent(pair.getFirst().getDescriptionId()).withStyle(pair.getFirst().getEffect().getCategory().getTooltipFormatting()));
            }
        }
        //tooltip.add(new StringTextComponent("Restores: " + FoodUtils.getHungerForSlice(instance.getHunger(), false) + ((instance.getHunger() % 7 != 0) ? " (+" + instance.getHunger() % 7 + ")" : "") + " Hunger per Slice").mergeStyle(TextFormatting.BLUE));
        //tooltip.add(new StringTextComponent("Restores: " + (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0) + " Saturation per Slice").mergeStyle(TextFormatting.BLUE));
    }
}
