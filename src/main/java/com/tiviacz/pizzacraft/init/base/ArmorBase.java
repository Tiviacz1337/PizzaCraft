package com.tiviacz.pizzacraft.init.base;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ArmorBase extends ItemArmor implements IHasModel
{
	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.PIZZA_DELIVER_HAT && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.PIZZA_DELIVER_SHIRT
		&& player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == ModItems.PIZZA_DELIVER_LEGGINGS && player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.PIZZA_DELIVER_BOOTS)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, 0, false, false));
		}
		
		if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.CHEF_HAT && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.CHEF_SHIRT
		&& player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == ModItems.CHEF_LEGGINGS && player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.CHEF_BOOTS)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1, 0, false, false));
		}
	}
}