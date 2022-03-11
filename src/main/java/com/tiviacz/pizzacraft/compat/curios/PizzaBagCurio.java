package com.tiviacz.pizzacraft.compat.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PizzaBagCurio implements ICurio
{
    public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack,
                       IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
                       float headPitch)
    {
        Block pizzaBag = ModBlocks.RED_PIZZA_BAG.get();

        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        matrixStack.scale(0.75F, 0.75F, 0.75F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
        matrixStack.translate(-0.5D, -0.55D, 0.0D);

        RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        RenderHelper.rotateIfSneaking(matrixStack, livingEntity);

        BlockRendererDispatcher blockDispatcher = Minecraft.getInstance().getBlockRenderer();
        BlockModelRenderer renderer = blockDispatcher.getModelRenderer();
        renderer.renderModel(
                livingEntity.level,
                blockDispatcher.getBlockModel(pizzaBag.defaultBlockState()),
                pizzaBag.defaultBlockState(),
                livingEntity.blockPosition().offset(0, 1, 0),
                matrixStack,
                renderTypeBuffer.getBuffer(RenderType.cutout()),
                false,
                livingEntity.getRandom(),
                pizzaBag.defaultBlockState().getSeed(livingEntity.blockPosition()),
                OverlayTexture.NO_OVERLAY,
                EmptyModelData.INSTANCE);

        matrixStack.popPose();
    }
}