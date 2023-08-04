package com.tiviacz.pizzacraft.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.EmptyModelData;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class PizzaBagCurioRenderer implements ICurioRenderer
{
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        Block pizzaBag = ModBlocks.RED_PIZZA_BAG.get();
        LivingEntity livingEntity = slotContext.entity();
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        matrixStack.scale(0.75F, 0.75F, 0.75F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
        matrixStack.translate(-0.5D, -0.55D, 0.0D);

        ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);

        BlockRenderDispatcher blockDispatcher = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer renderer = blockDispatcher.getModelRenderer();
        renderer.tesselateBlock(
                livingEntity.getLevel(),
                blockDispatcher.getBlockModel(pizzaBag.defaultBlockState()),
                pizzaBag.defaultBlockState(),
                livingEntity.blockPosition().offset(0, 1, 0),
                matrixStack,
                renderTypeBuffer.getBuffer(RenderType.cutout()),
                false,
                livingEntity.getRandom(),
                pizzaBag.defaultBlockState().getSeed(livingEntity.blockPosition()),
                OverlayTexture.NO_OVERLAY,
                EmptyModelData.INSTANCE);

        matrixStack.popPose();
    }
}
