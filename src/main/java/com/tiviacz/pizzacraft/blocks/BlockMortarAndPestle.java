package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;
import com.tiviacz.pizzacraft.util.Bounds;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockMortarAndPestle extends BlockContainer implements IHasModel
{	
	private static final PropertyInteger PESTLE = PropertyInteger.create("pestle", 0, 3);
	private static final AxisAlignedBB MORTAR_AND_PESTLE_AABB = new Bounds(4, 0, 4, 12, 4, 12).toAABB();
	
	public BlockMortarAndPestle(String name, Material material) 
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		
		this.setSoundType(SoundType.STONE);
		this.setResistance(10.0F);
		this.setHardness(2.0F);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(hand == EnumHand.MAIN_HAND)
		{
			ItemStack stack = playerIn.getHeldItem(hand);
			TileEntityMortarAndPestle tile = (TileEntityMortarAndPestle)worldIn.getTileEntity(pos);
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			
			if(stack.isEmpty() && !playerIn.isSneaking())
			{
				int value = state.getValue(PESTLE);
				
				if(value == 3)
				{
					worldIn.setBlockState(pos, state.withProperty(PESTLE, 0));
				}
				else
				{
					worldIn.setBlockState(pos, state.withProperty(PESTLE, value + 1));
				}
				tile.craft();
				return true;
			}
			
			if(!stack.isEmpty() && inv.getStackInSlot(3).isEmpty())
			{
				playerIn.setHeldItem(hand, inv.insertItem(tile.getProperSlotForStack(), playerIn.getHeldItem(hand), false));
				return true;
			}
			
			if(stack.isEmpty() && playerIn.isSneaking())
			{
				playerIn.setHeldItem(hand, inv.extractItem(tile.getProperSlotForTake(), 64, false));
				return true;
			}
		}
		return false;
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityMortarAndPestle tile = (TileEntityMortarAndPestle)worldIn.getTileEntity(pos);
		IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		for(int i = 0; i < inv.getSlots(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			
			if(!stack.isEmpty())
			{
				this.spawnAsEntity(worldIn, pos, stack);
			}
		}
		super.breakBlock(worldIn, pos, state);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return MORTAR_AND_PESTLE_AABB;
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {PESTLE});
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
		return state.getValue(PESTLE);
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(PESTLE, meta);
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityMortarAndPestle();
	}

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}