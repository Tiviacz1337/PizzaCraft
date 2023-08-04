package com.tiviacz.pizzacraft.util;

import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RenderUtils
{
    public static double[] getPosRandomAboveBlockHorizontal(Level level, BlockPos pos)
    {
        double d0 = 0.5D;
        double d5 = 0.5D - d0;
        double d6 = (double)pos.getX() + d5 + level.random.nextDouble() * d0 * 2.0D;
        //double d7 = (double)pos.getY() + world.rand.nextDouble() * y;
        double d8 = (double)pos.getZ() + d5 + level.random.nextDouble() * d0 * 2.0D;

        return new double[] {d6, d8};
    }

    public static List<BakedQuad> colorQuads(List<BakedQuad> quads, int color)
    {
        List<BakedQuad> coloredQuads = new ArrayList<>();

        for(BakedQuad quad : quads)
        {
            ColorTransformer transformer = new ColorTransformer(color, quad);
            quad.pipe(transformer);
            coloredQuads.add(transformer.build());
        }
        return coloredQuads;
    }

    public static int getDominantColor(TextureAtlasSprite sprite, boolean isRaw)
    {
        int iconWidth = sprite.getWidth();
        int iconHeight = sprite.getHeight();

        int frameCount = (int) sprite.getUniqueFrames().count();

        if (iconWidth <= 0 || iconHeight <= 0 || frameCount <= 0) {
            return 0xFFFFFF;
        }
        TreeMap<Integer, Integer> counts = new TreeMap<>();

        for (int f = 0; f < frameCount; f++) {
            for (int v = 0; v < iconWidth; v++) {
                for (int u = 0; u < iconHeight; u++) {
                    int rgba = sprite.getPixelRGBA(f, v, u);
                    int alpha = rgba >> 24 & 0xFF;

                    if (alpha > 0) {
                        counts.merge(rgba, 1, (color, count) -> count + 1);
                    }
                }
            }
        }
        int dominantColor = 0;
        int dominantSum = 0;

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > dominantSum) {
                dominantSum = entry.getValue();
                dominantColor = entry.getKey();
            }
        }
        Color color = new Color(dominantColor, true);
        // No idea why the r and b values are reversed, but they are
        return isRaw ? new Color(color.getBlue(), color.getGreen(), color.getRed()).brighter().brighter().brighter().brighter().brighter().getRGB() : new Color(color.getBlue(), color.getGreen(), color.getRed()).brighter().getRGB();
    }

    // Color Transformer from Mantle
    private static class ColorTransformer extends VertexTransformer {

        private final float r, g, b, a;

        public ColorTransformer(int color, BakedQuad quad) {
            super(new BakedQuadBuilder(quad.getSprite()));

            int a = (color >> 24);

            if (a == 0) {
                a = 255;
            }
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = (color) & 0xFF;

            this.r = (float) r / 255F;
            this.g = (float) g / 255F;
            this.b = (float) b / 255F;
            this.a = (float) a / 255F;
        }

        @Override
        public void put(int element, @Nonnull float... data) {
            VertexFormatElement.Usage usage = parent.getVertexFormat().getElements().get(element)
                    .getUsage();

            // Transform normals and position
            if (usage == VertexFormatElement.Usage.COLOR && data.length >= 4) {
                data[0] = r;
                data[1] = g;
                data[2] = b;
                data[3] = a;
            }
            super.put(element, data);
        }

        public BakedQuad build() {
            return ((BakedQuadBuilder) parent).build();
        }
    }
}