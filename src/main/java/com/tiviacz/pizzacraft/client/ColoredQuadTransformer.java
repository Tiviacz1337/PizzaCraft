package com.tiviacz.pizzacraft.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.client.model.IQuadTransformer;

import javax.annotation.Nonnull;
import java.util.List;

public class ColoredQuadTransformer implements IQuadTransformer
{
    private int color;

    public void color(List<BakedQuad> quads, int color)
    {
        this.color = color;
        this.processInPlace(quads);
    }

    @Override
    public void processInPlace(@Nonnull BakedQuad quad)
    {
        int[] vertices = quad.getVertices();
        int a = (this.color >> 24);

        if (a == 0) {
            a = 255;
        }
        int r = (this.color >> 16) & 0xFF;
        int g = (this.color >> 8) & 0xFF;
        int b = (this.color) & 0xFF;

        for (int i = 0; i < 4; i++) {
            int offset = i * IQuadTransformer.STRIDE + IQuadTransformer.COLOR;
            vertices[offset] = ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((g & 0xFF) << 8) | (r & 0xFF);
        }
    }
}
