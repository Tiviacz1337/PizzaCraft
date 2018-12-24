package com.tiviacz.pizzacraft.objects.block;

import java.util.Random;

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
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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

public class BlockPizzaBurnt extends BlockBase
{
    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 5);
    public static final AxisAlignedBB[] PIZZA_BURNT_AABB = new AxisAlignedBB[] {
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D),
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D),
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.875D, 0.0625D, 0.9375D),
    new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.5D, 0.0625D, 0.9375D),
    new AxisAlignedBB(0.0625D, 0.0D, 0.375D, 0.5D, 0.0625D, 0.9375D),
    new AxisAlignedBB(0.125D, 0.0D, 0.5625D, 0.5D, 0.0625D, 0.9375D)};

    public BlockPizzaBurnt(String name, Material material)
    {
        super(name, material);
 
        setSoundType(SoundType.CLOTH);
        setHardness(0.5F);
        setResistance(2.5F);
        setHarvestLevel("hand", 0);
        setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
        setTickRandomly(true);     
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PIZZA_BURNT_AABB[state.getValue(BITES).intValue()];
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
    	if(!worldIn.isRemote)
        {
        	ItemStack helditem = playerIn.getHeldItem(hand);
        	int a = state.getValue(BITES).intValue();
        	
        	if(helditem.getItem() == ModItems.PEEL && a == 0 && (!playerIn.capabilities.isCreativeMode || playerIn.capabilities.isCreativeMode))
        	{
        		worldIn.setBlockToAir(pos);
        		helditem.damageItem(1, playerIn);
        		spawnAsEntity(worldIn, pos, new ItemStack(this));
        	}
        	return this.eatCake(worldIn, pos, state, playerIn);
        }
    	return true;
    }

    private boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {	
    	ItemStack helditem = player.getHeldItem(player.getActiveHand());
    	int a = state.getValue(BITES).intValue();
    	
        if(helditem.getItem() == ModItems.PEEL)
        {
        	return false;
        }
        else
        {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 183, 30));
            player.getFoodStats().addStats(2, 0.0F);

            if (a < 5)
            {	
            	worldIn.setBlockState(pos, state.withProperty(BITES, a + 1), 3);
            }
            else
            {
                worldIn.setBlockToAir(pos);
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
    public int quantityDropped(Random random)
    {
        return 0;
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