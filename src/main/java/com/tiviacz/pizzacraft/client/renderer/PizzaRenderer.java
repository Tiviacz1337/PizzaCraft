package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.client.PizzaBakedModel;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.util.BlockAlphaRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.client.model.data.ModelData;

import java.util.Optional;

public class PizzaRenderer implements BlockEntityRenderer<PizzaBlockEntity>
{
    public PizzaRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PizzaBlockEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if(tileEntityIn.isBaking())
        {
            renderPizzaBakingProcess(tileEntityIn, poseStack, bufferIn);
        }
    }

    public void renderPizzaBakingProcess(PizzaBlockEntity tileEntity, PoseStack poseStack, MultiBufferSource bufferIn)
    {
        poseStack.pushPose();

        //Translate and scale renderer to avoid z-fighting
        poseStack.translate(-0.0025D, 0D, -0.0025D);
        poseStack.scale(1.005F, 1.005F, 1.005F);

        //Set IModelData to baked pizza, just as we want it
        ModelData modelData = tileEntity.getModelData();
        modelData.derive().with(PizzaBakedModel.IS_RAW, Optional.of(false));

        //Custom implementation of BlockModelRenderer with accessible alpha value (Thanks Commoble!)
        BlockAlphaRenderer.renderBlockAlpha(tileEntity.getBlockPos(), ModBlocks.PIZZA.get().defaultBlockState(), tileEntity.getLevel(), poseStack, bufferIn, modelData);
        poseStack.popPose();
    }

 /*   public static void render(PizzaTileEntity tile, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn)
    {
        float alpha = 1.0F;

        if(tile.isBaking())
        {
            //renderPizzaBakingProcess(tile, matrixStackIn, bufferIn);
        }

        if(!tile.isEmpty(tile.getInventory()))
        {
            matrixStackIn.push();
            matrixStackIn.translate(0.5D, 0.0D, 0.5D);
            matrixStackIn.scale(1.01F, 1.0F, 1.01F);

            for(int i = 0; i < tile.getInventory().getSlots(); i++)
            {
                if(!tile.getInventory().getStackInSlot(i).isEmpty() && i != 9)
                {
                    //matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90.0F));
                    //matrixStackIn.translate(0.0D, 0.0001D, 0.0D);
                    renderIngredientLayer(tile, tile.getInventory().getStackInSlot(i), matrixStackIn.getLast().getMatrix(), bufferIn, alpha, combinedLightIn);
                }
            }
            matrixStackIn.pop();
        }
    }

    public static void renderIngredientLayer(PizzaTileEntity tileEntity, ItemStack stack, Matrix4f matrix4f, IRenderTypeBuffer bufferIn, float alpha, int combinedLightIn)
    {
        TextureAtlasSprite layer = getLayerIcon(stack);
        IVertexBuilder renderer = bufferIn.getBuffer(RenderType.getText(layer.getAtlasTexture().getTextureLocation()));

        float MaxV, MinV, MaxU, MinU;

        MaxV = layer.getInterpolatedV(14.0D);
        MinV = layer.getInterpolatedV(2.0D);
        MaxU = layer.getInterpolatedU(14.0D);
        MinU = layer.getInterpolatedU(2.0D);

        float X = 0.375F;
        float Y = 0.375F;
        float X1 = 0.375F;
        float X2 = 0.375F;

        if(!tileEntity.isRaw())
        {
            int i = tileEntity.getBlockState().get(PizzaBlock.BITES);
            if(i == 1) { X1 -= 0.0625F; }
            else
                X1 -= ((i - 1) * 0.125F);
                X2 = X2 - (i * 0.0625F);
        }

        renderer.pos(matrix4f, X, MIN, Y).color(1.0F, 1.0F, 1.0F, alpha).tex(MinU, MaxV).lightmap(combinedLightIn).endVertex();
        renderer.pos(matrix4f, X, MIN, -Y).color(1.0F, 1.0F, 1.0F, alpha).tex(MaxU, MaxV).lightmap(combinedLightIn).endVertex();
        renderer.pos(matrix4f, -X1, MIN, -Y).color(1.0F, 1.0F, 1.0F, alpha).tex(MaxU, MinV).lightmap(combinedLightIn).endVertex();
        renderer.pos(matrix4f, -X1, MIN, Y).color(1.0F, 1.0F, 1.0F, alpha).tex(MinU, MinV).lightmap(combinedLightIn).endVertex();
    } */
}