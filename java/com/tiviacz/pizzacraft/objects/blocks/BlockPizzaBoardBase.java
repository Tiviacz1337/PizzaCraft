package com.tiviacz.pizzacraft.objects.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.base.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPizzaBoardBase extends BlockBase
{	
    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 5);
    protected static final AxisAlignedBB[] PIZZA_BOARD_AABB = new AxisAlignedBB[] {
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D), //Uneaten
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D), //Slice1
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D), //Slice2
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D), //Slice3
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D), //Slice4
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D)}; //Slice5
    
    float saturation;
    int foodstats;

    public BlockPizzaBoardBase(String name, Material material, int foodstats, Float saturation)
    {
        super(name, material);
 
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(15.0F);
        setHarvestLevel("hand", 0);
        this.saturation = saturation;
        this.foodstats = foodstats;
        this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
        this.setTickRandomly(true);       
        
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PIZZA_BOARD_AABB[((Integer)state.getValue(BITES)).intValue()];
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
    	ItemStack HeldItem = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    	int i = ((Integer)state.getValue(BITES)).intValue();
    	
    	if(!worldIn.isRemote)
    	{
    		if(HeldItem.isEmpty() && playerIn.isSneaking() && i == 0 && (playerIn.canEat(true) || playerIn.canEat(false)))
    		{
    			worldIn.setBlockToAir(pos);
    			InventoryHelper.spawnItemStack(worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), new ItemStack(this));
    		}
    		else if(!worldIn.isRemote)
    		{
    			return this.eatCake(worldIn, pos, state, playerIn);
    		}
    		else
    		{
    			ItemStack itemstack = playerIn.getHeldItem(hand);
    			return this.eatCake(worldIn, pos, state, playerIn) || itemstack.isEmpty();
    		}
    	}
		return true;
    }

    private boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (!player.canEat(false))
        {
            return false;
        }
        else
        {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.getFoodStats().addStats(6, 15.0F);    //The amount of food that restores 1 piece
            int i = ((Integer)state.getValue(BITES)).intValue();

            if (i < 5) //The amount of bites after pizza disappears
            {
                worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            }
            else
            {
                worldIn.setBlockState(pos, ModBlocks.pizza_board.getDefaultState());
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
    	int i = ((Integer)state.getValue(BITES)).intValue();
    	
        if (!this.canBlockStay(worldIn, pos))
        {
            if(i == 0)
            {
            	this.dropBlockAsItem(worldIn, pos, state, 1);
            	worldIn.setBlockToAir(pos);
            }
            
            else
            {	
            	ModBlocks.pizza_board.dropBlockAsItem(worldIn, pos, state, 1);
            	worldIn.setBlockToAir(pos);
            }
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	int i = ((Integer)state.getValue(BITES)).intValue();
    	if(i == 0)
    	{
    		return Item.getItemFromBlock(this);
    	}
    	else
    	{
    		return Item.getItemFromBlock(ModBlocks.pizza_board);
    	}

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
}

