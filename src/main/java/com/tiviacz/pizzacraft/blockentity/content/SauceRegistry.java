package com.tiviacz.pizzacraft.blockentity.content;

import com.google.common.collect.Maps;

import java.util.Map;

public class SauceRegistry
{
    public static final SauceRegistry REGISTRY = new SauceRegistry();

    private Map<String, SauceType> typesRegistry = Maps.newHashMap();
    private Map<SauceType, BasinContent> typeToContent = Maps.newHashMap();

    public SauceRegistry()
    {

    }

    public void register(SauceType type)
    {
        this.typesRegistry.put(type.toString(), type);
    }

    public void register(BasinContent content)
    {
        if(content.getContentType() == BasinContentType.SAUCE)
        {
            this.typeToContent.put(content.getSauceType(), content);
        }
    }
}
