package com.tiviacz.pizzacraft.items;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.blockentity.PizzaHungerSystem;
import com.tiviacz.pizzacraft.util.FoodUtils;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PizzaSliceItem extends Item
{
    public PizzaSliceItem(Properties properties)
    {
        super(properties.food(new FoodProperties.Builder().build()));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
        int hunger = PizzaHungerSystem.BASE_HUNGER / 7;
        float saturation = PizzaHungerSystem.BASE_SATURATION / 7;
        List<Pair<MobEffectInstance, Float>> effects = new ArrayList<>();

        if(stack.getTag() != null)
        {
            ItemStackHandler tempHandler = Utils.createHandlerFromStack(stack, 9);
            boolean requiresAddition = false;
            if(stack.getTag().contains("RequiresAddition"))
            {
                requiresAddition = stack.getTag().getBoolean("RequiresAddition");
            }
            PizzaHungerSystem instance = new PizzaHungerSystem(tempHandler);
            hunger = FoodUtils.getHungerForSlice(instance.getHunger(), requiresAddition);
            saturation = (float)(Math.round(instance.getSaturation() / 7 * 100.0) / 100.0);
            effects = instance.getEffects();
        }
        //TranslationTextComponent translation = new TranslationTextComponent("information.pizzacraft.hunger", hunger).mergeStyle(TextFormatting.BLUE);
        tooltip.add(Component.translatable("information.pizzacraft.hunger_slice", hunger).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("information.pizzacraft.saturation_slice", saturation).withStyle(ChatFormatting.BLUE));

        if(!effects.isEmpty())
        {
            tooltip.add(Component.translatable("information.pizzacraft.effects").withStyle(ChatFormatting.GOLD));

            for(Pair<MobEffectInstance, Float> pair : effects)
            {
                tooltip.add(Component.translatable(pair.getFirst().getDescriptionId()).withStyle(pair.getFirst().getEffect().getCategory().getTooltipFormatting()));
            }
        }
        //tooltip.add(new StringTextComponent("Restores: " + hunger + " Hunger").mergeStyle(TextFormatting.BLUE));
        //tooltip.add(new StringTextComponent("Restores: " + saturation + " Saturation").mergeStyle(TextFormatting.BLUE));
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity livingEntity)
    {
        if(livingEntity instanceof Player)
        {
            if(stack.getTag() != null)
            {
                ItemStackHandler tempHandler = Utils.createHandlerFromStack(stack, 9);

                boolean requiresAddition = false;

                if(stack.getTag().contains("RequiresAddition"))
                {
                    requiresAddition = stack.getTag().getBoolean("RequiresAddition");
                }

                PizzaHungerSystem instance = new PizzaHungerSystem(tempHandler);

                Player player = (Player)livingEntity;
                player.getFoodData().eat(FoodUtils.getHungerForSlice(instance.getHunger(), requiresAddition), instance.getSaturation() / 7);

                for(int i = 0; i < tempHandler.getSlots(); i++)
                {
                    if(!tempHandler.getStackInSlot(i).isEmpty())
                    {
                        if(tempHandler.getStackInSlot(i).isEdible())
                        {
                            FoodProperties food = tempHandler.getStackInSlot(i).getItem().getFoodProperties();

                            if(!food.getEffects().isEmpty())
                            {
                                food.getEffects().forEach(e ->
                                {
                                    if(level.random.nextFloat() < e.getSecond())
                                    {
                                        player.addEffect(e.getFirst());
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        return livingEntity.eat(level, stack);
    }
}