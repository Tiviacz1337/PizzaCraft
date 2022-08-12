package com.tiviacz.pizzacraft.client.renderer;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

public class PizzaDeliveryCapModel extends HumanoidModel
{
    public static final ModelLayerLocation CAP = new ModelLayerLocation(new ResourceLocation(PizzaCraft.MODID, "cap"), "main");

    public PizzaDeliveryCapModel(ModelPart root)
    {
        super(root);
    }

    public static LayerDefinition createModelData()
    {
        CubeDeformation cube = new CubeDeformation(0.5F);
        MeshDefinition humanoidMesh = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition part = humanoidMesh.getRoot().getChild("hat");

        part.addOrReplaceChild("box1", CubeListBuilder.create().texOffs(0, 53)
                .addBox(-4.0F, -32.0F, -4.0F, 8, 3, 8, cube),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        part.addOrReplaceChild("box2", CubeListBuilder.create().texOffs(33, 60)
                .addBox(-4.0F, -30.0F, -7.0F, 8, 1, 3, cube),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(humanoidMesh, 128, 64);
    }
}