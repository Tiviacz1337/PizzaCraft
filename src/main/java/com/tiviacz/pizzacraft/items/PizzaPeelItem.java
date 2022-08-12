package com.tiviacz.pizzacraft.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class PizzaPeelItem extends ShovelItem
{
    private final Multimap<Attribute, AttributeModifier> attributeModifier;

    public PizzaPeelItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);

        Multimap<Attribute, AttributeModifier> attributeMap = getDefaultAttributeModifiers(EquipmentSlot.MAINHAND);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
        modifierBuilder.putAll(attributeMap);
        modifierBuilder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier("PeelAttackKnockbackModifier", 0.25D, AttributeModifier.Operation.ADDITION));
        this.attributeModifier = modifierBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
    {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifier : super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }
}