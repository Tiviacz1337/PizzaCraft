package com.tiviacz.pizzacraft.blocks.plants;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.Bounds;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class CornPlantBottom extends BlockCrops
{
	public static final PropertyInteger CORN_BOTTOM_AGE = PropertyInteger.create("age", 0, 5);
    private static final AxisAlignedBB[] CORN_BOTTOM_AABB = new AxisAlignedBB[] {
    new Bounds(0, 0, 0, 16, 3, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 9, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 9, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 14, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 15, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 15, 16).toAABB()};
	
    public CornPlantBottom(String name)
    {
    	this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
        this.setUnlocalizedName(name);
		this.setRegistryName(name);
        
        ModBlocks.BLOCKS.add(this);
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	if(state.getValue(CORN_BOTTOM_AGE) != 0)
    	{
    		if(state.getValue(CORN_BOTTOM_AGE) == 1)
    		{
    			entityIn.motionX *= 0.75F;
        		entityIn.motionZ *= 0.75F;
    		}
    		else
    		{
    			entityIn.motionX *= 0.5F;
        		entityIn.motionZ *= 0.5F;
    		}
    	}
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CORN_BOTTOM_AABB[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected PropertyInteger getAgeProperty()
    {
        return CORN_BOTTOM_AGE;
    }

    @Override
    public int getMaxAge()
    {
        return 5;
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
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if(!worldIn.isAreaLoaded(pos, 1)) 
        {
        	return;
        }
        if(worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            float f = this.getGrowthChance(this, worldIn, pos);

            if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
            {
            	int i = this.getAge(state);

            	if(i < this.getMaxAge() - 1)
                {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            	else
            	{            	
                	if(worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR)
                	{
                		worldIn.setBlockState(pos.up(), ModBlocks.CORN_PLANT_TOP.getDefaultState());
                        worldIn.setBlockState(pos, this.withAge(this.getMaxAge()), 2);
                	}
                	else if(worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.CORN_PLANT_TOP)
                	{
                		IBlockState cornTop = worldIn.getBlockState(pos.up());
                		
                		if(((CornPlantTop)cornTop.getBlock()).getAge(cornTop) < ((CornPlantTop)cornTop.getBlock()).getMaxAge())
                		{
                			IBlockState topState = worldIn.getBlockState(pos.up());
                			CornPlantTop topBlock = (CornPlantTop)worldIn.getBlockState(pos.up()).getBlock();
                			int topAge = topBlock.getAge(topState);
                			worldIn.setBlockState(pos.up(), topBlock.withAge(topAge + 1), 2);
                            ForgeHooks.onCropsGrowPost(worldIn, pos.up(), topState, topState); 
                		}
                	}
            	}
            }
        }   
    }
    
    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge() - 1;
    	int excessAge = i - j;
    	
    	if(excessAge <= 0)
    	{
            worldIn.setBlockState(pos, this.withAge(i), 2);
    	}
    	else
    	{

        	if(worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR)
        	{
        		worldIn.setBlockState(pos.up(), ModBlocks.CORN_PLANT_TOP.getDefaultState());
                worldIn.setBlockState(pos, this.withAge(this.getMaxAge()), 2);
        		excessAge--;
        	}

        	if(excessAge > 0 && worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.CORN_PLANT_TOP)
        	{
        		IBlockState cornTopState = worldIn.getBlockState(pos.up());
        		CornPlantTop cornTopBlock = (CornPlantTop)worldIn.getBlockState(pos.up()).getBlock();
        		int cornTopAge = cornTopBlock.getAge(cornTopState);
        		
        		if(cornTopAge < cornTopBlock.getMaxAge())
        		{
        			int newAge = cornTopAge + excessAge;
        			
        			if(newAge > cornTopBlock.getMaxAge())
        			{
        				newAge = cornTopBlock.getMaxAge();
        			}
        			
        	        worldIn.setBlockState(pos.up(), cornTopBlock.withAge(newAge), 2);
        		}
        	}
    	}
    }
    
    @Override
    protected int getBonemealAgeIncrease(World worldIn)
    {
    	return MathHelper.getInt(worldIn.rand, 2, 3);
    }
        
    @Override
    protected Item getSeed()
    {
        return ModItems.SEED_CORN;
    }

    @Override
    protected Item getCrop()
    {
        return ModItems.CORN;
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) 
    {
    	drops.add(new ItemStack(this.getSeed(), this.RANDOM.nextInt(2)));
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this.getSeed());
    }
    
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
    	IBlockState upState = worldIn.getBlockState(pos.up());
    	return this.getAge(state) <= this.getMaxAge() - 1 ? true : upState.getBlock() == Blocks.AIR ? true : upState.getBlock() == ModBlocks.CORN_PLANT_TOP && !((CornPlantTop)upState.getBlock()).isMaxAge(upState);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.grow(worldIn, pos, state);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.withAge(meta);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return this.getAge(state);
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CORN_BOTTOM_AGE});
    }
}
