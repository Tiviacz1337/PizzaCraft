package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.renderer.ChefHatModel;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ChefArmor extends ArmorItem
{
    public ChefArmor(ArmorMaterial materialIn, ArmorItem.Type pType, Properties builderIn)
    {
        super(materialIn, pType, builderIn);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player)
    {
        if(player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.CHEF_HAT.get() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.CHEF_SHIRT.get()
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.CHEF_LEGGINGS.get() && player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.CHEF_BOOTS.get())
        {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1, 0, false, false));
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> humanoid)
            {
                if(armorSlot == EquipmentSlot.HEAD)
                {
                    ChefHatModel hat = new ChefHatModel(ChefHatModel.createModelData().bakeRoot());
                    humanoid.copyPropertiesTo(hat);

                    return hat;
                }
                return humanoid;
            }
        });
    }

 /*   @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default)
    {
        if(armorSlot == EquipmentSlot.HEAD)
        {
            ChefHatModel hat = new ChefHatModel();

            hat.box1.visible = armorSlot == EquipmentSlot.HEAD;
            hat.box2.visible = armorSlot == EquipmentSlot.HEAD;

            hat.young = _default.young;
            hat.riding = _default.riding;
            hat.crouching = _default.crouching;
            hat.rightArmPose = _default.rightArmPose;
            hat.leftArmPose = _default.leftArmPose;

            return (A)hat;
        }
        return null;
    } */

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
    {
        if(slot == EquipmentSlot.HEAD)
        {
            return new ResourceLocation(PizzaCraft.MODID, "textures/models/armor/chef_hat.png").toString();//PizzaCraft.MODID + ":textures/models/armor/chef_hat.png";
        }
        return null;
    }
}
