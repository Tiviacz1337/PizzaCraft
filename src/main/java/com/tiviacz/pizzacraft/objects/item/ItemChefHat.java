package com.tiviacz.pizzacraft.objects.item;

import com.tiviacz.pizzacraft.client.ModelChefHat;
import com.tiviacz.pizzacraft.init.base.ArmorBase;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChefHat extends ArmorBase
{
	public ItemChefHat(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
		if(itemStack != null)
		{
			if(itemStack.getItem() instanceof ItemArmor)
			{
				ModelChefHat model = new ModelChefHat();
				
				model.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
			    model.isChild = _default.isChild;
			    model.isRiding = _default.isRiding;
			    model.isSneak = _default.isSneak;
			    model.rightArmPose = _default.rightArmPose;
			    model.leftArmPose = _default.leftArmPose;
			     
			    return model;
			}
		}
		return null;
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return "pizzacraft:textures/models/armor/chef_hat.png";
	}
}