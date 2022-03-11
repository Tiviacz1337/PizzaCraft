package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.tileentity.PizzaBagTileEntity;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PizzaBagBlock extends Block
{
    public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;

    public PizzaBagBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(PROPERTY_OPEN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if(tileentity instanceof PizzaBagTileEntity)
        {
            ((PizzaBagTileEntity)tileentity).pizzaBagTick();
        }

    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(worldIn.getBlockEntity(pos) instanceof PizzaBagTileEntity)
        {
            ((PizzaBagTileEntity)worldIn.getBlockEntity(pos)).openGUI(player, (PizzaBagTileEntity)worldIn.getBlockEntity(pos), pos);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(worldIn.getBlockEntity(pos) instanceof PizzaBagTileEntity)
        {
            if(stack.getTag() != null)
            {
                ((PizzaBagTileEntity)worldIn.getBlockEntity(pos)).readFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        if(world.getBlockEntity(pos) instanceof PizzaBagTileEntity)
        {
            ItemStack stack = this.getBlock().getCloneItemStack(world, pos, state);
            ((PizzaBagTileEntity)world.getBlockEntity(pos)).writeToItemStack(stack);
            return stack;
        }
        return this.getBlock().getPickBlock(state, target, world, pos, player);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(PROPERTY_OPEN);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new PizzaBagTileEntity();
    }
}