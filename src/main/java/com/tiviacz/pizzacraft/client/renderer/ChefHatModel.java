package com.tiviacz.pizzacraft.client.renderer;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

public class ChefHatModel extends HumanoidModel
{
    public static final ModelLayerLocation CHEF_HAT = new ModelLayerLocation(new ResourceLocation(PizzaCraft.MODID, "chef_hat"), "main");

    public ChefHatModel(ModelPart root)
    {
        super(root);
    }

    public static LayerDefinition createModelData()
    {
        CubeDeformation cube = new CubeDeformation(0.25F);
        MeshDefinition humanoidMesh = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition part = humanoidMesh.getRoot().getChild("hat");

        part.addOrReplaceChild("box1", CubeListBuilder.create().texOffs(0, 51)
                        .addBox(-4.0F, -33.0F, -4.0F, 8, 4, 8, cube),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        part.addOrReplaceChild("box2", CubeListBuilder.create().texOffs(33, 49)
                        .addBox(-2.25F, -38.0F, -5.0F, 10, 5, 10, cube),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        return LayerDefinition.create(humanoidMesh, 128, 64);
    }
}
