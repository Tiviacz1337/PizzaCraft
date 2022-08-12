package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PizzaWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer
{
    private final Supplier<PizzaBlockEntity> pizza;

    public PizzaWithoutLevelRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet set, Supplier<PizzaBlockEntity> blockEntity)
    {
        super(dispatcher, set);
        this.pizza = blockEntity;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType type, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(stack.getItem() == ModItems.RAW_PIZZA.get() ? ModBlocks.RAW_PIZZA.get().defaultBlockState() : ModBlocks.PIZZA.get().defaultBlockState(), poseStack, buffer, combinedLight, combinedOverlay, getModelData(stack));
    }

    public IModelData getModelData(ItemStack stack)
    {
        return stack.hasTag() ? pizza.get().getItemStackModelData(stack) : EmptyModelData.INSTANCE;
    }
}