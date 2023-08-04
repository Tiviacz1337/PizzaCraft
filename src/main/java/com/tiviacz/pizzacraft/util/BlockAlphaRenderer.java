package com.tiviacz.pizzacraft.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;

@OnlyIn(Dist.CLIENT)
public class BlockAlphaRenderer extends ModelBlockRenderer
{
    private static BlockAlphaRenderer INSTANCE;

    public static BlockAlphaRenderer getInstance(ModelBlockRenderer baseRenderer)
    {
        if (INSTANCE == null || INSTANCE.blockColors != baseRenderer.blockColors)
        {
            INSTANCE = new BlockAlphaRenderer(baseRenderer);
        }

        return INSTANCE;
    }

    public BlockAlphaRenderer(ModelBlockRenderer baseRenderer)
    {
        super(baseRenderer.blockColors);
    }

    public static void renderBlockAlpha(BlockPos pos, BlockState state, Level level, PoseStack poseStack, MultiBufferSource buffer, IModelData data)
    {
        poseStack.pushPose();

        BlockRenderDispatcher blockDispatcher = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer renderer = getInstance(blockDispatcher.getModelRenderer());
        renderer.tesselateBlock(
                level,
                blockDispatcher.getBlockModel(state),
                state,
                pos,
                poseStack,
                buffer.getBuffer(RenderType.translucent()),
                false,
                level.random,
                state.getSeed(pos),
                OverlayTexture.NO_OVERLAY,
                data);

        poseStack.popPose();
    }

    @Override
    public void putQuadData(BlockAndTintGetter level, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, PoseStack.Pose pose, BakedQuad quad, float tintA, float tintB, float tintC, float tintD, int brightness0, int brightness1, int brightness2, int brightness3, int combinedOverlayIn)
    {
        float r=1F;
        float g=1F;
        float b=1F;

        if (quad.isTinted())
        {
            int i = this.blockColors.getColor(state, level, pos, quad.getTintIndex());
            r = (i >> 16 & 255) / 255.0F;
            g = (i >> 8 & 255) / 255.0F;
            b = (i & 255) / 255.0F;
        }

        float alpha = 1.0F;

        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            //Fetch the leftBakingTime and convert it to float value to represent smooth transition of baking
            PizzaBlockEntity tileEntity = (PizzaBlockEntity)level.getBlockEntity(pos);
            alpha = 1.0F - (float)tileEntity.getBakingTime() / tileEntity.getCalculatedBakingTime();
        }

        // putBulkData with alpha value instead of 1
        vertexConsumer.putBulkData(pose, quad, new float[]{tintA, tintB, tintC, tintD}, r, g, b, alpha, new int[]{brightness0, brightness1, brightness2, brightness3}, combinedOverlayIn, true);
    }
}