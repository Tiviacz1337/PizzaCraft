package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.renderer.ChefHatModel;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ChefArmor extends ArmorItem
{
    public ChefArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn)
    {
        super(materialIn, slot, builderIn);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        if(player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ModItems.CHEF_HAT.get() && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ModItems.CHEF_SHIRT.get()
                && player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ModItems.CHEF_LEGGINGS.get() && player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ModItems.CHEF_BOOTS.get())
        {
            player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 1, 0, false, false));
        }
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        if(armorSlot == EquipmentSlotType.HEAD)
        {
            ChefHatModel hat = new ChefHatModel();

            hat.box1.visible = armorSlot == EquipmentSlotType.HEAD;
            hat.box2.visible = armorSlot == EquipmentSlotType.HEAD;

            hat.young = _default.young;
            hat.riding = _default.riding;
            hat.crouching = _default.crouching;
            hat.rightArmPose = _default.rightArmPose;
            hat.leftArmPose = _default.leftArmPose;

            return (A)hat;
        }
        return null;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        if(slot == EquipmentSlotType.HEAD)
        {
            return new ResourceLocation(PizzaCraft.MODID, "textures/models/armor/chef_hat.png").toString();//PizzaCraft.MODID + ":textures/models/armor/chef_hat.png";
        }
        return null;
    }
}
