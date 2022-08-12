package com.tiviacz.pizzacraft.blockentity;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.SauceRegistry;
import com.tiviacz.pizzacraft.init.SauceType;

public enum BasinContent {

    AIR("air", BasinContentType.EMPTY, SauceType.NONE),
    MILK("milk", BasinContentType.MILK, SauceType.NONE),
    FERMENTING_MILK("fermenting_milk", BasinContentType.FERMENTING_MILK, SauceType.NONE),
    CHEESE("cheese", BasinContentType.CHEESE, SauceType.NONE),
    TOMATO_SAUCE("tomato_sauce", BasinContentType.SAUCE, SauceType.TOMATO),
    OLIVE_OIL("olive_oil", BasinContentType.OIL, SauceType.NONE); //#TODO WHAT ABOUT SAUCE

    private SauceType sauceType;
    private String name;
    private BasinContentType contentType;

    BasinContent(String name, BasinContentType contentType, SauceType sauce)
    {
        this.name = name;
        this.contentType = contentType;
        this.sauceType = sauce;
        SauceRegistry.INSTANCE.register(this);
    }

    public SauceType getSauceType()
    {
        return this.sauceType;
    }

    public BasinContentType getContentType()
    {
        return this.contentType;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    public String getTranslationKey()
    {
        return new StringBuilder(PizzaCraft.MODID).append(".").append(this.name).toString();
    }
}
