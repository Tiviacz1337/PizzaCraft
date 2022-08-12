package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.init.SauceType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class SauceItem extends Item
{
    private final SauceType type;
    public SauceItem(Item.Properties properties, SauceType type)
    {
        super(properties);
        this.type = type;
    }

    public SauceType getSauceType()
    {
        return this.type;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.DRINK;
    }
}