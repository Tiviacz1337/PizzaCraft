package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.client.renderer.PizzaItemStackRenderer;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class PizzaBlockItem extends BlockItem
{
    public PizzaBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer)
    {
        super.initializeClient(consumer);

        consumer.accept(new IItemRenderProperties()
        {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer()
            {
                return new PizzaItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), () -> new PizzaBlockEntity(BlockPos.ZERO, ModBlocks.PIZZA.get().defaultBlockState()));
            }
        });
    }
}