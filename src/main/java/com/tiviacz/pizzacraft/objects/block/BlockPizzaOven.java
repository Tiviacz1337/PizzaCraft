package com.tiviacz.pizzacraft.objects.block;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.base.BlockBase;

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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizzaOven extends BlockBase
{
	public static final PropertyInteger STATE = PropertyInteger.create("wood", 0, 4);

	public BlockPizzaOven(String name, Material material) 
	{
		super(name, material);

		setSoundType(SoundType.METAL);
        setHardness(1.5F);
        setResistance(4.0F);
        setHarvestLevel("pickaxe", 1);
        setDefaultState(blockState.getBaseState().withProperty(STATE, 0));
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
    {
        return true;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	if(!worldIn.isRemote)
        {	
        	ItemStack helditem = playerIn.getHeldItem(hand);
        	int a = state.getValue(STATE).intValue();
        	
        	if(helditem.getItem() == Items.STICK && (a == 0 || a == 1 || a == 2))
        	{
        		worldIn.setBlockState(pos, state.withProperty(STATE, a + 1), 3);
        		helditem.shrink(1);
        	}
        	
        	if(helditem.getItem() == Item.getItemFromBlock(Blocks.NETHERRACK) && a == 0)
        	{
        		worldIn.setBlockState(pos, state.withProperty(STATE, 4), 3);
        		helditem.shrink(1);
        	}
        	
        	if(helditem.isEmpty() && a > 0)
        	{
        		if(a == 4)
        		{
        			worldIn.setBlockState(pos, state.withProperty(STATE, 0), 3);
            		playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.NETHERRACK));
        		}
        		
        		else
        		{
        			worldIn.setBlockState(pos, state.withProperty(STATE, a - 1), 3);
            		playerIn.inventory.addItemStackToInventory(new ItemStack(Items.STICK));
        		}
        	}
        	
        	if(helditem.getItem() == Items.FLINT_AND_STEEL)
        	{
        		if(a != 0)
        		{
        			worldIn.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, a - 1), 3);
        		}
        	}
        	
        	if(worldIn.isAirBlock(pos.up()))
			{
				if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_0))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_0.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_1))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_1.getDefaultState(), 3);
					helditem.shrink(1);
				}

				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_2))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_2.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_3))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_3.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_4))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_4.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_5))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_5.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_6))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_6.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_7))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_7.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_8))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_8.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_9))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_9.getDefaultState(), 3);
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_10))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_10.getDefaultState(), 3);
					helditem.shrink(1);
				}
			}
        }
		return true;
    }
    
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STATE, meta);
    }
    
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STATE).intValue();
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
        return new BlockStateContainer(this, new IProperty[] {STATE});
    }
    
	@Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
    	int a = state.getValue(STATE).intValue();
    	
    	if(!worldIn.isRemote && !player.capabilities.isCreativeMode)
		{	
    		if(a != 4)
    		{
    			if(a != 0)
    			{
    				spawnAsEntity(worldIn, pos, new ItemStack(Items.STICK, a));
    			}
    		}
    		else
    		{
    			spawnAsEntity(worldIn, pos, new ItemStack(Blocks.NETHERRACK));
    		}
		}
    }
}