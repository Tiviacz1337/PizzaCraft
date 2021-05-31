package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class PizzaDeliveryCapModel extends BipedModel
{
    public final ModelRenderer box1;
    public final ModelRenderer box2;

    public PizzaDeliveryCapModel()
    {
        super(1.0F, 0.0F, 128, 64);

        box1 = new ModelRenderer(this, 0, 53);
        box1.setRotationPoint(0.0F, 24.0F, 0.0F);
        box1.addBox(-4.0F, -32.0F, -4.0F, 8, 3, 8, 0.5F);

        box2 = new ModelRenderer(this, 33, 60);
        box2.setRotationPoint(0.0F, 24.0F, 0.0F);
        box2.addBox(-4.0F, -30.0F, -7.0F, 8, 1, 3, 0.5F);

        this.bipedHeadwear.addChild(box1);
        this.bipedHeadwear.addChild(box2);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.box1.copyModelAngles(this.bipedHeadwear);
        this.box2.copyModelAngles(this.bipedHeadwear);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}