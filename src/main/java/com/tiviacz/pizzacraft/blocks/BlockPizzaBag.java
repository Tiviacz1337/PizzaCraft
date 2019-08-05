package com.tiviacz.pizzacraft.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizzaBag;
import com.tiviacz.pizzacraft.util.Bounds;
import com.tiviacz.pizzacraft.util.IHasModel;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizzaBag extends BlockContainer implements IHasModel
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final AxisAlignedBB PIZZA_BAG_AABB = new Bounds(0, 0, 0, 16, 5, 16).toAABB();
	
	public BlockPizzaBag(String name, Material materialIn) 
	{
		super(materialIn);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		setSoundType(SoundType.CLOTH);
        setHardness(0.5F);
        setResistance(2.5F);
        setHarvestLevel("hand", 0);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!playerIn.isSneaking())
		{
			if(!worldIn.isRemote)
			{
				playerIn.openGui(PizzaCraft.instance, Reference.GUI_PIZZA_BAG, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		else
		{
			TileEntityPizzaBag tile = (TileEntityPizzaBag)worldIn.getTileEntity(pos);
			tile.dropAsItem(worldIn, pos);
			worldIn.setBlockToAir(pos);
		}
		return true;
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);

        if(this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            List<ItemStack> items = new ArrayList<ItemStack>();
            ItemStack itemstack = this.getSilkTouchDrop(state);

            if(!itemstack.isEmpty())
            {
                items.add(itemstack);
            }

            ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
            
            for(ItemStack item : items)
            {
                spawnAsEntity(worldIn, pos, item);
            }
        }
        else
        {
            harvesters.set(player);
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            
            if(te instanceof TileEntityPizzaBag)
    		{
    			TileEntityPizzaBag tile = (TileEntityPizzaBag)te;

    			if(!player.capabilities.isCreativeMode)
    			{
    				tile.dropAsItem(worldIn, pos);
    			}
    		}
            
            harvesters.set(null);
        }
    }
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if(tileentity instanceof TileEntityPizzaBag)
       	{
			((TileEntityPizzaBag)tileentity).loadData(stack.getTagCompound());
       	}
    }
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return PIZZA_BAG_AABB;
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
		return new TileEntityPizzaBag();
	}

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}