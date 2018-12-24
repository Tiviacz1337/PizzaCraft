package com.tiviacz.pizzacraft.objects.item;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.client.ModelDelivererCap;
import com.tiviacz.pizzacraft.init.base.ArmorBase;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDelivererCap extends ArmorBase
{
	public ItemDelivererCap(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
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
				ModelDelivererCap model = new ModelDelivererCap();
				
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
		return "pizzacraft:textures/models/armor/deliver_hat.png";
	}
}