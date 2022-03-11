package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.init.SauceType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class SauceItem extends Item
{
    private final SauceType type;
    public SauceItem(Properties properties, SauceType type)
    {
        super(properties);
        this.type = type;
    }

    public SauceType getSauceType()
    {
        return this.type;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack)
    {
        return UseAction.DRINK;
    }
}