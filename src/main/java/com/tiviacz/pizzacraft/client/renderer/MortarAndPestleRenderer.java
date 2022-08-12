package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.tiviacz.pizzacraft.blockentity.MortarAndPestleBlockEntity;
import com.tiviacz.pizzacraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class MortarAndPestleRenderer implements BlockEntityRenderer<MortarAndPestleBlockEntity>
{
    private final Random rand = new Random();

    public MortarAndPestleRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(MortarAndPestleBlockEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if(!tileEntityIn.isEmpty(tileEntityIn.getInventory()))
        {
            IItemHandler inventory = tileEntityIn.getInventory();
            rand.setSeed(Reference.CONSTANT_RENDERING_LONG);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            poseStack.pushPose();

            //Set proper translations
            setTranslations(poseStack);

            for(int i = 0; i < inventory.getSlots(); i++)
            {
                ItemStack stackToRender = inventory.getStackInSlot(i);

                if(!stackToRender.isEmpty())
                {
                    //boolean blockItem = itemRenderer.getItemModelWithOverrides(stackToRender, tileEntityIn.getWorld(), null).isGui3d();
                    itemRenderer.renderStatic(stackToRender, ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, 0);
                    poseStack.translate(0.0D, 0.0D, -0.065D);
                    poseStack.mulPose(Vector3f.ZP.rotationDegrees(360.0F * rand.nextFloat()));
                }
            }
            poseStack.popPose();
        }
    }

    public void setTranslations(PoseStack poseStack, boolean blockItem)
    {
        if(blockItem)
        {
            // Center block in the mortar
            poseStack.translate(0.5D, 0.27D, 0.5D);

            // Resize the block
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }
        else
        {
            // Center item in the mortar
            poseStack.translate(0.5D, 0.1D, 0.5D);

            // Rotate item flat in the basin. Use X and Y from now on
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

            // Resize the item
            poseStack.scale(0.375F, 0.375F, 0.375F);
        }
    }

    public static void setTranslations(PoseStack poseStack)
    {
        double tx = -0.5D;
        double tz = -0.5D;

        poseStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        poseStack.translate(tx, tz, -0.1D);
        poseStack.scale(0.375F, 0.375F, 0.375F);
    }
}
