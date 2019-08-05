package com.tiviacz.pizzacraft.client;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelChefHat extends ModelBiped 
{
	private final ModelRenderer box1;
	private final ModelRenderer box2;

	public ModelChefHat() 
	{
		textureWidth = 128;
		textureHeight = 64;
		
		box1 = new ModelRenderer(this, 0, 51);
		box1.setRotationPoint(0.0F, 24.0F, 0.0F);
		box1.addBox(-4.0F, -33.0F, -4.0F, 8, 4, 8, 0.25F);

		box2 = new ModelRenderer(this, 33, 49);
		box2.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(box2, 0.0F, 0.0F, -0.0873F);
		box2.addBox(-2.25F, -38.0F, -5.0F, 10, 5, 10, 0.25F);
		
		this.bipedHeadwear.addChild(box1);
		this.bipedHeadwear.addChild(box2);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		GlStateManager.pushMatrix();

		if(this.isChild)
        {
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedHeadwear.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
        }
        else
        {
            if(entityIn.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedHeadwear.render(scale);
        }

        GlStateManager.popMatrix();
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) 
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}