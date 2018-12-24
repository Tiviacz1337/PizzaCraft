package com.tiviacz.pizzacraft.objects.block.plants;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TomatoPlant extends BlockCrops
{
	public static final AxisAlignedBB[] TOMATO_PLANT_AABB = new AxisAlignedBB[] {
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),  
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D), 
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D)}; 
	
	public TomatoPlant(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{		
		if(!worldIn.isRemote)
		{
			if(this.isMaxAge(state))
			{
				spawnAsEntity(worldIn, pos, new ItemStack(this.getCrop(), worldIn.rand.nextInt(3) + 1));
				worldIn.setBlockState(pos, this.getDefaultState().withProperty(getAgeProperty(), 4), 2);
				return true;
			}				
		}
		return false;
	}
	
	@Override
	protected Item getSeed()
	{
		return ModItems.SEED_TOMATO;
	}
	
	@Override
	protected Item getCrop()
	{
		return ModItems.TOMATO;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		int a = this.getDefaultState().getValue(this.getAgeProperty());
		
		if(worldIn.getBlockState(pos.down()).getBlock() != Blocks.FARMLAND)
		{
			worldIn.setBlockToAir(pos);
			
			if(a < 6)
			{
				spawnAsEntity(worldIn, pos, new ItemStack(this.getSeed(), worldIn.rand.nextInt(3) + 1));
			}
			else
			{
				spawnAsEntity(worldIn, pos, new ItemStack(this.getCrop(), worldIn.rand.nextInt(3) + 1));
			}
		} 
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TOMATO_PLANT_AABB[state.getValue(this.getAgeProperty()).intValue()];
	}
	
	@Override
	public int getMaxAge()
    {
        return 6;
    }
	
	@Override
	protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }
	
	@Override
	protected int getAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty()).intValue();
    }
	
	@Override
    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

	@Override
    public boolean isMaxAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty()).intValue() >= this.getMaxAge();
    }
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
		return getAge(state) < getMaxAge();
    }
	
	@Override
	public void grow(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j)
        {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);
    }
	
	@Override
	protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.getInt(worldIn.rand, 2, 3);
    }
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) 
	{
		return new ItemStack(this.getSeed());
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {		
		return this.isMaxAge(state) ? new ItemStack(this.getCrop(), rand.nextInt(3) + 1).getItem() : new ItemStack(this.getSeed(), rand.nextInt(3) + 1).getItem();
    }
}