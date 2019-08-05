package com.tiviacz.pizzacraft.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.handlers.SoundHandler;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.BlockBase;
import com.tiviacz.pizzacraft.tileentity.TileEntityRawPizza;
import com.tiviacz.pizzacraft.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRawPizza extends BlockBase implements ITileEntityProvider
{
	public static final AxisAlignedBB RAW_PIZZA_AABB = new Bounds(1, 0, 1, 15, 1, 15).toAABB();

	public BlockRawPizza(String name, Material material) 
	{
		super(name, material);
		
		setSoundType(SoundType.CLOTH);
        setHardness(0.5F);
        setResistance(2.5F);
        setHarvestLevel("hand", 0);
        this.setTickRandomly(true);
	
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return RAW_PIZZA_AABB;
    }
	
	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		if(worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.BURNING_PIZZA_OVEN)
		{
			worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundHandler.PIZZA_SIZZLING, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
		}
		
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(tile instanceof TileEntityRawPizza)
		{
			if(((TileEntityRawPizza)tile).isCooking())
			{
				worldIn.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 0, 0.025, 0);
			}
		}
    }
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	if(!worldIn.isRemote)
        {	
        	ItemStack helditem = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        	
        	if(helditem.getItem() == ModItems.PEEL && (!playerIn.capabilities.isCreativeMode || playerIn.capabilities.isCreativeMode))
        	{
        		spawnAsEntity(worldIn, pos, new ItemStack(this));
        		worldIn.setBlockToAir(pos);
        		playerIn.getHeldItem(hand).damageItem(1, playerIn);
        	}
        }
		return true;
    }
    
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;       
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {	
        if(!this.canBlockStay(worldIn, pos))
        {	
        	worldIn.setBlockToAir(pos);
        }      
    }
        
    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
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
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityRawPizza();
	}
}