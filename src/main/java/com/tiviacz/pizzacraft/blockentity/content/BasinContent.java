package com.tiviacz.pizzacraft.blockentity.content;

import com.google.common.collect.Maps;
import com.tiviacz.pizzacraft.PizzaCraft;

import java.util.Map;

public enum BasinContent {

    AIR("air", BasinContentType.EMPTY, SauceType.NONE),
    MILK("milk", BasinContentType.MILK, SauceType.NONE),
    FERMENTING_MILK("fermenting_milk", BasinContentType.FERMENTING_MILK, SauceType.NONE),
    CHEESE("cheese", BasinContentType.CHEESE, SauceType.NONE),
    TOMATO_SAUCE("tomato_sauce", BasinContentType.SAUCE, SauceType.TOMATO),
    OLIVE_OIL("olive_oil", BasinContentType.OIL, SauceType.NONE); //#TODO WHAT ABOUT SAUCE

    private final SauceType sauceType;
    private final String name;
    private final BasinContentType contentType;

    BasinContent(String name, BasinContentType contentType, SauceType sauce)
    {
        this.name = name;
        this.contentType = contentType;
        this.sauceType = sauce;
        BasinContentRegistry.REGISTRY.registerContent(this);
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
        return PizzaCraft.MODID + "." + this.name;
    }

    public static class BasinContentRegistry
    {
        public static final BasinContentRegistry REGISTRY = new BasinContentRegistry();

        public Map<String, BasinContent> contentsRegistry = Maps.newHashMap();

        public BasinContentRegistry() {}

        public Map<String, BasinContent> getContentsRegistry()
        {
            return this.contentsRegistry;
        }

        public void registerContent(BasinContent content)
        {
            this.contentsRegistry.put(content.name, content);

            if(content.contentType == BasinContentType.SAUCE)
            {
                SauceRegistry.REGISTRY.register(content);
            }
        }

        public BasinContent fromString(String name)
        {
            if(contentsRegistry.containsKey(name))
            {
                return contentsRegistry.get(name);
            }
            throw new IllegalStateException("Content does not exist in registry!");
        }
    }
}
