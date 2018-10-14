package com.tiviacz.pizzacraft.objects.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.BlockBase;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizzaBox extends BlockBase 
{
	public static final PropertyInteger COUNT = PropertyInteger.create("count", 0, 8);
	protected static final AxisAlignedBB[] PIZZA_BOX_AABB = new AxisAlignedBB[] {
		    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D), //0
		    new AxisAlignedBB(0D, 0, 0D, 1D, 0.25D, 1D), //1
		    new AxisAlignedBB(0D, 0, 0D, 1D, 0.375D, 1D), //2
		    new AxisAlignedBB(0D, 0, 0D, 1D, 0.5D, 1D), //3
		    new AxisAlignedBB(0D, 0, 0D, 1D, 0.625D, 1D), //4
		    new AxisAlignedBB(0D, 0, 0D, 1D, 0.75D, 1D), //5
			new AxisAlignedBB(0D, 0, 0D, 1D, 0.875D, 1D), //6
			new AxisAlignedBB(0D, 0, 0D, 1D, 1D, 1D), //7
			new AxisAlignedBB(0D, 0, 0D, 1D, 1D, 1D)}; //8	
		
	public BlockPizzaBox(String name, Material material)
	{
		super(name, material);
		
		setSoundType(SoundType.CLOTH);
        setHardness(0.5F);
        setResistance(2.5F);
        setHarvestLevel("hand", 0);
	}
	
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		int c = ((Integer)state.getValue(COUNT)).intValue();
		
		if(c == 8)
		{
			return true;
		}
		
		else
		{
			return false; 
		}
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		int c = ((Integer)state.getValue(COUNT)).intValue();
		
		if(c == 7)
		{
			return true;
		}
		
		else
		{
			return false; 
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	if(!worldIn.isRemote)
        {	
    		int c = ((Integer)state.getValue(COUNT)).intValue();
        	ItemStack HeldItem = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        	IBlockState iBlockstate = worldIn.getBlockState(pos.up());
        	
        	if(HeldItem.getItem() == Item.getItemFromBlock(this))
        	{   
        		if(c != 7)
        		{
        			worldIn.setBlockState(pos, state.withProperty(COUNT, c + 1), 3);
        			
        			if(!playerIn.capabilities.isCreativeMode)
        			{
        				HeldItem.shrink(1);
        			}
        		}
        		
        		if(c == 7 && iBlockstate == Blocks.AIR.getDefaultState())
        		{
        			worldIn.setBlockState(pos.up(), state.withProperty(COUNT, 0));
        			
        			if(!playerIn.capabilities.isCreativeMode)
        			{
        				HeldItem.shrink(1);
        			}
        		}
        	}
	        	
        	else if(HeldItem.getItem() != Item.getItemFromBlock(this))
	        {
	        	if(c != 0)	
	        	{
	        		worldIn.setBlockState(pos, state.withProperty(COUNT, c - 1), 3);
		        	InventoryHelper.spawnItemStack(worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), new ItemStack(this));	  
	        	}
	        	
	        	if(c == 0)
	        	{
	        		worldIn.setBlockToAir(pos);
	        		InventoryHelper.spawnItemStack(worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), new ItemStack(this));
	        	}
	        }
        }
		return true;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PIZZA_BOX_AABB[((Integer)state.getValue(COUNT)).intValue()];	    
	}
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COUNT});
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(COUNT)).intValue();
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
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		int c = ((Integer)state.getValue(COUNT)).intValue();
		
		if(c == 0)
		{
			return 1;
		}
		
		if(c == 1)
		{
			return 2; 
		}
		
		if(c == 2)
		{
			return 3;
		}
		
		if(c == 3)
		{
			return 4;
		}
		
		if(c == 4)
		{
			return 5;
		}
		
		if(c == 5)
		{
			return 6;
		}
		
		if(c == 6)
		{
			return 7;
		}
		
		if(c == 7)
		{
			return 8;
		}
		return super.quantityDropped(state, fortune, random);
	}
}
