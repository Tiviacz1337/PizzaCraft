package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tiviacz.pizzacraft.blockentity.ChoppingBoardBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;

public class ChoppingBoardRenderer implements BlockEntityRenderer<ChoppingBoardBlockEntity>
{
    public ChoppingBoardRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ChoppingBoardBlockEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Direction direction = tileEntityIn.getFacing().getOpposite();
        ItemStack stack = tileEntityIn.getStoredStack();

        if(!stack.isEmpty())
        {
            poseStack.pushPose();

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            boolean blockItem = itemRenderer.getModel(stack, tileEntityIn.getLevel(), null, 0).isGui3d();

            if(tileEntityIn.isItemCarvingBoard())
            {
                // Move carved tool a bit to right side depending on direction
                double x = direction == Direction.NORTH ? 0.6D : direction == Direction.SOUTH ? 0.4D : 0.5D;
                double z = direction == Direction.EAST ? 0.6D :  direction == Direction.WEST ? 0.4D : 0.5D;

                // Center item above the cutting board // Put hoes and pickaxes little deeper
                poseStack.translate(x, stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof HoeItem ? 0.225D : 0.25D, z);

                // Rotate item to face the cutting board's front side
                float f = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));

                // Rotate item to be carved on the surface, A little less so for hoes and pickaxes.
                poseStack.mulPose(Axis.ZP.rotationDegrees(stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof HoeItem ? 225.0F : 180.0F));

                // Resize the item
                poseStack.scale(0.6F, 0.6F, 0.6F);
            }
            else if(blockItem)
            {
                // Center block above the cutting board
                poseStack.translate(0.5D, 0.25D, 0.5D);

                // Rotate block to face the cutting board's front side
                float f = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));

                // Resize the block
                poseStack.scale(0.75F, 0.75F, 0.75F);
            }
            else
            {
                // Center item above the cutting board
                poseStack.translate(0.5D, 0.075D, 0.5D);

                // Rotate item to face the cutting board's front side
                float f = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));

                // Rotate item flat on the cutting board. Use X and Y from now on
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                // Resize the item
                poseStack.scale(0.6F, 0.6F, 0.6F);
            }

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, tileEntityIn.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
