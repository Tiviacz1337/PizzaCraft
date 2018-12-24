package com.tiviacz.pizzacraft.objects.block;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizzaBoxBase extends BlockBase
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final AxisAlignedBB PIZZA_BOX_AABB = new AxisAlignedBB(0D, 0, 0D, 1D, 0.1875D, 1D);
	
	IBlockState pizzablock;
	
	public BlockPizzaBoxBase(String name, Material material, IBlockState pizzablock) 
	{
		super(name, material);
		
		setSoundType(SoundType.CLOTH);
	    setHardness(0.5F);
	    setResistance(2.5F);
	    setHarvestLevel("hand", 0);
	    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)); 
	    this.pizzablock = pizzablock;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PIZZA_BOX_AABB;    
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
		
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false; 
	}
	
	@Override		   
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
	    if(!worldIn.isRemote)
	    {	
        	worldIn.setBlockState(pos, pizzablock);
        	spawnAsEntity(worldIn, pos, new ItemStack(ModItems.CARDBOARD, 3));
	    }
	    return true;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
	    EnumFacing facing = EnumFacing.getFront(meta);

	    if(facing.getAxis() == EnumFacing.Axis.Y) 
	    {
	    	facing = EnumFacing.NORTH;
	    }
	    return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) 
	{
	    return state.getValue(FACING).getIndex();
	}
	    
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) 
	{
	    return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}