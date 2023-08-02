package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PizzaWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer
{
    private final Supplier<PizzaBlockEntity> pizza;

    public PizzaWithoutLevelRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet set, Supplier<PizzaBlockEntity> blockEntity)
    {
        super(dispatcher, set);
        this.pizza = blockEntity;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType context, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        BlockState pState = stack.getItem() == ModItems.RAW_PIZZA.get() ? ModBlocks.RAW_PIZZA.get().defaultBlockState() : ModBlocks.PIZZA.get().defaultBlockState();

        RenderShape rendershape = pState.getRenderShape();
        if(rendershape != RenderShape.INVISIBLE)
        {
            BakedModel bakedmodel = this.getBlockModel(pState);
            for (net.minecraft.client.renderer.RenderType rt : bakedmodel.getRenderTypes(pState, RandomSource.create(42), getModelData(stack)))
                renderModel(stack, poseStack.last(), buffer.getBuffer(net.minecraftforge.client.RenderTypeHelper.getEntityRenderType(rt, false)), pState, bakedmodel, combinedLight, combinedOverlay, getModelData(stack), rt);
        }
    }

    public void renderModel(ItemStack stack, PoseStack.Pose pPose, VertexConsumer pConsumer, @Nullable BlockState pState, BakedModel pModel, int pPackedLight, int pPackedOverlay, ModelData modelData, RenderType renderType) {
        RandomSource randomsource = RandomSource.create();
        long i = 42L;

        for(Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            renderQuadList(stack, pPose, pConsumer, pModel.getQuads(pState, direction, randomsource, modelData, renderType), pPackedLight, pPackedOverlay);
        }

        randomsource.setSeed(42L);
        renderQuadList(stack, pPose, pConsumer, pModel.getQuads(pState, (Direction)null, randomsource, modelData, renderType), pPackedLight, pPackedOverlay);
    }

    private static void renderQuadList(ItemStack stack, PoseStack.Pose pPose, VertexConsumer pConsumer, List<BakedQuad> pQuads, int pPackedLight, int pPackedOverlay)
    {
        for(BakedQuad bakedquad : pQuads)
        {
            float f;
            float f1;
            float f2;

            if(bakedquad.isTinted())
            {
                int i = Minecraft.getInstance().getItemColors().getColor(stack, bakedquad.getTintIndex());
                float r = (float)(i >> 16 & 255) / 255.0F;
                float g = (float)(i >> 8 & 255) / 255.0F;
                float b = (float)(i & 255) / 255.0F;

                f = Mth.clamp(r, 0.0F, 1.0F);
                f1 = Mth.clamp(g, 0.0F, 1.0F);
                f2 = Mth.clamp(b, 0.0F, 1.0F);
            }
            else
            {
                f = 1.0F;
                f1 = 1.0F;
                f2 = 1.0F;
            }

            pConsumer.putBulkData(pPose, bakedquad, f, f1, f2, pPackedLight, pPackedOverlay);
        }

    }

    public BakedModel getBlockModel(BlockState pState) {
        return Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(pState);
    }

    public ModelData getModelData(ItemStack stack)
    {
        return stack.hasTag() ? pizza.get().getItemStackModelData(stack) : ModelData.EMPTY;
    }
}