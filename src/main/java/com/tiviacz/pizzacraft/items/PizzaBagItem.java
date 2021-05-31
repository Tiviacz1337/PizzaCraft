package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.compat.curios.PizzaCraftCurios;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
    public ITextComponent getDisplayName(ItemStack stack)
    {
        return new TranslationTextComponent(this.getTranslationKey(stack)).mergeStyle(TextFormatting.RED);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        if(PizzaCraft.curiosLoaded)
        {
            return new ICapabilityProvider()
            {
                final LazyOptional<ICurio> curio = LazyOptional.of(PizzaCraftCurios::createPizzaBagProvider);

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