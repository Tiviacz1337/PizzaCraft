package com.tiviacz.pizzacraft.objects.item;

import com.tiviacz.pizzacraft.init.base.ItemBase;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMilkBottle extends ItemBase
{		
	public ItemMilkBottle(String name)
	{
		super(name);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(!worldIn.isRemote)
        {
	        if(entityLiving instanceof EntityPlayerMP)
	        {
	            EntityPlayerMP entityplayermp = (EntityPlayerMP)entityLiving;
	            CriteriaTriggers.CONSUME_ITEM.trigger(entityplayermp, stack);
	        }
	
	        if(entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
	        {
	            stack.shrink(1);
	        }
	
	        entityLiving.removePotionEffect(MobEffects.BLINDNESS);
	        entityLiving.removePotionEffect(MobEffects.HUNGER);
	        entityLiving.removePotionEffect(MobEffects.NAUSEA);
	        entityLiving.removePotionEffect(MobEffects.POISON);
	        entityLiving.removePotionEffect(MobEffects.SLOWNESS);
	        entityLiving.removePotionEffect(MobEffects.WEAKNESS);
	        entityLiving.removePotionEffect(MobEffects.WITHER);
        }
        return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
	
	@Override
	public boolean hasContainerItem(ItemStack itemstack) 
	{
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemstack) 
	{
		return new ItemStack(Items.GLASS_BOTTLE);
	}
}