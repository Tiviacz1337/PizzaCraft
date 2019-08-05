package com.tiviacz.pizzacraft.client;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tiviacz.pizzacraft.tileentity.TileEntityMortarAndPestle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class RendererMortarAndPestle extends TileEntitySpecialRenderer<TileEntityMortarAndPestle>
{
	@Override
	public void render(TileEntityMortarAndPestle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
		List<ItemStack> inv = te.getActualInputs();
		double height = y + 0.08;
		int count = 0;
		float rotation = 0F;
		
		for(ItemStack stack : inv)
		{
			if(!stack.isEmpty())
			{
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
				model = ForgeHooksClient.handleCameraTransforms(model, TransformType.NONE, false);
					
				if(model.isGui3d())
				{
					Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					
					GlStateManager.enableRescaleNormal();
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
					GlStateManager.enableBlend();
					RenderHelper.enableStandardItemLighting();
					GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
							
					GlStateManager.pushMatrix();
					GlStateManager.translate(x + 0.5, height, z + 0.5);
					GlStateManager.scale(0.1D, 0.1D, 0.1D);
					GlStateManager.rotate(90F, 1, 0, 0);
					GlStateManager.rotate(rotation, 0, 0, 1);
					Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
					GlStateManager.popMatrix();
					
					count++;
					rotation = (count % 2 == 1) ? rotation += 20 : rotation - 40;
					height += 0.085;
					
					GlStateManager.disableRescaleNormal();
					GlStateManager.disableBlend();
				}
				else
				{
					Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
							
					GlStateManager.enableRescaleNormal();
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
					GlStateManager.enableBlend();
					RenderHelper.enableStandardItemLighting();
					GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
							
					GlStateManager.pushMatrix();
					GlStateManager.translate(x + 0.5, height, z + 0.5);
					GlStateManager.scale(0.4D, 0.4D, 0.4D);
					GlStateManager.rotate(90F, 1, 0, 0);
					GlStateManager.rotate(rotation, 0, 0, 1);
					Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
					GlStateManager.popMatrix();
					
					count++;
					rotation = (count % 2 == 1) ? rotation += 20 : rotation - 40;
					height += 0.0275;
					
					GlStateManager.disableRescaleNormal();
					GlStateManager.disableBlend();
				}
			}
			
		/*	if(stack.getItem() == ModItems.OLIVE_OIL)
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);
				int color = 14393875;
				RenderUtils.renderFluidInTank(color, -0.823, -0.12, 0.178);
				GlStateManager.popMatrix();
			}  */
		}
    }
}
