package com.tiviacz.pizzacraft.compat.curios;

import top.theillusivec4.curios.api.type.capability.ICurio;

public class PizzaCraftCurios
{
    public static ICurio createPizzaBagProvider()
    {
        return new PizzaBagCurio();
    }
}