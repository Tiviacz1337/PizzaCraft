package com.tiviacz.pizzacraft.objects.item;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.ItemBase;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;

public class ItemPizzaShield extends ItemShield implements IHasModel
{
	public ItemPizzaShield(String name, int damage) 
	{
		super();
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PizzaCraft.PIZZACRAFTTAB);
		setMaxStackSize(1);
		setMaxDamage(damage);
        
        ModItems.ITEMS.add(this);
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
    {
		return I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
}