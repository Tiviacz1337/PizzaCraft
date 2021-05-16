package com.tiviacz.pizzacraft.util;

import org.apache.commons.lang3.tuple.Triple;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RenderUtils 
{
	//CyclopsCore part https://github.com/CyclopsMC/EvilCraft/blob/master-1.12/src/main/java/org/cyclops/evilcraft/client/render/tileentity/RenderTileEntityDarkTank.java#L87
	//https://minecraft.curseforge.com/projects/cyclops-core
	//https://github.com/CyclopsMC/CyclopsCore
	//Author: https://minecraft.curseforge.com/members/kroeser
	
	private static final double OFFSET = 0.01D;
	private static final double MINY = OFFSET;
	private static final double MIN = 0.125D + OFFSET;
	private static final double MAX = 0.525D - OFFSET;
	private static double[][][] coordinates = {
        { // DOWN
            {MIN, MINY, MIN},
            {MIN, MINY, MAX},
            {MAX, MINY, MAX},
            {MAX, MINY, MIN}
        },
        { // UP
            {MIN, MAX, MIN},
            {MIN, MAX, MAX},
            {MAX, MAX, MAX},
            {MAX, MAX, MIN}
        },
        { // NORTH
            {MIN, MINY, MIN},
            {MIN, MAX, MIN},
            {MAX, MAX, MIN},
            {MAX, MINY, MIN}
        },
        { // SOUTH
            {MIN, MINY, MAX},
            {MIN, MAX, MAX},
            {MAX, MAX, MAX},
            {MAX, MINY, MAX}
        },
        { // WEST
            {MIN, MINY, MIN},
            {MIN, MAX, MIN},
            {MIN, MAX, MAX},
            {MIN, MINY, MAX}
        },
        { // EAST
            {MAX, MINY, MIN},
            {MAX, MAX, MIN},
            {MAX, MAX, MAX},
            {MAX, MINY, MAX}
        }
    };
	
	public static void renderFluidSides(double height, int color, int brightness) 
	{
        int l2 = brightness >> 0x10 & 0xFFFF;
        int i3 = brightness & 0xFFFF;
        Triple<Float, Float, Float> colorParts = getFluidVertexBufferColor(color);
        float r = colorParts.getLeft();
        float g = colorParts.getMiddle();
        float b = colorParts.getRight();
        float a = 1.0F;
        
		for(EnumFacing side : EnumFacing.VALUES) 
		{
			TextureAtlasSprite icon = getFluidIcon(new FluidStack(FluidRegistry.WATER, 250), side);

            Tessellator t = Tessellator.getInstance();
            BufferBuilder worldRenderer = t.getBuffer();
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			
			double[][] c = coordinates[side.ordinal()];
			double replacedMaxV = (side == EnumFacing.UP || side == EnumFacing.DOWN) ? icon.getInterpolatedV(8D) : ((icon.getMaxV() - icon.getMinV()) * height + icon.getMinV());
			double replacedU1 = (side == EnumFacing.UP || side == EnumFacing.DOWN) ? icon.getInterpolatedU(4D) : icon.getInterpolatedU(7D);
			double replacedU2 = (side == EnumFacing.UP || side == EnumFacing.DOWN) ? icon.getInterpolatedU(12D) : icon.getInterpolatedU(8D);

			worldRenderer.pos(c[0][0], getHeight(side, c[0][1], height), c[0][2]).tex(replacedU1, replacedMaxV).color(r, g, b, a).endVertex();
		    worldRenderer.pos(c[1][0], getHeight(side, c[1][1], height), c[1][2]).tex(replacedU1, icon.getMinV()).color(r, g, b, a).endVertex();
		    worldRenderer.pos(c[2][0], getHeight(side, c[2][1], height), c[2][2]).tex(replacedU2, icon.getMinV()).color(r, g, b, a).endVertex();
		    worldRenderer.pos(c[3][0], getHeight(side, c[3][1], height), c[3][2]).tex(replacedU2, replacedMaxV).color(r, g, b, a).endVertex();
			
			t.draw();
		}
	}
	
	private static double getHeight(EnumFacing side, double height, double replaceHeight) 
	{
		if(height == MAX) 
		{
			return replaceHeight;
		}
		return height;
	}
	
	public static void renderFluidInTank(int color, double x, double y, double z)
	{
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F , 0, 0, 1);
		
		renderFluidContext(x, y, z, new IFluidContextRender() 
        {
            @Override
            public void renderFluid() 
            {
                double height = (0.0625 / 2) * 0.99D;
                RenderUtils.renderFluidSides(height, color, 100);
            }
        });
		
		GlStateManager.popMatrix();
	}
	
	public static TextureAtlasSprite getFluidIcon(FluidStack fluid, EnumFacing side) 
	{
        Block defaultBlock = Blocks.WATER;
        Block block = defaultBlock;
        
        if(fluid.getFluid().getBlock() != null)
        {
            block = fluid.getFluid().getBlock();
        }

        if(side == null) 
        {
        	side = EnumFacing.UP;
        }

        TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getFlowing(fluid).toString());
        
        if(icon == null || (side == EnumFacing.UP || side == EnumFacing.DOWN)) 
        {
            icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill(fluid).toString());
        }
        
        if(icon == null) 
        {
            icon = getBlockIcon(block);
            
            if(icon == null) 
            {
                icon = getBlockIcon(defaultBlock);
            }
        }

        return icon;
    }
	
	public static TextureAtlasSprite getBlockIcon(Block block) 
	{
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(block.getDefaultState());
    }
	
	public static void renderFluidContext(double x, double y, double z, IFluidContextRender render) 
	{
            GlStateManager.pushMatrix();

            GlStateManager.disableBlend();
            GlStateManager.disableCull();

            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GlStateManager.translate(x, y, z);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            render.renderFluid();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
    }
	
	public static Triple<Float, Float, Float> getFluidVertexBufferColor(int color) 
	{
        return intToRGB(color);
    }
	
	public static Triple<Float, Float, Float> intToRGB(int color) 
	{
		float red, green, blue;
		red = (float)(color >> 16 & 255) / 255.0F;
		green = (float)(color >> 8 & 255) / 255.0F;
		blue = (float)(color & 255) / 255.0F;
		return Triple.of(red, green, blue);
	}
	 
	public static interface IFluidContextRender 
	{
        public void renderFluid();
    }
}
