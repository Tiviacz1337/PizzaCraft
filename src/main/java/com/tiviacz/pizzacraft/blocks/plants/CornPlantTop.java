package com.tiviacz.pizzacraft.blocks.plants;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.util.Bounds;
import com.tiviacz.pizzacraft.util.HarvestingUtils;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CornPlantTop extends CornPlantBottom
{
	public static final PropertyInteger CORN_TOP_AGE = PropertyInteger.create("age", 0, 5);
	private static final AxisAlignedBB[] CORN_BOTTOM_AABB = new AxisAlignedBB[] {
	new Bounds(0, 0, 0, 16, 10, 16).toAABB(),
	new Bounds(0, 0, 0, 16, 10, 16).toAABB(),
	new Bounds(0, 0, 0, 16, 10, 16).toAABB(), 
	new Bounds(0, 0, 0, 16, 15, 16).toAABB(), 
	new Bounds(0, 0, 0, 16, 15, 16).toAABB(), 
	new Bounds(0, 0, 0, 16, 15, 16).toAABB()};
	
	public CornPlantTop(String name) 
	{
		super(name);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CORN_BOTTOM_AABB[state.getValue(this.getAgeProperty())];
    }
	
	@Override
	protected PropertyInteger getAgeProperty()
    {
        return CORN_TOP_AGE;
    }
	
	@Override
	public int getMaxAge()
    {
        return 5;
    }
	
	@Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == ModBlocks.CORN_PLANT_BOTTOM;
    }
    
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) 
	{
		
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
	
    public float getGrowthChance(World par1World, int par2, int par3, int par4)
    {
        return 0.0F;
    }
    
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState cornBottom = worldIn.getBlockState(pos.down());
        return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && (cornBottom.getBlock() == ModBlocks.CORN_PLANT_BOTTOM && cornBottom.getBlock().getMetaFromState(cornBottom) >= ((CornPlantBottom)cornBottom.getBlock()).getMaxAge() - 1); 
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	super.getDrops(drops, world, pos, state, fortune);
        int age = getAge(state);
        Random rand = new Random();

        if(age >= this.getMaxAge())
        {
        	if(rand.nextInt(2 * getMaxAge()) <= age)
        	{
        		drops.add(new ItemStack(this.getSeed(), 1, 0));
        	}

        	drops.add(new ItemStack(this.getCrop(), 1, 0));
        	
        	for(int i = 0; i < 1 + fortune; ++i)
            {
                if(rand.nextInt(2 * getMaxAge()) <= age)
                {
                	drops.add(new ItemStack(this.getCrop(), 1, 0));
                }
            }
        }
        else
        {
        	drops.add(new ItemStack(this.getSeed(), 1, 0));
        } 
    }
    
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
    	return !this.isMaxAge(state);
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CORN_TOP_AGE});
    }
}