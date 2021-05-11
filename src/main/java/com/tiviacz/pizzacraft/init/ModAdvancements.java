package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.advancement.ChoppingBoardTrigger;
import com.tiviacz.pizzacraft.advancement.MortarAndPestleTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModAdvancements
{
    public static ChoppingBoardTrigger CHOPPING_BOARD = new ChoppingBoardTrigger();
    public static MortarAndPestleTrigger MORTAR_AND_PESTLE = new MortarAndPestleTrigger();

    public static void register() {
        CriteriaTriggers.register(CHOPPING_BOARD);
        CriteriaTriggers.register(MORTAR_AND_PESTLE);
    }
}