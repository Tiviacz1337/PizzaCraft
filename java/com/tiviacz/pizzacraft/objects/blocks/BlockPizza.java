package com.tiviacz.pizzacraft.objects.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.BlockBase;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizza;

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
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizza extends BlockBase implements ITileEntityProvider
{	
	
    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 5);
    protected static final AxisAlignedBB[] PIZZA_AABB = new AxisAlignedBB[] {
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D), //Uneaten
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D), //Slice1
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.875D, 0.0625D, 0.9375D), //Slice2
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.5D, 0.0625D, 0.9375D), //Slice3
    new AxisAlignedBB(0.0625D, 0.0D, 0.375D, 0.5D, 0.0625D, 0.9375D), //Slice4
    new AxisAlignedBB(0.125D, 0.0D, 0.5625D, 0.5D, 0.0625D, 0.9375D)}; //Slice5
    
    float saturation;
    int foodstats;

    public BlockPizza(String name, Material material, int foodstats, Float saturation)
    {
        super(name, material);
 
        setSoundType(SoundType.CLOTH);
        setHardness(0.5F);
        setResistance(2.5F);
        setHarvestLevel("hand", 0);
        this.saturation = saturation;
        this.foodstats = foodstats;
        this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
        this.setTickRandomly(true);
       
    }

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PIZZA_AABB[((Integer)state.getValue(BITES)).intValue()];
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	if(!worldIn.isRemote)
        {	
    		int i = ((Integer)state.getValue(BITES)).intValue();
        	ItemStack HeldItem = playerIn.getHeldItem(EnumHand.MAIN_HAND);        	
        	ItemStack Peel = new ItemStack(ModItems.peel);
        	
        	if(HeldItem.getItem() == ModItems.peel && i == 0 && (!playerIn.capabilities.isCreativeMode || playerIn.capabilities.isCreativeMode))
        	{
        		InventoryHelper.spawnItemStack(worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), new ItemStack(this));

        		worldIn.setBlockToAir(pos);
        		playerIn.getHeldItem(EnumHand.MAIN_HAND).damageItem(1, playerIn);
        	}
        }
    	if (!worldIn.isRemote)
        {
            return this.eatCake(worldIn, pos, state, playerIn);
        }
        else
        {
            ItemStack itemstack = playerIn.getHeldItem(hand);
            return this.eatCake(worldIn, pos, state, playerIn) || itemstack.isEmpty();
        }
    }
   
    private boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {	
    	ItemStack HeldItem = player.getHeldItem(EnumHand.MAIN_HAND);
    	
        if (!player.canEat(false))
        {
            return false;
        }
        else if(HeldItem.getItem() == ModItems.peel)
        {
        	return false;
        }
        else
        {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.getFoodStats().addStats(foodstats, saturation);    //The amount of food that restores 1 piece
            int i = ((Integer)state.getValue(BITES)).intValue();

            if (i < 5) //The amount of bites after pizza disappears
            {	
            	if(HeldItem.getItem() == ModItems.peel)
            	{
            		return false;
            	}
            	else
                worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            }
            else
            {
                worldIn.setBlockToAir(pos);
            }

            return true;
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;       
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {	
        if (!this.canBlockStay(worldIn, pos))
        {	
        	worldIn.setBlockToAir(pos);
        }      
    }

    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BITES, meta);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(BITES)).intValue();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BITES});
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityPizza();
	}
}