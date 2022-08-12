package com.tiviacz.pizzacraft.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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

    public static void renderBlockAlpha(BlockPos pos, BlockState state, Level level, PoseStack poseStack, MultiBufferSource buffer, ModelData data)
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
                data, null);

        poseStack.popPose();
    }

    @Override
    public void putQuadData(BlockAndTintGetter level, BlockState state, BlockPos pos, VertexConsumer consumer, PoseStack.Pose pose, BakedQuad quadIn, float tintA, float tintB, float tintC, float tintD, int brightness0, int brightness1, int brightness2, int brightness3, int combinedOverlayIn)
    {
        float r=1F;
        float g=1F;
        float b=1F;
        if (quadIn.isTinted())
        {
            int i = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, quadIn.getTintIndex());
            r = (i >> 16 & 255) / 255.0F;
            g = (i >> 8 & 255) / 255.0F;
            b = (i & 255) / 255.0F;
        }
        // FORGE: Apply diffuse lighting at render-time instead of baking it in
        //if (quadIn.isShade()) // better name: shouldApplyDiffuseLighting
        //{
       //     // TODO this should be handled by the forge lighting pipeline
       //     float forgeLighting = net.minecraftforge.client.model.lighting.FlatQuadLighterLightUtil.diffuseLight(quadIn.getDirection());
       //     r *= forgeLighting;
       //     g *= forgeLighting;
       //     b *= forgeLighting;
       // }

        float alpha = 1.0F;

        if(level.getBlockEntity(pos) instanceof PizzaBlockEntity)
        {
            //Fetch the leftBakingTime and convert it to float value to represent smooth transition of baking
            PizzaBlockEntity tileEntity = (PizzaBlockEntity)level.getBlockEntity(pos);
            alpha = 1.0F - (float)tileEntity.getLeftBakingTime() / tileEntity.getBakingTime();
        }

        // use our method below instead of adding the quad in the usual manner
        addTransparentQuad(pose, quadIn, new float[] { tintA, tintB, tintC, tintD }, r, g, b, new int[] { brightness0, brightness1, brightness2, brightness3 },
                combinedOverlayIn, true, consumer, alpha);
    }

    // as IVertexBuilder::addQuad except when we add the vertex, we add an opacity float instead of 1.0F
    public static void addTransparentQuad(PoseStack.Pose pose, BakedQuad quad, float[] colorMuls, float r, float g, float b, int[] vertexLights,
                                          int combinedOverlayIn, boolean mulColor, VertexConsumer consumer, float alpha)
    {
        int[] vertexData = quad.getVertices();
        Vec3i faceVector3i = quad.getDirection().getNormal();
        Vector3f faceVector = new Vector3f(faceVector3i.getX(), faceVector3i.getY(), faceVector3i.getZ());
        Matrix4f matrix = pose.pose();
        faceVector.transform(pose.normal());

        int vertexDataEntries = vertexData.length / 8;

        try(MemoryStack memorystack = MemoryStack.stackPush())
        {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int vertexIndex = 0; vertexIndex < vertexDataEntries; ++vertexIndex)
            {
                intbuffer.clear();
                intbuffer.put(vertexData, vertexIndex * 8, 8);
                float x = bytebuffer.getFloat(0);
                float y = bytebuffer.getFloat(4);
                float z = bytebuffer.getFloat(8);
                float red = colorMuls[vertexIndex] * r;
                float green = colorMuls[vertexIndex] * g;
                float blue = colorMuls[vertexIndex] * b;


                if (mulColor)
                {
                    float redMultiplier = (bytebuffer.get(12) & 255) / 255.0F;
                    float greenMultiplier = (bytebuffer.get(13) & 255) / 255.0F;
                    float blueMultiplier = (bytebuffer.get(14) & 255) / 255.0F;
                    red = redMultiplier * red;
                    green = greenMultiplier * green;
                    blue = blueMultiplier * blue;
                }

                int light = consumer.applyBakedLighting(vertexLights[vertexIndex], bytebuffer);
                float texU = bytebuffer.getFloat(16);
                float texV = bytebuffer.getFloat(20);
                Vector4f posVector = new Vector4f(x, y, z, 1.0F);
                posVector.transform(matrix);
                consumer.applyBakedNormals(faceVector, bytebuffer, pose.normal());
                consumer.vertex(posVector.x(), posVector.y(), posVector.z(), red, green, blue, alpha, texU, texV,
                        combinedOverlayIn, light, faceVector.x(), faceVector.y(), faceVector.z());
            }
        }
    }
}
