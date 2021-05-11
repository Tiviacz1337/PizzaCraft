package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiviacz.pizzacraft.tileentity.MortarAndPestleTileEntity;
import com.tiviacz.pizzacraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class MortarAndPestleRenderer extends TileEntityRenderer<MortarAndPestleTileEntity>
{
    private final Random rand = new Random();

    public MortarAndPestleRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(MortarAndPestleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if(!tileEntityIn.isEmpty(tileEntityIn.getInventory()))
        {
            IItemHandler inventory = tileEntityIn.getInventory();
            rand.setSeed(Reference.CONSTANT_RENDERING_LONG);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            matrixStackIn.push();

            //Set proper translations
            setTranslations(matrixStackIn);

            for(int i = 0; i < inventory.getSlots(); i++)
            {
                ItemStack stackToRender = inventory.getStackInSlot(i);

                if(!stackToRender.isEmpty())
                {
                    //boolean blockItem = itemRenderer.getItemModelWithOverrides(stackToRender, tileEntityIn.getWorld(), null).isGui3d();
                    itemRenderer.renderItem(stackToRender, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
                    matrixStackIn.translate(0.0D, 0.0D, -0.065D);
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(360.0F * rand.nextFloat()));
                }
            }
            matrixStackIn.pop();
        }
    }

    public void setTranslations(MatrixStack matrixStackIn, boolean blockItem)
    {
        if(blockItem)
        {
            // Center block in the mortar
            matrixStackIn.translate(0.5D, 0.27D, 0.5D);

            // Resize the block
            matrixStackIn.scale(0.4F, 0.4F, 0.4F);
        }
        else
        {
            // Center item in the mortar
            matrixStackIn.translate(0.5D, 0.1D, 0.5D);

            // Rotate item flat in the basin. Use X and Y from now on
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));

            // Resize the item
            matrixStackIn.scale(0.375F, 0.375F, 0.375F);
        }
    }

    public static void setTranslations(MatrixStack matrixStackIn)
    {
        double tx = -0.5D;
        double tz = -0.5D;

        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90.0F));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
        matrixStackIn.translate(tx, tz, -0.1D);
        matrixStackIn.scale(0.375F, 0.375F, 0.375F);
    }
}
