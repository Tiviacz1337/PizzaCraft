package com.tiviacz.pizzacraft.blocks.trees;

import java.util.List;
import java.util.Random;

import com.tiviacz.pizzacraft.handlers.ConfigHandler;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;

import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockLeavesGrowing extends BlockLeavesBase implements IGrowable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	
	public BlockLeavesGrowing(String name) 
	{
		super(name);
		
		type = name.replaceAll("_leaves_growing", "").trim();
		
		setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if(state.getValue(AGE) >= 2) 
		{
			if(type.equals("olive"))
			{
				if(this.getAge(state) == 2)
				{
					spawnAsEntity(world, pos.offset(side), new ItemStack(ModItems.OLIVE));
				}
				
				if(this.isMaxAge(state))
				{
					spawnAsEntity(world, pos.offset(side), new ItemStack(ModItems.BLACK_OLIVE));
				}
			}
			
			world.setBlockState(pos, state.withProperty(AGE, 0));
			
			return true;
		}
		return false;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) 
	{
		super.updateTick(worldIn, pos, state, rand);

		int i = state.getValue(AGE);

		if(i < this.getMaxAge()) 
		{
			if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(ConfigHandler.oliveGrowChance) == 0)) 
			{
				worldIn.setBlockState(pos, state.withProperty(AGE, (i + 1)), 2);
				ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
		}
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        if(type.equals("olive"))
		{
        	if(this.isMaxAge(state))
        	{
        		drops.add(new ItemStack(ModItems.BLACK_OLIVE));
        	}
        	
        	if(this.getAge(state) == 2)
        	{
        		drops.add(new ItemStack(ModItems.OLIVE));
        	}
		}
    }
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) 
	{
		return NonNullList.withSize(1, new ItemStack(ModBlocks.OLIVE_LEAVES));
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) 
	{
		return new ItemStack(ModBlocks.OLIVE_LEAVES);
	}

	public int getMaxAge()
	{
		return 3;
	}
	
	public PropertyInteger getAgeProperty()
    {
        return AGE;
    }
	
	public int getAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty());
    }
	
	public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

    public boolean isMaxAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) 
	{
		return state.getValue(AGE) < getMaxAge();
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		return state.getValue(AGE) < getMaxAge();
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		int i = state.getValue(AGE) + this.getBonemealAgeIncrease(worldIn);
		int j = this.getMaxAge();
		if (i > j) {
			i = j;
		}
		worldIn.setBlockState(pos, state.withProperty(AGE, i), 2);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(this);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(AGE, (meta & 3)).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		i |= state.getValue(AGE);

		if(!state.getValue(DECAYABLE)) 
		{
			i |= 4;
		}
		if(state.getValue(CHECK_DECAY)) 
		{
			i |= 8;
		}

		return i;
	}
	
	public int getBonemealAgeIncrease(World worldIn) 
	{
		return MathHelper.getInt(worldIn.rand, 1, 2);
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {AGE, CHECK_DECAY, DECAYABLE});
	}
}