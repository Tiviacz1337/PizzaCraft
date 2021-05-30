package com.tiviacz.pizzacraft.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

public class KnifeItem extends SwordItem
{
    private final Multimap<Attribute, AttributeModifier> attributeModifier;

    public KnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);

        Multimap<Attribute, AttributeModifier> attributeMap = getAttributeModifiers(EquipmentSlotType.MAINHAND);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
        modifierBuilder.putAll(attributeMap);
        modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("KnifeMovementSpeedModifier", 0.1D, AttributeModifier.Operation.MULTIPLY_BASE));
        modifierBuilder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier("KnifeAttackKnockbackModifier", -0.1D, AttributeModifier.Operation.ADDITION));
        this.attributeModifier = modifierBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
    {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifier : super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent("description.pizzacraft.backstab.title").mergeStyle(TextFormatting.RED));

        if(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT))
        {
            tooltip.add(new TranslationTextComponent("description.pizzacraft.backstab.description").mergeStyle(TextFormatting.BLUE));
        }
        else
        {
            tooltip.add(new TranslationTextComponent("description.pizzacraft.hold_shift.title").mergeStyle(TextFormatting.BLUE));
        }
    }

    @Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class KnifeEvents
    {
        @SubscribeEvent
        public static void onKnifeBackstab(LivingHurtEvent event)
        {
            LivingEntity victim = event.getEntityLiving();
            Entity attacker = event.getSource().getTrueSource();

            if(victim != null && attacker != null)
            {
                if(attacker instanceof LivingEntity)
                {
                    if(compareRotations(attacker.rotationYaw, victim.rotationYaw, 50.0D))
                    {
                        Hand activeHand = ((LivingEntity)attacker).getActiveHand();

                        if(activeHand == Hand.MAIN_HAND)
                        {
                            ItemStack stack = ((LivingEntity)attacker).getHeldItem(activeHand);

                            if(stack.getItem() instanceof KnifeItem)
                            {
                                float newDamage = event.getAmount() * 1.25F;
                                event.setAmount(newDamage);
                                attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, attacker.getSoundCategory(), 1.0F, 1.2F);
                                Minecraft.getInstance().particles.emitParticleAtEntity(victim, ParticleTypes.CRIT, 10);
                            }
                        }
                    }
                }
            }
        }

        public static boolean compareRotations(double yaw1, double yaw2, double maxDiff)
        {
            maxDiff = Math.abs(maxDiff);
            double d = Math.abs(yaw1 - yaw2) % 360;
            double diff = d > 180.0D ? 360.0D - d : d;

            return diff < maxDiff;
        }
    }
}