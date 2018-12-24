package com.tiviacz.pizzacraft.objects.block;

import java.util.Random;

import com.tiviacz.pizzacraft.init.base.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizzaBox extends BlockBase 
{
	public static final PropertyInteger COUNT = PropertyInteger.create("count", 0, 8);
	public static final AxisAlignedBB[] PIZZA_BOX_AABB = new AxisAlignedBB[] {
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.25D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.375D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.5D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.625D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.75D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 0.875D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 1D, 1D),
	new AxisAlignedBB(0D, 0, 0D, 1D, 1D, 1D)};
		
	public BlockPizzaBox(String name, Material material)
	{
		super(name, material);
		
		setSoundType(SoundType.CLOTH);
        setHardness(0.5F);
        setResistance(2.5F);
        setHarvestLevel("hand", 0);
        setDefaultState(blockState.getBaseState().withProperty(COUNT, 0));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PIZZA_BOX_AABB[state.getValue(COUNT).intValue()];
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		int a = state.getValue(COUNT).intValue();
		
		if(a == 7) 
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		int a = state.getValue(COUNT).intValue();
		
		if(a == 7) 
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	if(!worldIn.isRemote)
        {	
        	ItemStack helditem = playerIn.getHeldItem(hand);
        	int a = state.getValue(COUNT).intValue();
        	
        	if(helditem.getItem() == Item.getItemFromBlock(this))
        	{   
        		if(a == 7 && worldIn.isAirBlock(pos.up()))
        		{
        			worldIn.setBlockState(pos.up(), state.withProperty(COUNT, 0), 3);
        			
        			if(!playerIn.capabilities.isCreativeMode)
        			{
        				helditem.shrink(1);
        			}
        		}
        		
        		else
        		{
        			worldIn.setBlockState(pos, state.withProperty(COUNT, a + 1), 3);
        			
        			if(!playerIn.capabilities.isCreativeMode)
        			{
        				helditem.shrink(1);
        			}
        		}
        	}
	        	
        	if(helditem.getItem() != Item.getItemFromBlock(this))
	        {
        		spawnAsEntity(worldIn, pos, new ItemStack(this));
        		
	        	if(a == 0)
	        	{
	        		worldIn.setBlockToAir(pos);
	        	}
	        	
	        	else
	        	{
	        		worldIn.setBlockState(pos, state.withProperty(COUNT, a - 1), 3);
	        	}
	        }
        }
		return true;
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COUNT).intValue();
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COUNT, meta);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COUNT});
    }
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{	
		for(int c = state.getValue(COUNT).intValue(); c < 8;)
		{
			return c + 1;
		}
		return super.quantityDropped(state, fortune, random);
	}
}