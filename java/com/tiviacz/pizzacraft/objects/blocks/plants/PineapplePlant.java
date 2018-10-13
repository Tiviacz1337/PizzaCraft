package com.tiviacz.pizzacraft.objects.blocks.plants;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class PineapplePlant extends BlockCrops
{
	private static final AxisAlignedBB[] PINEAPPLE_PLANT_AABB =
			new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),  
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),   
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D),  
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), 
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)}; 
	
	public PineapplePlant(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		
		if(!worldIn.isRemote)
		{
			if(this.isMaxAge(state))
			{
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.PINEAPPLE, 1)));
				worldIn.setBlockState(pos, this.getDefaultState().withProperty(getAgeProperty(), 0));
				return true;
			}				
		}
		return false;
	}
	
	@Override
	protected Item getSeed()
	{
		return ModItems.SEED_PINEAPPLE;
	}
	
	@Override
	protected Item getCrop()
	{
		return ModItems.PINEAPPLE;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		int a = ((Integer)state.getValue(this.getAgeProperty()));
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		
		if(block != Blocks.FARMLAND)
		{
			if(a == 0 || a == 1 || a == 2 || a == 3 || a == 4 || a == 5)
			{
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SEED_PINEAPPLE, 1));
				worldIn.setBlockToAir(pos);
			}
			else
			{
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.PINEAPPLE, 1));
				worldIn.setBlockToAir(pos);
			}
		} 
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PINEAPPLE_PLANT_AABB[(Integer)state.getValue(this.getAgeProperty()).intValue()];
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
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }
	
	@Override
    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

	@Override
    public boolean isMaxAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
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
		int a = ((Integer)state.getValue(this.getAgeProperty()));
		if(a == 0 || a == 1 || a == 2 || a == 3 || a == 4 || a == 5)
		{
			return new ItemStack(ModItems.SEED_PINEAPPLE);
		}
		else
		{
			return new ItemStack(ModItems.PINEAPPLE);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		
		int a = ((Integer)state.getValue(this.getAgeProperty()));
		if(a == 0 || a == 1 || a == 2 || a == 3 || a == 4 || a == 5)
		{
			return new ItemStack(ModItems.SEED_PINEAPPLE).getItem();
		}
		else
		{
			return new ItemStack(ModItems.PINEAPPLE).getItem();
		}
    }
	
}
