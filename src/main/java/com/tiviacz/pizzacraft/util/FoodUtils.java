package com.tiviacz.pizzacraft.util;

public class FoodUtils
{
    public static boolean requiresAddition(int totalHunger, int slice)
    {
        if(totalHunger % 7 != 0)
        {
            if(totalHunger % 7 == 1) return slice == 0;
            if(totalHunger % 7 == 2) return slice == 0 || slice == 1;
            if(totalHunger % 7 == 3) return slice == 0 || slice == 1 || slice == 2;
            if(totalHunger % 7 == 4) return slice == 0 || slice == 1 || slice == 2 || slice == 3;
            if(totalHunger % 7 == 5) return slice == 0 || slice == 1 || slice == 2 || slice == 3 || slice == 4;
            if(totalHunger % 7 == 6) return slice == 0 || slice == 1 || slice == 2 || slice == 3 || slice == 4 || slice == 5;
        }
        return false;
    }

    public static int getHungerForSlice(int totalHunger, boolean requiresAddition)
    {
        if(totalHunger % 7 != 0)
        {
            if(requiresAddition) return (totalHunger / 7) + 1;
        }
        return totalHunger / 7;
    }

    public static int getHungerForSlice(int totalHunger, int slice)
    {
        if(totalHunger % 7 != 0)
        {
            switch(totalHunger % 7) {
                case 1:
                    if(slice == 0) return (totalHunger / 7) + 1;
                case 2:
                    if(slice == 0 || slice == 1) return (totalHunger / 7) + 1;
                case 3:
                    if(slice == 0 || slice == 1 || slice == 2) return (totalHunger / 7) + 1;
                case 4:
                    if(slice == 0 || slice == 1 || slice == 2 || slice == 3) return (totalHunger / 7) + 1;
                case 5:
                    if(slice == 0 || slice == 1 || slice == 2 || slice == 3 || slice == 4) return (totalHunger / 7) + 1;
                case 6:
                    if(slice == 0 || slice == 1 || slice == 2 || slice == 3 || slice == 4 || slice == 5) return (totalHunger / 7) + 1;
            }
        }
        return totalHunger / 7;
    }
}