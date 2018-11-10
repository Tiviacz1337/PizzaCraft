package com.tiviacz.pizzacraft.objects.blocks;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.BlockBase;

import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizzaBoard extends BlockBase
{
	public static final AxisAlignedBB PIZZA_BOARD_AABB = new AxisAlignedBB(0D, 0, 0D, 1D, 0.0625D, 1D);
		
	public BlockPizzaBoard(String name, Material material)
	{
		super(name, material);
		
		setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(15.0F);
        setHarvestLevel("hand", 0);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PIZZA_BOARD_AABB;    
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false; 
	}
	
	private boolean canBlockStay(World worldIn, BlockPos pos)
    {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!this.canBlockStay(worldIn, pos))
        {
        	this.dropBlockAsItem(worldIn, pos, state, 1);
            worldIn.setBlockToAir(pos);
        }
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
	    return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}
	
	@Override		   
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
        	ItemStack helditem = playerIn.getHeldItem(hand);
        	
        	if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_0))
            {     
                worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_0.getDefaultState());
                helditem.shrink(1);
            }
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_1))
        	{
                worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_1.getDefaultState());
                helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_2))
        	{
                worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_2.getDefaultState());
                helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_3))
        	{
                worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_3.getDefaultState());
                helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_4))
        	{
                worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_4.getDefaultState());
                helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_5))
        	{
        		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_5.getDefaultState());
        		helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_6))
        	{
        		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_6.getDefaultState());
        		helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_7))
        	{
        		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_7.getDefaultState());
        		helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_8))
        	{
        		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_8.getDefaultState());
        		helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_9))
        	{
        		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_9.getDefaultState());
        		helditem.shrink(1);
        	}
        	else if(helditem.getItem() == Item.getItemFromBlock(ModBlocks.PIZZA_10))
        	{
        		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD_10.getDefaultState());
        		helditem.shrink(1);
        	}
        	else 
        	{   
                InventoryHelper.spawnItemStack(worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), new ItemStack(this));
                worldIn.setBlockToAir(pos);
        	}
        }
		return true;
    }
}
	

