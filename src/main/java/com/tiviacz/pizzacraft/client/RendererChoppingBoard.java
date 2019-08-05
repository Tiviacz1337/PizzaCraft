package com.tiviacz.pizzacraft.client;

import org.lwjgl.opengl.GL11;

import com.tiviacz.pizzacraft.blocks.BlockChoppingBoard;
import com.tiviacz.pizzacraft.tileentity.TileEntityChoppingBoard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;

public class RendererChoppingBoard extends TileEntitySpecialRenderer<TileEntityChoppingBoard>
{
	@Override
	public void render(TileEntityChoppingBoard te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
		ItemStack input = te.inventory.getStackInSlot(0);
		ItemStack result = te.inventory.getStackInSlot(1);
		ItemStack knife = te.inventory.getStackInSlot(2);
		
		int count = 0;
		
		if(!input.isEmpty())
		{
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(input, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, TransformType.NONE, false);
					
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			
			this.setTranslations(te, x, y, z, 0, input, model);
					
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
				
		if(!result.isEmpty())
		{
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(result, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, TransformType.NONE, false);
					
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			
			this.setTranslations(te, x, y, z, 1, result, model);
					
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
		
		if(!knife.isEmpty())
		{
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(knife, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, TransformType.NONE, false);
					
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			
			this.setTranslations(te, x, y, z, 2, knife, model);
					
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		} 
    }
	
	public void setTranslations(TileEntityChoppingBoard te, double x, double y, double z, int slot, ItemStack stackToRender, IBakedModel model)
	{
		EnumFacing facing = this.getWorld().getBlockState(te.getPos()).getValue(BlockChoppingBoard.FACING);
		
		int count = 0;
		
		if(facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH || facing == EnumFacing.WEST || facing == EnumFacing.EAST)
		{
			if(slot == 0)
			{
				double tx = facing == EnumFacing.NORTH ? x + 0.675 : x + 0.325;
				tx = (facing == EnumFacing.EAST || facing == EnumFacing.WEST) ? x + 0.5 : tx;
				
				double tz = facing == EnumFacing.WEST ? z + 0.325 : z + 0.675;
				tz = (facing == EnumFacing.EAST || facing == EnumFacing.WEST) ? tz : z + 0.5;
				
				float rotation = facing == EnumFacing.NORTH ? 20F : 200F;
				rotation = facing == EnumFacing.EAST ? 110F : rotation;
				rotation = facing == EnumFacing.WEST ? 290F : rotation;
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(tx, y + 0.06, tz);
				GlStateManager.scale(0.4D, 0.4D, 0.4D);
				GlStateManager.rotate(90F, 1, 0, 0);
				GlStateManager.rotate(rotation, 0, 0, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stackToRender, model);
				GlStateManager.popMatrix();
			}
			
			if(slot == 1)
			{
				double tx = facing == EnumFacing.NORTH ? x + 0.325 : x + 0.675;
				tx = (facing == EnumFacing.EAST || facing == EnumFacing.WEST) ? x + 0.5 : tx;
				
				double tz = facing == EnumFacing.WEST ? z + 0.675 : z + 0.325;
				tz = (facing == EnumFacing.EAST || facing == EnumFacing.WEST) ? tz : z + 0.5;
				
				if(count >= 0)
				{
					GlStateManager.pushMatrix();
					GlStateManager.translate(tx, y + 0.06, tz);
					GlStateManager.rotate(90F, 1, 0, 0);
							
					GlStateManager.scale(0.35D, 0.35D, 0.35D);
					Minecraft.getMinecraft().getRenderItem().renderItem(stackToRender, model);
					GlStateManager.popMatrix();
					count = 1;
				}
				
				if(count >= 1)
				{
					GlStateManager.pushMatrix();
					GlStateManager.translate(tx, y + 0.08, tz);
					GlStateManager.rotate(90F, 1, 0, 0);
							
					GlStateManager.rotate(45F, 0, 0, 1);
					GlStateManager.scale(0.35D, 0.35D, 0.35D);
					Minecraft.getMinecraft().getRenderItem().renderItem(stackToRender, model);
					GlStateManager.popMatrix();
				}
			}
			
			if(slot == 2)
			{
				double tx = facing == EnumFacing.NORTH ? x + 0.2 : x + 0.8;
				tx = facing == EnumFacing.WEST ? x + 0.7 : tx;
				tx = facing == EnumFacing.EAST ? x + 0.3 : tx;
				
				double tz = facing == EnumFacing.WEST ? z + 0.8 : z + 0.2;
				tz = facing == EnumFacing.NORTH ? z + 0.7 : tz;
				tz = facing == EnumFacing.SOUTH ? z + 0.3 : tz;
				
				float rotationZ = 260F;
				float rotationY = facing == EnumFacing.NORTH ? 0F : 180F;
				rotationY = (facing == EnumFacing.WEST || facing == EnumFacing.EAST) ? 90F : rotationY;
				float rotationY2 = facing == EnumFacing.EAST ? 180F : 0F;
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(tx, y + 0.25F, tz);
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				GlStateManager.rotate(rotationY2, 0, 1, 0);
				GlStateManager.rotate(rotationY, 0, 1, 0);
				GlStateManager.rotate(rotationZ, 0, 0, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stackToRender, model);
				GlStateManager.popMatrix();
			}
		}
	}
}