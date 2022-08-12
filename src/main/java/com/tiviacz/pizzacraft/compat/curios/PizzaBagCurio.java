package com.tiviacz.pizzacraft.compat.curios;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

public record PizzaBagCurio(ItemStack stack) implements ICurio
{
    @Override
    public ItemStack getStack() {
        return stack;
    }
}