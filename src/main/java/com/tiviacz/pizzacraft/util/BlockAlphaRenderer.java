package com.tiviacz.pizzacraft.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@OnlyIn(Dist.CLIENT)
public class BlockAlphaRenderer extends BlockModelRenderer
{
    private static BlockAlphaRenderer INSTANCE;

    public static BlockAlphaRenderer getInstance(BlockModelRenderer baseRenderer)
    {
        if (INSTANCE == null || INSTANCE.blockColors != baseRenderer.blockColors)
        {
            INSTANCE = new BlockAlphaRenderer(baseRenderer);
        }

        return INSTANCE;
    }
    public BlockAlphaRenderer(BlockModelRenderer baseRenderer)
    {
        super(baseRenderer.blockColors);
    }

    public static void renderBlockAlpha(BlockPos pos, BlockState state, World world, MatrixStack matrix, IRenderTypeBuffer renderTypeBuffer, IModelData data)
    {
        matrix.pushPose();

        BlockRendererDispatcher blockDispatcher = Minecraft.getInstance().getBlockRenderer();
        BlockModelRenderer renderer = getInstance(blockDispatcher.getModelRenderer());
        renderer.renderModel(
                world,
                blockDispatcher.getBlockModel(state),
                state,
                pos,
                matrix,
                renderTypeBuffer.getBuffer(RenderType.translucent()),
                false,
                world.random,
                state.getSeed(pos),
                OverlayTexture.NO_OVERLAY,
                data);

        matrix.popPose();
    }

    @Override
    public void putQuadData(IBlockDisplayReader world, BlockState state, BlockPos pos, IVertexBuilder buffer, MatrixStack.Entry matrixEntry, BakedQuad quadIn, float tintA, float tintB, float tintC, float tintD, int brightness0, int brightness1, int brightness2, int brightness3, int combinedOverlayIn)
    {
        float r=1F;
        float g=1F;
        float b=1F;
        if (quadIn.isTinted())
        {
            int i = Minecraft.getInstance().getBlockColors().getColor(state, world, pos, quadIn.getTintIndex());
            r = (i >> 16 & 255) / 255.0F;
            g = (i >> 8 & 255) / 255.0F;
            b = (i & 255) / 255.0F;
        }
        // FORGE: Apply diffuse lighting at render-time instead of baking it in
        if (quadIn.isShade()) // better name: shouldApplyDiffuseLighting
        {
            // TODO this should be handled by the forge lighting pipeline
            float forgeLighting = net.minecraftforge.client.model.pipeline.LightUtil.diffuseLight(quadIn.getDirection());
            r *= forgeLighting;
            g *= forgeLighting;
            b *= forgeLighting;
        }

        float alpha = 1.0F;

        if(world.getBlockEntity(pos) instanceof PizzaTileEntity)
        {
            //Fetch the leftBakingTime and convert it to float value to represent smooth transition of baking
            PizzaTileEntity tileEntity = (PizzaTileEntity)world.getBlockEntity(pos);
            alpha = 1.0F - (float)tileEntity.getLeftBakingTime() / tileEntity.getBakingTime();
        }

        // use our method below instead of adding the quad in the usual manner
        addTransparentQuad(matrixEntry, quadIn, new float[] { tintA, tintB, tintC, tintD }, r, g, b, new int[] { brightness0, brightness1, brightness2, brightness3 },
                combinedOverlayIn, true, buffer, alpha);
    }

    // as IVertexBuilder::addQuad except when we add the vertex, we add an opacity float instead of 1.0F
    public static void addTransparentQuad(MatrixStack.Entry matrixEntry, BakedQuad quad, float[] colorMuls, float r, float g, float b, int[] vertexLights,
                                          int combinedOverlayIn, boolean mulColor, IVertexBuilder buffer, float alpha)
    {
        int[] vertexData = quad.getVertices();
        Vector3i faceVector3i = quad.getDirection().getNormal();
        Vector3f faceVector = new Vector3f(faceVector3i.getX(), faceVector3i.getY(), faceVector3i.getZ());
        Matrix4f matrix = matrixEntry.pose();
        faceVector.transform(matrixEntry.normal());

        int vertexDataEntries = vertexData.length / 8;

        try(MemoryStack memorystack = MemoryStack.stackPush())
        {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormats.BLOCK.getVertexSize());
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

                int light = buffer.applyBakedLighting(vertexLights[vertexIndex], bytebuffer);
                float texU = bytebuffer.getFloat(16);
                float texV = bytebuffer.getFloat(20);
                Vector4f posVector = new Vector4f(x, y, z, 1.0F);
                posVector.transform(matrix);
                buffer.applyBakedNormals(faceVector, bytebuffer, matrixEntry.normal());
                buffer.vertex(posVector.x(), posVector.y(), posVector.z(), red, green, blue, alpha, texU, texV,
                        combinedOverlayIn, light, faceVector.x(), faceVector.y(), faceVector.z());
            }
        }
    }
}
