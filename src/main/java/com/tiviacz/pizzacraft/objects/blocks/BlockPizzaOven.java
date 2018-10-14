package com.tiviacz.pizzacraft.objects.blocks;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
	public static final PropertyInteger WOOD = PropertyInteger.create("wood", 0, 4);

	public BlockPizzaOven(String name, Material material) 
	{
		super(name, material);

		setSoundType(SoundType.METAL);
        setHardness(1.5F);
        setResistance(4.0F);
        setHarvestLevel("pickaxe", 1);
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	if(!worldIn.isRemote)
        {	
        	ItemStack helditem = playerIn.getHeldItem(hand);
        	int i = ((Integer)state.getValue(WOOD)).intValue();
        	IBlockState iBlockState = worldIn.getBlockState(pos.up());
        	
        	if(helditem.getItem() == Items.STICK && (i == 0 || i == 1 || i == 2))
        	{
        		worldIn.setBlockState(pos, state.withProperty(WOOD, i + 1));
        		helditem.shrink(1);
        	}
        	
        	if(helditem.isEmpty() && (i == 1 || i == 2 || i == 3))
        	{
        		worldIn.setBlockState(pos, state.withProperty(WOOD, i - 1));
        		playerIn.inventory.addItemStackToInventory(new ItemStack(Items.STICK));
        	}
        	
        	if(helditem.getItem() == Item.getItemFromBlock(Blocks.NETHERRACK) && i == 0)
        	{
        		worldIn.setBlockState(pos, state.withProperty(WOOD, 4));
        		helditem.shrink(1);
        	}
        	
        	if(helditem.isEmpty() && i == 4)
        	{
        		worldIn.setBlockState(pos, state.withProperty(WOOD, 0));
        		playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.NETHERRACK));
        		
        	}
        	
        	if(helditem.getItem() == Items.FLINT_AND_STEEL && i == 1)
        	{
        		worldIn.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 0));
        	}
        	
        	if(helditem.getItem() == Items.FLINT_AND_STEEL && i == 2)
        	{
        		worldIn.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 1));
        	}
        	
        	if(helditem.getItem() == Items.FLINT_AND_STEEL && i == 3)
        	{
        		worldIn.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 2));
        	}
        	
        	if(helditem.getItem() == Items.FLINT_AND_STEEL && i == 4)
        	{
        		worldIn.setBlockState(pos, ModBlocks.BURNING_PIZZA_OVEN.getDefaultState().withProperty(BlockPizzaOvenBurning.FIRE, 3));
        	}
        	
        	if(iBlockState == Blocks.AIR.getDefaultState())
			{
				if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_0))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_0.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_1))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_1.getDefaultState());
					helditem.shrink(1);
				}

				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_2))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_2.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_3))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_3.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_4))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_4.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_5))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_5.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_6))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_6.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_7))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_7.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_8))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_8.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_9))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_9.getDefaultState());
					helditem.shrink(1);
				}
				
				else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.RAW_PIZZA_10))
				{
					worldIn.setBlockState(pos.up(), ModBlocks.RAW_PIZZA_10.getDefaultState());
					helditem.shrink(1);
				}
			}
        }
		return true;
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
        return this.getDefaultState().withProperty(WOOD, meta);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(WOOD)).intValue();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {WOOD});
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
    	if(!worldIn.isRemote && !player.capabilities.isCreativeMode)
		{
			int i = ((Integer)state.getValue(WOOD)).intValue();
			
			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();
			
	        if(i == 1)
	        {
	        	InventoryHelper.spawnItemStack(worldIn, x, y, z, new ItemStack(Items.STICK));
	        }
	        
	        if(i == 2)
	        {
	        	InventoryHelper.spawnItemStack(worldIn, x, y, z, new ItemStack(Items.STICK, 2));
	        }
	        
	        if(i == 3)
	        {
	        	InventoryHelper.spawnItemStack(worldIn, x, y, z, new ItemStack(Items.STICK, 3));
	        }
	        
	        if(i == 4)
	        {
	        	InventoryHelper.spawnItemStack(worldIn, x, y, z, new ItemStack(Blocks.NETHERRACK));
	        }
		}
    }
}
