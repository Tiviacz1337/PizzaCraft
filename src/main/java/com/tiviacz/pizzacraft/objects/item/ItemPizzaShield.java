package com.tiviacz.pizzacraft.objects.item;

import javax.annotation.Nullable;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.base.ItemBase;
import com.tiviacz.pizzacraft.util.IHasModel;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        
        addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }
	
	public String getItemStackDisplayName(ItemStack stack)
    {
		return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
}