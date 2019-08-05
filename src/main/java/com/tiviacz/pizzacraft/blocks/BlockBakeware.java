package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;
import com.tiviacz.pizzacraft.util.Bounds;
import com.tiviacz.pizzacraft.util.IHasModel;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
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

public class BlockBakeware extends BlockContainer implements IHasModel
{
	public static final AxisAlignedBB BAKEWARE_AABB = new Bounds(1,0,1,15,1,15).toAABB();
	
	public BlockBakeware(String name, Material material) 
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		
		this.setSoundType(SoundType.METAL);
		this.setResistance(10.0F);
		this.setHardness(2.0F);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(worldIn.getTileEntity(pos) instanceof TileEntityBakeware)
		{
			if(!worldIn.isRemote)
			{
				playerIn.openGui(PizzaCraft.instance, Reference.GUI_BAKEWARE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
        return false;
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		TileEntityBakeware te = (TileEntityBakeware)worldIn.getTileEntity(pos);
		
		for(int i = 0; i < te.getSizeInventory(); i++)
		{
			ItemStack content = te.getStackInSlot(i);
			
			if(!content.isEmpty())
			{
				this.spawnAsEntity(worldIn, pos, content);
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
        return BAKEWARE_AABB;
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
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityBakeware();
	}

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
