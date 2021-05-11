package com.tiviacz.pizzacraft.items;

import com.google.common.collect.Sets;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

public class KnifeItem extends ToolItem
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();

    public KnifeItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties builderIn)
    {
        super(attackDamageIn, attackSpeedIn, tier, EFFECTIVE_ON, builderIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        Material material = state.getMaterial();
        return material != Material.WOOL && material != Material.CARPET && material != Material.CAKE && material != Material.WEB ? super.getDestroySpeed(stack, state) : this.efficiency;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class KnifeEvents
    {
        @SubscribeEvent
        public static void onKnifeKnockback(LivingKnockBackEvent event)
        {
            LivingEntity attacker = event.getEntityLiving().getAttackingEntity();
            ItemStack tool = attacker != null ? attacker.getHeldItem(Hand.MAIN_HAND) : ItemStack.EMPTY;

            if(tool.getItem() instanceof KnifeItem)
            {
                event.setStrength(event.getOriginalStrength() - 0.1F);
            }
        }
    }
}