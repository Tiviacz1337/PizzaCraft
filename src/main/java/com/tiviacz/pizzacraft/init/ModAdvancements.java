package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.advancement.ChoppingBoardTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModAdvancements
{
    public static ChoppingBoardTrigger CHOPPING_BOARD = new ChoppingBoardTrigger();

    public static void register() {
        CriteriaTriggers.register(CHOPPING_BOARD);
    }
}