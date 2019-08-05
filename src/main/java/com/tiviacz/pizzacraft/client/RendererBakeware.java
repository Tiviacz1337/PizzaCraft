package com.tiviacz.pizzacraft.client;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class RendererBakeware extends TileEntitySpecialRenderer<TileEntityBakeware>
{
	@Override
	public void render(TileEntityBakeware te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
		List<Item> ingredients = Reference.getIngredientsList(te);
		boolean isDoughHere = isDoughHere(te);
		
		for(int i = 0; i < te.getSizeInventory(); i++)
		{
			float rotation = 60 + i*10F;
			float posX = (float)(x + 0.5);
			float posZ = (float)(z + 0.5);
				
			if(i <= 2)
			{
				posX += -0.15 + i*0.15;
				posZ -= 0.2;
			}
				
			if(i > 2 && i <= 5)
			{
				posX += -0.8 + i*0.2;
			}
				
			if(i > 5 && i <= 8)
			{
				posX += -1.05 + i*0.15;
				posZ += 0.2;
			}
				
			ItemStack stack = te.getStackInSlot(i);
				
			if(stack.getItem() == ModItems.PIZZA_DOUGH)
			{
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
				model = ForgeHooksClient.handleCameraTransforms(model, TransformType.NONE, false);
					
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					
				GlStateManager.enableRescaleNormal();
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
				GlStateManager.enableBlend();
				RenderHelper.enableStandardItemLighting();
				GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
					
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5, y + 0.04, z + 0.5);
				GlStateManager.rotate(90F, 1, 0, 0);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
				GlStateManager.popMatrix();
					
				GlStateManager.disableRescaleNormal();
				GlStateManager.disableBlend();
			}
				
			if(ingredients.contains(stack.getItem()) && isDoughHere)
			{
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
				model = ForgeHooksClient.handleCameraTransforms(model, TransformType.NONE, false);
					
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					
				GlStateManager.enableRescaleNormal();
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
				GlStateManager.enableBlend();
				RenderHelper.enableStandardItemLighting();
				GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
					
				GlStateManager.pushMatrix();
				GlStateManager.translate(posX, y + 0.08, posZ);
				GlStateManager.rotate(90F, 1, 0, 0);
					
				GlStateManager.rotate(rotation, 0, 0, 1);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
				GlStateManager.popMatrix();
					
				GlStateManager.disableRescaleNormal();
				GlStateManager.disableBlend();
			}
		}
    }
	
	public boolean isDoughHere(TileEntityBakeware te)
	{
		for(int i = 0; i < te.getSizeInventory(); i++)
		{
			if(te.getStackInSlot(i).getItem() == ModItems.PIZZA_DOUGH)
			{
				return true;
			}
		}
		return false;
	}
}