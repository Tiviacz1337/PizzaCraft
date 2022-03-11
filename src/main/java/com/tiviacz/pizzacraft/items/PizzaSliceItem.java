package com.tiviacz.pizzacraft.items;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.tileentity.PizzaHungerSystem;
import com.tiviacz.pizzacraft.util.FoodUtils;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
        super(properties.food(new Food.Builder().build()));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        int hunger = PizzaHungerSystem.BASE_HUNGER / 7;
        float saturation = PizzaHungerSystem.BASE_SATURATION / 7;
        List<Pair<EffectInstance, Float>> effects = new ArrayList<>();

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
        tooltip.add(new TranslationTextComponent("information.pizzacraft.hunger_slice", hunger).withStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("information.pizzacraft.saturation_slice", saturation).withStyle(TextFormatting.BLUE));

        if(!effects.isEmpty())
        {
            tooltip.add(new TranslationTextComponent("information.pizzacraft.effects").withStyle(TextFormatting.GOLD));

            for(Pair<EffectInstance, Float> pair : effects)
            {
                tooltip.add(new TranslationTextComponent(pair.getFirst().getDescriptionId()).withStyle(pair.getFirst().getEffect().getCategory().getTooltipFormatting()));
            }
        }
        //tooltip.add(new StringTextComponent("Restores: " + hunger + " Hunger").mergeStyle(TextFormatting.BLUE));
        //tooltip.add(new StringTextComponent("Restores: " + saturation + " Saturation").mergeStyle(TextFormatting.BLUE));
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity livingEntity)
    {
        if(livingEntity instanceof PlayerEntity)
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

                PlayerEntity player = (PlayerEntity)livingEntity;
                player.getFoodData().eat(FoodUtils.getHungerForSlice(instance.getHunger(), requiresAddition), instance.getSaturation() / 7);

                for(int i = 0; i < tempHandler.getSlots(); i++)
                {
                    if(!tempHandler.getStackInSlot(i).isEmpty())
                    {
                        if(tempHandler.getStackInSlot(i).isEdible())
                        {
                            Food food = tempHandler.getStackInSlot(i).getItem().getFoodProperties();

                            if(!food.getEffects().isEmpty())
                            {
                                food.getEffects().forEach(e ->
                                {
                                    if(worldIn.random.nextFloat() < e.getSecond())
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
        return livingEntity.eat(worldIn, stack);
    }
}