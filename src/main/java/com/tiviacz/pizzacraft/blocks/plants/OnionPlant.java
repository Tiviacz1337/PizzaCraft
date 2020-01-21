package com.tiviacz.pizzacraft.blocks.plants;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.HarvestingUtils;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class OnionPlant extends BlockCrops
{
	public static final AxisAlignedBB[] ONION_PLANT_AABB = new AxisAlignedBB[] {
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),    
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D),  
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),  
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),  
	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D)}; 
	
	public OnionPlant(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(this.isMaxAge(state))
		{
			HarvestingUtils.harvestPlant(state, playerIn, worldIn, pos);
			return true;			
		}
		return false;
	}
	
	@Override
	protected Item getSeed()
	{
		return ModItems.SEED_ONION;
	}
	
	@Override
	protected Item getCrop()
	{
		return ModItems.ONION;
	}
	
	@Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
    	super.getDrops(drops, world, pos, state, fortune);
        int age = getAge(state);
        Random rand = new Random();

        if(age >= this.getMaxAge())
        {
            for(int i = 0; i < 1 + fortune; ++i)
            {
                if(rand.nextInt(4) == 3)
                {
                	drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        	
        	for(int i = 0; i < 1 + fortune; ++i)
            {
                if(rand.nextInt(2) == 1)
                {
                	drops.add(new ItemStack(this.getCrop(), 1, 0));
                }
            }
        }
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return ONION_PLANT_AABB[state.getValue(this.getAgeProperty())];
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
        return state.getValue(this.getAgeProperty());
    }
	
	@Override
    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

	@Override
    public boolean isMaxAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
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
		return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }
}