package com.tiviacz.pizzacraft.objects.items;

import java.awt.Desktop.Action;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.obj.OBJModel.Material;
import net.minecraftforge.common.util.EnumHelper;

public class ItemPeel extends ItemTool implements IHasModel
{
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.AIR});
	
	public static final ToolMaterial PEEL_MATERIAL = EnumHelper.addToolMaterial("peel", 0, 150, 1.0F, 1.0F, 0);
			
	public ItemPeel(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn) 
	{
		super(attackDamageIn, attackSpeedIn, materialIn, EFFECTIVE_ON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setFull3D();
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		this.setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
		
	}

	@Override
	public void registerModels() 
	{
		PizzaCraft.proxy.registerItemRenderer(this, 0, "inventory");		
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemstack) 
	{
		return itemstack;
	} 

	@Override
	public boolean hasContainerItem(ItemStack itemstack) 
	{
		return true;
	} 
	
	@Override
	public boolean isRepairable()
	{
		return false;
	}
}
