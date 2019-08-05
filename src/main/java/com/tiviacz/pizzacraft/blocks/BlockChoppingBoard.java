package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.crafting.chopping.ChoppingBoardRecipes;
import com.tiviacz.pizzacraft.crafting.chopping.ChoppingBoardUtils;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModPotions;
import com.tiviacz.pizzacraft.tileentity.TileEntityChoppingBoard;
import com.tiviacz.pizzacraft.util.Bounds;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockChoppingBoard extends BlockContainer implements IHasModel
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final AxisAlignedBB CHOPPING_BOARD_AABB = new Bounds(0.125D, 0D, 0.25D, 0.875D, 0.046875D, 0.75D).toAABB();
	private static final AxisAlignedBB CHOPPING_BOARD_AABB2 = new Bounds(0.25D, 0D, 0.125D, 0.75D, 0.046875D, 0.875D).toAABB();
	
	public BlockChoppingBoard(String name, Material material) 
	{
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		this.setSoundType(SoundType.WOOD);
		this.setResistance(10.0F);
		this.setHardness(2.0F);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		EnumFacing facing = state.getValue(FACING);
		
		switch(facing)
		{
		case NORTH:
			return CHOPPING_BOARD_AABB;
		case SOUTH:
			return CHOPPING_BOARD_AABB;
		case EAST: 
			return CHOPPING_BOARD_AABB2;
		case WEST: 
			return CHOPPING_BOARD_AABB2;
		default:
			return CHOPPING_BOARD_AABB;
		}
    }
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(hand == EnumHand.MAIN_HAND)
		{
			TileEntityChoppingBoard tile = (TileEntityChoppingBoard)worldIn.getTileEntity(pos);
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			ItemStack stack = playerIn.getHeldItem(hand);
			
			if(ChoppingBoardUtils.isItemValid(stack))
			{
				if(inv.getStackInSlot(0).isEmpty() && canHit(state.getValue(FACING), hitX, hitZ, 0))
				{
					playerIn.setHeldItem(hand, inv.insertItem(0, stack, false));
					return true;
				}
			}
			
			else if(!inv.getStackInSlot(0).isEmpty() && stack.isEmpty() && canHit(state.getValue(FACING), hitX, hitZ, 0))
			{
				playerIn.setHeldItem(hand, inv.extractItem(0, 64, false));
				return true;
			}
			
			else if(stack.getItem() == ModItems.KNIFE && canHit(state.getValue(FACING), hitX, hitZ, 0) && !inv.getStackInSlot(0).isEmpty())
			{
				if(tile.chop())
				{
					if(ChoppingBoardRecipes.instance().getChoppingResult(tile.inventory.getStackInSlot(0)).getItem() == ModItems.ONION_SLICE)
					{
						playerIn.addPotionEffect(new PotionEffect(ModPotions.EYE_IRRITATION_EFFECT, 100, 0, true, true));
					}
					
					stack.damageItem(1, playerIn);
					worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return true;
				}
			}
			
			else if(!inv.getStackInSlot(1).isEmpty() && canHit(state.getValue(FACING), hitX, hitZ, 1) && stack.isEmpty())
			{
				playerIn.setHeldItem(hand, inv.extractItem(1, 64, false));
				return true;
			}
			
			else if(inv.getStackInSlot(2).isEmpty() && stack.getItem() == ModItems.KNIFE && canHit(state.getValue(FACING), hitX, hitZ, 1))
			{
				playerIn.setHeldItem(hand, inv.insertItem(2, stack, false));
				return true;
			}
			
			else if(!inv.getStackInSlot(2).isEmpty() && stack.isEmpty() && canHit(state.getValue(FACING), hitX, hitZ, 1))
			{
				playerIn.setHeldItem(hand, inv.extractItem(2, 64, false));
				return true;
			}
		}
		return false;
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityChoppingBoard tile = (TileEntityChoppingBoard)worldIn.getTileEntity(pos);
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
	
	private boolean canHit(EnumFacing facing, float hitX, float hitZ, int slot)
	{
		if(facing == EnumFacing.NORTH)
		{
			if(hitX >= 0.5F && slot == 0)
			{
				return true;
			}
			
			if(hitX <= 0.5F && slot == 1)
			{
				return true;
			}
		}
		
		if(facing == EnumFacing.SOUTH)
		{
			if(hitX <= 0.5F && slot == 0)
			{
				return true;
			}
			
			if(hitX >= 0.5F && slot == 1)
			{
				return true;
			}
		}
		
		if(facing == EnumFacing.EAST)
		{
			if(hitZ >= 0.5F && slot == 0)
			{
				return true;
			}
			
			if(hitZ <= 0.5F && slot == 1)
			{
				return true;
			}
		}
		
		if(facing == EnumFacing.WEST)
		{
			if(hitZ <= 0.5F && slot == 0)
			{
				return true;
			}
			
			if(hitZ >= 0.5F && slot == 1)
			{
				return true;
			}
		}
		
		return false;
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
		return state.getValue(FACING).getIndex();
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if(enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityChoppingBoard();
	}

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}