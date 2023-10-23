package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.client.renderer.PizzaWithoutLevelRenderer;
import com.tiviacz.pizzacraft.client.tooltip.PizzaTooltipComponent;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.Optional;
import java.util.function.Consumer;

public class PizzaBlockItem extends BlockItem
{
    public PizzaBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions()
        {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return new PizzaWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), () -> new PizzaBlockEntity(BlockPos.ZERO, ModBlocks.PIZZA.get().defaultBlockState()));
            }
        });
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack pStack)
    {
        return Optional.of(new PizzaTooltipComponent(pStack));
    }
}