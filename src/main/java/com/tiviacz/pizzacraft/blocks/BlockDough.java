package com.tiviacz.pizzacraft.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.BlockBase;
import com.tiviacz.pizzacraft.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDough extends BlockBase
{
	public static final PropertyInteger KNEEDING = PropertyInteger.create("kneeding", 0, 6);	
	public static final AxisAlignedBB[] DOUGH_AABB = new AxisAlignedBB[] {
		    new Bounds(5, 0, 5, 11, 6, 11).toAABB(),
		    new Bounds(3, 0, 4, 11, 4, 11).toAABB(),
		    new Bounds(4, 0, 5, 10, 5, 12).toAABB(),
		    new Bounds(5, 0, 2, 13, 3, 11).toAABB(),
		    new Bounds(3, 0, 5, 11, 4, 11).toAABB(),
		    new Bounds(3, 0, 3, 13, 2, 13).toAABB(),
		    new Bounds(1, 0, 1, 15, 1, 15).toAABB()};
	
	public BlockDough(String name, Material material) 
	{
		super(name, material);
		
		setDefaultState(this.blockState.getBaseState().withProperty(KNEEDING, 0));
		
		this.setSoundType(SoundType.SNOW);
		this.setResistance(2.0F);
		this.setHardness(0.0F);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return DOUGH_AABB[state.getValue(KNEEDING)];
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(hand == EnumHand.MAIN_HAND)
    	{
    		if(state.getValue(KNEEDING) == 6 && playerIn.isSneaking())
    		{
    			if(playerIn.getHeldItem(hand).isEmpty())
    			{
    				playerIn.setHeldItem(hand, new ItemStack(ModItems.PIZZA_DOUGH));
    				worldIn.setBlockToAir(pos);
    				return true;
    			}
    		}
    		
    		if(playerIn.getHeldItem(hand).isEmpty())
    		{
    			return this.proceedKneeding(worldIn, pos, state);
    		}
    	}
        return false;
    }
    
    private boolean proceedKneeding(World worldIn, BlockPos pos, IBlockState state)
    {
    	int value = state.getValue(KNEEDING);
    	
    	if(value < 6)
    	{
    		worldIn.setBlockState(pos, state.withProperty(KNEEDING, value + 1));
    		worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 1.0F, 1.0F);
    		return true;
    	}
    	return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;       
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {	
        if(!this.canBlockStay(worldIn, pos))
        {	
        	worldIn.setBlockToAir(pos);
        }      
    }
        
    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	if(state.getValue(KNEEDING) == 6)
    	{
    		return ModItems.PIZZA_DOUGH;
    	}
        return Item.getItemFromBlock(this);
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {KNEEDING});
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
		return state.getValue(KNEEDING);
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(KNEEDING, meta);
    }
}