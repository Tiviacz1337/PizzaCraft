package com.tiviacz.pizzacraft.objects.block;

import java.util.Random;

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
import net.minecraft.entity.player.EntityPlayer;
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
    public static final AxisAlignedBB[] PIZZA_BOARD_AABB = new AxisAlignedBB[] {
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D),
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D),
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D),
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D),
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D),
    new AxisAlignedBB(0D, 0, 0D, 1D, 0.125D, 1D)};
    
    float saturation;
    int foodstats;
    Item pizzaslice;

    public BlockPizzaBoardBase(String name, Material material, int foodstats, Float saturation, Item pizzaslice)
    {
        super(name, material);
 
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(15.0F);
        setHarvestLevel("hand", 0);
        setDefaultState(blockState.getBaseState().withProperty(BITES, 0));
        setTickRandomly(true);   
        this.saturation = saturation;
        this.foodstats = foodstats;
        this.pizzaslice = pizzaslice;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PIZZA_BOARD_AABB[state.getValue(BITES).intValue()];
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack helditem = playerIn.getHeldItem(hand);
    	int i = state.getValue(BITES).intValue();
    	
    	if(!worldIn.isRemote)
    	{
    		if(helditem.isEmpty() && playerIn.isSneaking() && i == 0 && (playerIn.canEat(true) || playerIn.canEat(false)))
    		{
    			spawnAsEntity(worldIn, pos, new ItemStack(this));
    			worldIn.setBlockToAir(pos);
    		}
    		
    		if(helditem.getItem() == ModItems.KNIFE)
        	{
    			spawnAsEntity(worldIn, pos, new ItemStack(pizzaslice));
    			helditem.damageItem(1, playerIn);
    			
        		if(i < 5)
        		{
        			worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
        		}
        		else
        		{
        			worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD.getDefaultState(), 3);
        		}
        	}
    		return this.eatCake(worldIn, pos, state, playerIn);
    	}
		return true;
    }

    private boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(!player.canEat(false) || player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.KNIFE)
        {
            return false;
        }
        else
        {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.getFoodStats().addStats(6, 15.0F);
            int i = state.getValue(BITES).intValue();

            if(i < 5)
            {
                worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            }
            else
            {
                worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD.getDefaultState(), 3);
            }

            return true;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	int i = state.getValue(BITES).intValue();
    	
        if (!this.canBlockStay(worldIn, pos))
        {
            if(i == 0)
            {
            	this.dropBlockAsItem(worldIn, pos, state, 1);
            	worldIn.setBlockToAir(pos);
            }
            
            else
            {	
            	ModBlocks.PIZZA_BOARD.dropBlockAsItem(worldIn, pos, state, 1);
            	worldIn.setBlockToAir(pos);
            }
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 1;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	int i = state.getValue(BITES).intValue();
    	
    	if(i == 0)
    	{
    		return Item.getItemFromBlock(this);
    	}
    	else
    	{
    		return Item.getItemFromBlock(ModBlocks.PIZZA_BOARD);
    	}

    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BITES, meta);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BITES).intValue();
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
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BITES});
    }
}