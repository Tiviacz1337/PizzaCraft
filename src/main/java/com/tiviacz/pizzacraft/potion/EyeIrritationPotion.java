package com.tiviacz.pizzacraft.potion;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.util.TextUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EyeIrritationPotion extends Potion
{
	public static final ResourceLocation EFFECT_ICON = TextUtils.setResourceLocation("textures/gui/eye_irritation.png");
	
	public EyeIrritationPotion() 
	{
		super(true, 3112959);
		
		this.setPotionName("effect." + "eye_irritation");
		this.setRegistryName(new ResourceLocation(PizzaCraft.MODID, "eye_irritation"));
		this.setIconIndex(0, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
		Minecraft.getMinecraft().renderEngine.bindTexture(EFFECT_ICON);
        return super.getStatusIconIndex();
    }

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		if(duration <= 0)
		{
			return true;
		}
		return false;
	}
}