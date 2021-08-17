package com.tiviacz.pizzacraft.init;

import com.google.common.collect.Maps;
import com.tiviacz.pizzacraft.tileentity.BasinContent;
import com.tiviacz.pizzacraft.tileentity.BasinContentType;

import java.util.Map;

public class SauceRegistry
{
    public static final SauceRegistry INSTANCE = new SauceRegistry();

    private Map<String, SauceType> sauceTypes = Maps.newHashMap();
    private Map<String, BasinContent> basinContents = Maps.newHashMap();
    private Map<SauceType, BasinContent> typeToSauce = Maps.newHashMap();
    //private Map<SauceType, ItemStack> typeToSauceItemStack = Maps.newHashMap();
    //private Map<BasinContent, ItemStack> contentToItemStack = Maps.newHashMap();
    //private Map<BasinContent, Integer> contentExtractSize = Maps.newHashMap();

    public SauceRegistry()
    {
        //this.typeToSauceItemStack.put(SauceType.TOMATO, new ItemStack(ModItems.TOMATO_SAUCE.get(), 1));
        //this.contentToItemStack.put(BasinContent.OLIVE_OIL, new ItemStack(ModItems.OLIVE_OIL.get(), 1));
        //this.contentExtractSize.put(BasinContent.AIR, 999);
        //this.contentExtractSize.put(BasinContent.MILK, 999);
        //this.contentExtractSize.put(BasinContent.FERMENTING_MILK, 999);
        //this.contentExtractSize.put(BasinContent.CHEESE, 30);
        //this.contentExtractSize.put(BasinContent.TOMATO_SAUCE, 5);
        //this.contentExtractSize.put(BasinContent.OLIVE_OIL, 15);
    }

    public void register(SauceType type)
    {
        this.sauceTypes.put(type.toString(), type);
    }

    public void register(BasinContent content)
    {
        this.basinContents.put(content.toString(), content);
        if(content.getContentType() == BasinContentType.SAUCE)
        {
            this.typeToSauce.put(content.getSauceType(), content);
        }
    }

    public BasinContent basinContentFromString(String name)
    {
       // System.out.println(this.basinContents.keySet());
        return this.basinContents.get(name);
    }

    public SauceType sauceTypeFromString(String name)
    {
        return this.sauceTypes.get(name);
    }

/*    public ItemStack sauceItemFromSauceType(SauceType type)
    {
        return this.typeToSauceItemStack.get(type);
    }

    public ItemStack contentToItemStack(BasinContent content)
    {
        return this.contentToItemStack.get(content);
    }

    public int contentExtractSize(BasinContent content)
    {
        return this.contentExtractSize.get(content);
    } */
}
