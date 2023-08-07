package com.tiviacz.pizzacraft.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
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

    public KnifeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);

        Multimap<Attribute, AttributeModifier> attributeMap = getDefaultAttributeModifiers(EquipmentSlot.MAINHAND);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
        modifierBuilder.putAll(attributeMap);
        modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("KnifeMovementSpeedModifier", 0.1D, AttributeModifier.Operation.MULTIPLY_BASE));
        modifierBuilder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier("KnifeAttackKnockbackModifier", -0.1D, AttributeModifier.Operation.ADDITION));
        this.attributeModifier = modifierBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
    {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifier : super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, level, tooltip, flagIn);

        tooltip.add(Component.translatable("description.pizzacraft.backstab.title").withStyle(ChatFormatting.RED));

        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT))
        {
            tooltip.add(Component.translatable("description.pizzacraft.backstab.description").withStyle(ChatFormatting.BLUE));
        }
        else
        {
            tooltip.add(Component.translatable("description.pizzacraft.hold_shift.title").withStyle(ChatFormatting.BLUE));
        }
    }

    @Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class KnifeEvents
    {
        @SubscribeEvent
        public static void onKnifeBackstab(LivingHurtEvent event)
        {
            LivingEntity victim = event.getEntity();
            Entity attacker = event.getSource().getEntity();

            if(victim != null && attacker != null)
            {
                Level level = attacker.getLevel();

                if(attacker instanceof LivingEntity)
                {
                    if(compareRotations(attacker.yRotO, victim.yRotO, 50.0D))
                    {
                        InteractionHand activeHand = ((LivingEntity)attacker).getUsedItemHand();

                        if(activeHand == InteractionHand.MAIN_HAND)
                        {
                            ItemStack stack = ((LivingEntity)attacker).getItemInHand(activeHand);

                            if(stack.getItem() instanceof KnifeItem)
                            {
                                float newDamage = event.getAmount() * 1.25F;
                                event.setAmount(newDamage);
                                level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, attacker.getSoundSource(), 1.0F, 1.2F);

                                if(level instanceof ServerLevel serverLevel)
                                {
                                    float f = level.random.nextFloat() * (float) Math.PI * 2.0F;
                                    float f1 = level.random.nextFloat() * 0.5F + 0.5F;
                                    float f2 = Mth.sin(f) * 0.5F * f1;
                                    float f3 = Mth.cos(f) * 0.5F * f1;
                                    serverLevel.sendParticles(ParticleTypes.CRIT,
                                            victim.position().x + f2,
                                            victim.getBoundingBox().minY + level.random.nextFloat() + 0.5F,
                                            victim.position().z + f3, 8, (double)(float)Math.pow(2.0D, (level.random.nextInt(169) - 12) / 12.0D) / 24.0D, -1.0D, 0.0D, 0.1);
                                }
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