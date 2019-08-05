package com.tiviacz.pizzacraft.items;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPeel extends ItemTool implements IHasModel
{
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {});
	
	public static final ToolMaterial PEEL_MATERIAL = EnumHelper.addToolMaterial("peel", 0, 150, 1.0F, 1.0F, 0);
			
	public ItemPeel(String name, float attackDamageIn, float attackSpeedIn) 
	{
		super(attackDamageIn, attackSpeedIn, PEEL_MATERIAL, EFFECTIVE_ON);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setFull3D();
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		setMaxStackSize(1);
		
		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return worldIn == null ? 1.0F : 0.0F;
			}
		});
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public boolean isRepairable()
	{
		return false;
	}

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
}