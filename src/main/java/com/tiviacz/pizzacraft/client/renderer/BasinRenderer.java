package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.BasinBlockEntity;
import com.tiviacz.pizzacraft.blockentity.content.BasinContent;
import com.tiviacz.pizzacraft.blockentity.content.BasinContentType;
import com.tiviacz.pizzacraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class BasinRenderer implements BlockEntityRenderer<BasinBlockEntity>
{
   // public static final ResourceLocation CONTENT = new ResourceLocation(PizzaCraft.MODID, "content");
   // public static final ResourceLocation SAUCE = new ResourceLocation(PizzaCraft.MODID, "sauce");

    private static final ResourceLocation MILK_TEX = new ResourceLocation(PizzaCraft.MODID, "textures/block/milk.png");
    private static final ResourceLocation CHEESE_TEX = new ResourceLocation(PizzaCraft.MODID, "textures/block/cheese.png");
    private static final ResourceLocation OLIVE_OIL_TEX = new ResourceLocation(PizzaCraft.MODID, "textures/block/olive_oil.png");
    private final Random rand = new Random();

    protected ContentModel cheese; //= new ContentModel(64, 32); //#TODO NEW MILK TEXTURE
    protected ContentModel milk; //= new ContentModel(64, 32);
    protected ContentModel model; //= new ContentModel(64, 32);
    protected SauceModel sauce;

    public BasinRenderer(BlockEntityRendererProvider.Context context)
    {
      //  super(rendererDispatcherIn);
        cheese = new ContentModel(context);
        milk = new ContentModel(context);
        model = new ContentModel(context);
        //sauce = new SauceModel(context);
    }

    @Override
    public void render(BasinBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        rand.setSeed(Reference.CONSTANT_RENDERING_LONG);
        ResourceLocation tex = getTexture(blockEntity.getBasinContent());
        BasinContent basinContent = blockEntity.getBasinContent();
        ItemStack squashedStack = blockEntity.getInventory().getStackInSlot(0);

        //Basin contents rendered above block
       //if(tileEntityIn.getWorld() != null && renderDispatcher.renderInfo.getBlockAtCamera().getBlock() instanceof BasinBlock)
       // {
       //     renderContentsList(tileEntityIn.getTextComponentsForRenderer(), renderDispatcher.fontRenderer, matrixStackIn, bufferIn, combinedLightIn);
       // }

        poseStack.pushPose();

        if(basinContent.getContentType() == BasinContentType.SAUCE)
        {

            float[] rgb = basinContent.getSauceType().getRGB();
            sauce = new SauceModel(blockEntity.getAmount());
            sauce.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entitySolid(tex)), combinedLightIn, combinedOverlayIn, rgb[0], rgb[1], rgb[2], 1.0F);
            //SauceModel model = new SauceModel();
          //  SauceModel model = new SauceModel(64, 32, blockEntity.getAmount());
          //  float[] rgb = basinContent.getSauceType().getRGB();
          //  model.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entitySolid(tex)), combinedLightIn, combinedOverlayIn, rgb[0], rgb[1], rgb[2], 1.0F);
        }

        if(basinContent.getContentType() == BasinContentType.OIL)
        {
            sauce = new SauceModel(blockEntity.getAmount());
            model.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entitySolid(tex)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);

         //   SauceModel model = new SauceModel(64, 32, blockEntity.getAmount());
         //   model.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entitySolid(tex)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        else if(basinContent.getContentType() != BasinContentType.EMPTY)
        {
            if(basinContent.getContentType() == BasinContentType.FERMENTING_MILK || basinContent.getContentType() == BasinContentType.MILK)
            {
                //ContentModel cheese = new ContentModel(64, 32); //#TODO NEW MILK TEXTURE
                //ContentModel milk = new ContentModel(64, 32);

                float progressToFloatInv = 1.0F - (float)blockEntity.getFermentProgress() / blockEntity.getDefaultFermentTime();
                cheese.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entitySolid(CHEESE_TEX)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
                milk.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entityTranslucent(MILK_TEX)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, progressToFloatInv);
            }
            if(basinContent.getContentType() == BasinContentType.CHEESE)
            {
                //ContentModel model = new ContentModel(64, 32, basinContent == BasinContent.MILK ? RenderType::getEntityTranslucent : RenderType::getEntitySolid);
                //ContentModel model = new ContentModel(64, 32);
                model.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entitySolid(tex)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        poseStack.popPose();

        if(!squashedStack.isEmpty())
        {
            poseStack.pushPose();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            boolean blockItem = itemRenderer.getModel(squashedStack, blockEntity.getLevel(), null, 0).isGui3d(); //#TODO

            if(blockItem)
            {
                // Center block in the basin
                poseStack.translate(0.5D, 0.27D, 0.5D);

                // Resize the block
                poseStack.scale(0.75F, 0.75F, 0.75F);
            }
            else
            {
                // Center item in the basin
                poseStack.translate(0.5D, 0.1D, 0.5D);

                // Rotate item flat in the basin. Use X and Y from now on
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                // Resize the item
                poseStack.scale(0.6F, 0.6F, 0.6F);
            }

            if(!blockItem)
            {
                for(int i = 0; i < squashedStack.getCount(); i++)
                {
                    itemRenderer.m_269128_(squashedStack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, blockEntity.getLevel(), 0);
                    poseStack.translate(0.0D, 0.0D, -0.065D);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(360.0F * rand.nextFloat()));
                }
            }
            else //If not normal item, just render block, to avoid any glitches and complications, just don't use blockItems here :-)
            {
                itemRenderer.m_269128_(squashedStack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, blockEntity.getLevel(), 0);
            }
            poseStack.popPose();
        }
    }

 /*   private void renderContentsList(List<ITextComponent> components, FontRenderer fontrenderer, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
            matrixStack.pushPose();

            matrixStack.translate(0.5D, 2.0, 0.5D);
            Matrix4f matrix4f = matrixStack.last().pose();

            //float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            //int j = (int) (f1 * 255.0F) << 24;

            matrixStack.mulPose(renderer.camera.rotation());

            matrixStack.scale(-0.025F, -0.025F, -0.025F);

            for(ITextComponent text : components)
            {
                matrixStack.translate(0.0D, 15, 0.0D);
                float f2 = (float) (-fontrenderer.width(text) / 2);
                //fontrenderer.func_243247_a(text, f2, 0, 553648127, false, matrix4f, bufferIn, false, j, packedLightIn);
                fontrenderer.drawInBatch(text, f2, 0, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }
            matrixStack.popPose();

    } */

    public ResourceLocation getTexture(BasinContent content)
    {
        return switch (content.getContentType()) {
            case MILK, FERMENTING_MILK -> MILK_TEX;
            case CHEESE -> CHEESE_TEX;
            case SAUCE -> content.getSauceType().getTexture();
            case OIL -> OLIVE_OIL_TEX;
            default -> MissingTextureAtlasSprite.getLocation();
        };
    }

    public static class ContentModel extends Model
    {
        public static final ResourceLocation CONTENT = new ResourceLocation(PizzaCraft.MODID, "content");
        public static final ModelLayerLocation CONTENT_LAYER = new ModelLayerLocation(CONTENT, "main");
        private final ModelPart content;

        public ContentModel(BlockEntityRendererProvider.Context context)
        {
            super(RenderType::entitySolid);
            content = context.getModelSet().bakeLayer(CONTENT_LAYER).getChild("main");
            //this.texHeight = height;
            //this.texWidth = width;
           // this.content = new ModelRenderer(this);
            //this.content.addBox(2.0F, 1.0F, 2.0F, 12.0F, 6.0F, 12.0F);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {
            this.content.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        public static LayerDefinition createModelData()
        {
            MeshDefinition mesh = new MeshDefinition();
            mesh.getRoot().addOrReplaceChild("main",
                    CubeListBuilder.create().addBox(2.0F, 1.0F, 2.0F, 12.0F, 6.0F, 12.0F),
                    PartPose.ZERO
            );
            return LayerDefinition.create(mesh, 64, 32);
        }
    }

    public static class SauceModel extends Model
    {
        public static final ResourceLocation SAUCE = new ResourceLocation(PizzaCraft.MODID, "sauce");
        public static final ModelLayerLocation SAUCE_LAYER = new ModelLayerLocation(SAUCE, "main");
        private final ModelPart sauce;

        public SauceModel(float height)
        {
            super(RenderType::entitySolid);
            sauce = createModelDataWithHeight(height).bakeRoot().getChild("main");
            //context.getModelSet().bakeLayer(SAUCE_LAYER).getChild("main");
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {
            this.sauce.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        public static LayerDefinition createModelData()
        {
            return createModelDataWithHeight(6.0F);
         /*   MeshDefinition mesh = new MeshDefinition();
            mesh.getRoot().addOrReplaceChild("main",
                    CubeListBuilder.create().addBox(2.0F, 1.0F, 2.0F, 12.0F, 6.0F, 12.0F),
                    PartPose.ZERO
            );
            return LayerDefinition.create(mesh, 64, 32); */
        }

        public static LayerDefinition createModelDataWithHeight(float height)
        {
            MeshDefinition mesh = new MeshDefinition();
            mesh.getRoot().addOrReplaceChild("main",
                    CubeListBuilder.create().addBox(2.0F, 1.0F, 2.0F, 12.0F, height, 12.0F),
                    PartPose.ZERO
            );
            return LayerDefinition.create(mesh, 64, 32);
        }
    }

  /*  private static class SauceModel extends Model
    {
        ModelRenderer sauce;

        public SauceModel(int width, int height, float size)
        {
            super(RenderType::entitySolid);
            this.texHeight = height;
            this.texWidth = width;
            this.sauce = new ModelRenderer(this);
            this.sauce.addBox(2.0F, 1.0F, 2.0F, 12.0F, size, 12.0F);
        }

        @Override
        public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {
            this.sauce.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    } */ //TODO
}
