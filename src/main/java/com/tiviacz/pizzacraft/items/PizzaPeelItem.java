package com.tiviacz.pizzacraft.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;

public class PizzaPeelItem extends ShovelItem
{
    private final Multimap<Attribute, AttributeModifier> attributeModifier;

    public PizzaPeelItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);

        Multimap<Attribute, AttributeModifier> attributeMap = getAttributeModifiers(EquipmentSlotType.MAINHAND);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
        modifierBuilder.putAll(attributeMap);
        modifierBuilder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier("PeelAttackKnockbackModifier", 0.25D, AttributeModifier.Operation.ADDITION));
        this.attributeModifier = modifierBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
    {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifier : super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.damageItem(1, attacker, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }
}