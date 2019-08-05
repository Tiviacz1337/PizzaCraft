package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

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
		return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

	@Override
	public void registerModel() 
	{
		PizzaCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
}