package com.tiviacz.pizzacraft.objects.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.BlockBase;
import com.tiviacz.pizzacraft.tileentity.TileEntityBurningPizzaOven;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
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
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
		ItemStack heldItem = playerIn.getHeldItem(hand);
    	IBlockState IBlockstate = worldIn.getBlockState(pos.up());
    	int i = ((Integer)state.getValue(FIRE)).intValue(); 
    	
    	if(heldItem.getItem() == Items.WATER_BUCKET)
    	{
    		worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    		
    		if(!worldIn.isRemote)
    		{
    			if(i == 0)
        		{
        			worldIn.setBlockState(pos, ModBlocks.pizza_oven.getDefaultState().withProperty(BlockPizzaOven.WOOD, 0));
        			playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
        		}
        		
        		else if(i == 1)
        		{
        			worldIn.setBlockState(pos, ModBlocks.pizza_oven.getDefaultState().withProperty(BlockPizzaOven.WOOD, 1));
        			playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
        		}
        		
        		else if(i == 2)
        		{
        			worldIn.setBlockState(pos, ModBlocks.pizza_oven.getDefaultState().withProperty(BlockPizzaOven.WOOD, 2));
        			playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
        		}
    			
        		else if(i == 3)
        		{
        			worldIn.setBlockState(pos, ModBlocks.pizza_oven.getDefaultState().withProperty(BlockPizzaOven.WOOD, 4));
        			playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
        		}
    		}
    	}
    	
		if(!worldIn.isRemote)
        {	     	
        	if(IBlockstate == Blocks.AIR.getDefaultState())
			{
				if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_0))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_0.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_1))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_1.getDefaultState());
					heldItem.shrink(1);
				}

				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_2))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_2.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_3))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_3.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_4))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_4.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_5))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_5.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_6))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_6.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_7))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_7.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_8))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_8.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_9))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_9.getDefaultState());
					heldItem.shrink(1);
				}
				
				else if(heldItem.getItem() == Item.getItemFromBlock(ModBlocks.raw_pizza_10))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.raw_pizza_10.getDefaultState());
					heldItem.shrink(1);
				}
			}
        }
    	return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    }
	
	public boolean isFullCube(IBlockState state)
    {
        return true;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FIRE, meta);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(FIRE)).intValue();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FIRE});
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityBurningPizzaOven();
	}

}
