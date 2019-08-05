package com.tiviacz.pizzacraft.blocks;

import java.util.Random;

import com.tiviacz.pizzacraft.handlers.ConfigHandler;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizzaBoardBase extends BlockPizza
{	
    public static final AxisAlignedBB[] PIZZA_BOARD_AABB = new AxisAlignedBB[] {
    new Bounds(0, 0, 0, 16, 2, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 2, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 2, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 2, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 2, 16).toAABB(),
    new Bounds(0, 0, 0, 16, 2, 16).toAABB()};
    
    private float saturation;
    private int foodstats;
    private Item pizzaslice;

    public BlockPizzaBoardBase(String name, Material material, int foodstats, Float saturation, Item pizzaslice)
    {
        super(name, material, foodstats, saturation, pizzaslice);
 
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(15.0F);
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
    		
    		if(ConfigHandler.isKnifeNeeded)
            {
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
           	}
        	else
        	{
        		if(playerIn.isSneaking())
        		{
        			spawnAsEntity(worldIn, pos, new ItemStack(pizzaslice));
            		
                    if(i < 5)
                   	{
                   		worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
                   	}
                   	else
                   	{
                   		worldIn.setBlockState(pos, ModBlocks.PIZZA_BOARD.getDefaultState(), 3);
                   	}
        		}
        	}
    		return this.eatCake(worldIn, pos, state, playerIn);
    	}
		return true;
    }

    private boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(!player.canEat(false) || player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.KNIFE || player.isSneaking())
        {
            return false;
        }
        else
        {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.getFoodStats().addStats(foodstats, saturation);
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
}