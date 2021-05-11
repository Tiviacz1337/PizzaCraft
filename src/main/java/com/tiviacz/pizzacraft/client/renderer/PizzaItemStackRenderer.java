package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PizzaItemStackRenderer extends ItemStackTileEntityRenderer
{
    private final Supplier<PizzaTileEntity> pizza = PizzaTileEntity::new;

    public PizzaItemStackRenderer() {}

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
    {
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(stack.getItem() == ModItems.RAW_PIZZA.get() ? ModBlocks.RAW_PIZZA.get().getDefaultState() : ModBlocks.PIZZA.get().getDefaultState(), matrixStack, buffer, combinedLight, combinedOverlay, getModelData(stack));
    }

    public IModelData getModelData(ItemStack stack)
    {
        return stack.hasTag() ? pizza.get().getItemStackModelData(stack) : EmptyModelData.INSTANCE;
    }
}