package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.renderer.PizzaDeliveryCapModel;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
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
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class PizzaDeliveryArmor extends ArmorItem
{
    public PizzaDeliveryArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn)
    {
        super(materialIn, slot, builderIn);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player)
    {
        if(player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.PIZZA_DELIVERY_CAP.get() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.PIZZA_DELIVERY_SHIRT.get()
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.PIZZA_DELIVERY_LEGGINGS.get() && player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.PIZZA_DELIVERY_BOOTS.get())
        {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 0, false, false));
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer)
    {
        consumer.accept(new IItemRenderProperties()
        {
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> humanoid)
            {
                if(armorSlot == EquipmentSlot.HEAD)
                {
                    PizzaDeliveryCapModel hat = new PizzaDeliveryCapModel(PizzaDeliveryCapModel.createModelData().bakeRoot());
                    humanoid.copyPropertiesTo(hat);

                    return hat;
                }
                return null;
            }
        });
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
    {
        if(slot == EquipmentSlot.HEAD)
        {
            return new ResourceLocation(PizzaCraft.MODID, "textures/models/armor/pizza_delivery_cap.png").toString();//PizzaCraft.MODID + ":textures/models/armor/chef_hat.png";
        }
        return null;
    }
}