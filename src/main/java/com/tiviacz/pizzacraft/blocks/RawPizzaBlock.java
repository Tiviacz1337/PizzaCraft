package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModSounds;
import com.tiviacz.pizzacraft.tileentity.PizzaHungerSystem;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
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

public class RawPizzaBlock extends Block
{
    private static final VoxelShape SHAPE = makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

    public RawPizzaBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if(worldIn.getTileEntity(pos) instanceof PizzaTileEntity)
        {
            if(((PizzaTileEntity)worldIn.getTileEntity(pos)).isBaking())
            {
                //worldIn.playSound(null, pos, ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                if(rand.nextDouble() < 0.3D)
                {
                    worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }

                if(rand.nextInt(100) > 35)
                {
                    worldIn.addParticle(ParticleTypes.POOF, pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D, 0D, 0.025D, 0D);
                }
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        TileEntity tile = worldIn.getTileEntity(pos);

        if(player.getHeldItem(handIn).getItem() == ModItems.PIZZA_PEEL.get() && worldIn.getTileEntity(pos) instanceof PizzaTileEntity)
        {
            ItemStack stack = asItem().getDefaultInstance();
            ((PizzaTileEntity)worldIn.getTileEntity(pos)).writeToItemStack(stack);

            if(!worldIn.isRemote)
            {
                worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
            }

            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

            return ActionResultType.SUCCESS;
        }

        if(tile instanceof PizzaTileEntity)
        {
            return ((PizzaTileEntity)tile).onBlockActivated(player, handIn);
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid)
    {
        if(world.getTileEntity(pos) instanceof PizzaTileEntity && !player.isCreative() && player.getHeldItemMainhand().getItem() != ModItems.PIZZA_PEEL.get())
        {
            PizzaTileEntity tileEntity = (PizzaTileEntity)world.getTileEntity(pos);

            for(int i = 0; i < tileEntity.getInventory().getSlots(); i++)
            {
                if(!tileEntity.getInventory().getStackInSlot(i).isEmpty())
                {
                    world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getInventory().getStackInSlot(i)));
                }
            }

            world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.DOUGH.get())));
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!(newState.getBlock() instanceof PizzaBlock))
        {
       /*     if(world.getTileEntity(pos) instanceof PizzaTileEntity)
            {
                PizzaTileEntity tileEntity = (PizzaTileEntity)world.getTileEntity(pos);

                for(int i = 0; i < tileEntity.getInventory().getSlots(); i++)
                {
                    if(!tileEntity.getInventory().getStackInSlot(i).isEmpty())
                    {
                        world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getInventory().getStackInSlot(i)));
                    }
                }

                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.DOUGH.get())));
            } */

            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(worldIn.getTileEntity(pos) instanceof PizzaTileEntity)
        {
            if(stack.getTag() != null)
            {
                ((PizzaTileEntity)worldIn.getTileEntity(pos)).readFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        if(world.getTileEntity(pos) instanceof PizzaTileEntity)
        {
            ItemStack stack = this.getBlock().getItem(world, pos, state);
            ((PizzaTileEntity)world.getTileEntity(pos)).writeToItemStack(stack);
            return stack;
        }
        return this.getBlock().getPickBlock(state, target, world, pos, player);
    }


    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
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
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
       // if(stack.getTag() != null)
        //{
            ItemStackHandler handler = new ItemStackHandler(9);

            if(stack.getTag() != null)
            {
                handler.deserializeNBT(stack.getTag().getCompound("Inventory"));
            }

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
            tooltip.add(new StringTextComponent("Restores: " + instance.getHunger() / 6 + " Hunger per Slice").mergeStyle(TextFormatting.BLUE));
       // }
    }
}