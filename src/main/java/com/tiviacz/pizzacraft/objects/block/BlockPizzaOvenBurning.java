package com.tiviacz.pizzacraft.objects.block;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.base.BlockBase;
import com.tiviacz.pizzacraft.tileentity.TileEntityBurningPizzaOven;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizzaOvenBurning extends BlockBase implements ITileEntityProvider
{
	public static final PropertyInteger FIRE = PropertyInteger.create("fire", 0, 3);

	public BlockPizzaOvenBurning(String name, Material material) 
	{
		super(name, material);
		
		setSoundType(SoundType.METAL);
        setHardness(1.5F);
        setResistance(4.0F);
        setHarvestLevel("pickaxe", 1);
        setLightLevel(1.0F);
        setLightOpacity(1);
        setDefaultState(blockState.getBaseState().withProperty(FIRE, 0));
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
		ItemStack helditem = playerIn.getHeldItem(hand);
    	int i = state.getValue(FIRE).intValue();
    	
    	if(helditem.getItem() == Items.WATER_BUCKET)
    	{
    		worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    		
    		if(!worldIn.isRemote)
    		{
    			playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
    			
    			if(i != 3)
    			{
    				worldIn.setBlockState(pos, ModBlocks.PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOven.WOOD, i), 3);
    			}
    			
    			else
        		{
        			worldIn.setBlockState(pos, ModBlocks.PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOven.WOOD, 4), 3);
        		}
    		}
    	}
    	
		if(!worldIn.isRemote)
        {	     	
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
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    }
    
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FIRE, meta);
    }
    
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FIRE).intValue();
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
        return new BlockStateContainer(this, new IProperty[] {FIRE});
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityBurningPizzaOven();
	}
}