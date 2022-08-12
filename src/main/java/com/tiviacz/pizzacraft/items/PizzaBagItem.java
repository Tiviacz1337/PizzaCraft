package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.compat.curios.PizzaBagCurio;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PizzaBagItem extends BlockItem
{
    public PizzaBagItem(Block blockIn, Properties properties)
    {
        super(blockIn, properties);
    }

    @Override
    public Component getName(ItemStack stack)
    {
        return new TranslatableComponent(this.getDescriptionId(stack)).withStyle(ChatFormatting.RED);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        if(PizzaCraft.curiosLoaded)
        {
            return new ICapabilityProvider()
            {
                final LazyOptional<ICurio> curio = LazyOptional.of(() -> new PizzaBagCurio(ItemStack.EMPTY));

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
                {
                    return CuriosCapability.ITEM.orEmpty(cap, curio);
                }
            };
        }
        return null;
    }
}