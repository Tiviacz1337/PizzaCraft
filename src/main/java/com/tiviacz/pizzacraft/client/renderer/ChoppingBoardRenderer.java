package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiviacz.pizzacraft.blocks.ChoppingBoardBlock;
import com.tiviacz.pizzacraft.tileentity.ChoppingBoardTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ChoppingBoardRenderer extends TileEntityRenderer<ChoppingBoardTileEntity>
{
    public ChoppingBoardRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ChoppingBoardTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Direction direction = tileEntityIn.getFacing().getOpposite();
        ItemStack stack = tileEntityIn.getStoredStack();

        if(!stack.isEmpty())
        {
            matrixStackIn.push();

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            boolean blockItem = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null).isGui3d();

            if(tileEntityIn.isItemCarvingBoard())
            {
                // Move carved tool a bit to right side depending on direction
                double x = direction == Direction.NORTH ? 0.6D : direction == Direction.SOUTH ? 0.4D : 0.5D;
                double z = direction == Direction.EAST ? 0.6D :  direction == Direction.WEST ? 0.4D : 0.5D;

                // Center item above the cutting board // Put hoes and pickaxes little deeper
                matrixStackIn.translate(x, stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof HoeItem ? 0.225D : 0.25D, z);

                // Rotate item to face the cutting board's front side
                float f = -direction.getHorizontalAngle();
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));

                // Rotate item to be carved on the surface, A little less so for hoes and pickaxes.
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof HoeItem ? 225.0F : 180.0F));

                // Resize the item
                matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            }
            else if(blockItem)
            {
                // Center block above the cutting board
                matrixStackIn.translate(0.5D, 0.25D, 0.5D);

                // Rotate block to face the cutting board's front side
                float f = -direction.getHorizontalAngle();
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));

                // Resize the block
                matrixStackIn.scale(0.75F, 0.75F, 0.75F);
            }
            else
            {
                // Center item above the cutting board
                matrixStackIn.translate(0.5D, 0.075D, 0.5D);

                // Rotate item to face the cutting board's front side
                float f = -direction.getHorizontalAngle();
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));

                // Rotate item flat on the cutting board. Use X and Y from now on
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));

                // Resize the item
                matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            }

            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}
