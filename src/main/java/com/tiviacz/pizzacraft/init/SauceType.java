package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.util.ResourceLocation;

public enum SauceType
{
    NONE("air", 0, 0.0F, new float[] {0.0F, 0.0F, 0.0F}),
    TOMATO("tomato", 6, 1.5F, new float[] {0.76F, 0.22F, 0.14F}),
    GARLIC("garlic", 6, 1.5F, new float[] {0.0F, 0.0F, 0.0F}),
    HOT("hot", 5, 1.2F, new float[] {0.8F, 0.01F, 0.01F});
    //OLIVE_OIL("olive_oil", 0, 0.0F, new float[] {1.0F, 1.0F, 1.0F}); //¯\_( ͡° ͜ʖ ͡°)_/¯ //#TODO OLIVE RENDERING

    String name;
    int hunger;
    float saturation;
    float[] particleColorRGB;

    SauceType(String name, int hunger, float satModifier, float[] particleColorRGB)
    {
        this.name = name;
        this.hunger = hunger;
        this.saturation = satModifier;
        this.particleColorRGB = particleColorRGB;
        SauceRegistry.INSTANCE.register(this);
    }

    @Override
    public String toString()
    {
        return name;
    }

    public float[] getRGB()
    {
        return particleColorRGB;
    }

    public ResourceLocation getTexture()
    {
        return new ResourceLocation(PizzaCraft.MODID, "textures/block/sauce.png");
    }
}
