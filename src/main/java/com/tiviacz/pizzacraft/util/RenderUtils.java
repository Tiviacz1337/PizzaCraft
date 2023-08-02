package com.tiviacz.pizzacraft.util;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.awt.*;
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
}