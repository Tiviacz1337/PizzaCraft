package com.tiviacz.pizzacraft.common;

public class TasteHandler
{
    private final int uniqueness;
    private final int size;

    public TasteHandler(int uniqueness, int size)
    {
        this.uniqueness = uniqueness;
        this.size = size;
    }

    public Taste getTaste()
    {
        int pointer = this.uniqueness - this.size;

        if(pointer == 0) return Taste.DELICIOUS;
        if(pointer >= -3 && pointer < 0) return Taste.TASTY;
        if(pointer >= -6 && pointer < -3) return Taste.GOOD;
        else return Taste.DISGUSTING;
    }

    public enum Taste
    {
        DISGUSTING,
        GOOD,
        TASTY,
        DELICIOUS;

        @Override
        public String toString()
        {
            return this.name();
        }
    }
}