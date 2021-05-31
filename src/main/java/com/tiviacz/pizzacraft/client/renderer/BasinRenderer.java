package com.tiviacz.pizzacraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.tileentity.BasinContent;
import com.tiviacz.pizzacraft.tileentity.BasinContentType;
import com.tiviacz.pizzacraft.tileentity.BasinTileEntity;
import com.tiviacz.pizzacraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.Random;

public class BasinRenderer extends TileEntityRenderer<BasinTileEntity>
{
    private static final ResourceLocation MILK_TEX = new ResourceLocation(PizzaCraft.MODID, "textures/block/milk.png");
    private static final ResourceLocation CHEESE_TEX = new ResourceLocation(PizzaCraft.MODID, "textures/block/cheese.png");
    private static final ResourceLocation OLIVE_OIL_TEX = new ResourceLocation(PizzaCraft.MODID, "textures/block/olive_oil.png");
    private final Random rand = new Random();

    public BasinRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BasinTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        rand.setSeed(Reference.CONSTANT_RENDERING_LONG);
        ResourceLocation tex = getTexture(tileEntityIn.getBasinContent());
        BasinContent basinContent = tileEntityIn.getBasinContent();
        ItemStack squashedStack = tileEntityIn.getInventory().getStackInSlot(0);

        //Basin contents rendered above block
       //if(tileEntityIn.getWorld() != null && renderDispatcher.renderInfo.getBlockAtCamera().getBlock() instanceof BasinBlock)
       // {
       //     renderContentsList(tileEntityIn.getTextComponentsForRenderer(), renderDispatcher.fontRenderer, matrixStackIn, bufferIn, combinedLightIn);
       // }

        matrixStackIn.push();

        if(basinContent.getContentType() == BasinContentType.SAUCE)
        {
            SauceModel model = new SauceModel(64, 32, tileEntityIn.getAmount());
            float[] rgb = basinContent.getSauceType().getRGB();
            model.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(tex)), combinedLightIn, combinedOverlayIn, rgb[0], rgb[1], rgb[2], 1.0F);
        }

        if(basinContent.getContentType() == BasinContentType.OIL)
        {
            SauceModel model = new SauceModel(64, 32, tileEntityIn.getAmount());
            model.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(tex)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        else if(basinContent.getContentType() != BasinContentType.EMPTY)
        {
            if(basinContent.getContentType() == BasinContentType.FERMENTING_MILK || basinContent.getContentType() == BasinContentType.MILK)
            {
                ContentModel cheese = new ContentModel(64, 32); //#TODO NEW MILK TEXTURE
                ContentModel milk = new ContentModel(64, 32);

                float progressToFloatInv = 1.0F - (float)tileEntityIn.getFermentProgress() / tileEntityIn.getDefaultFermentTime();
                cheese.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(CHEESE_TEX)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
                milk.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityTranslucent(MILK_TEX)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, progressToFloatInv);
            }
            if(basinContent.getContentType() == BasinContentType.CHEESE)
            {
                //ContentModel model = new ContentModel(64, 32, basinContent == BasinContent.MILK ? RenderType::getEntityTranslucent : RenderType::getEntitySolid);
                ContentModel model = new ContentModel(64, 32);
                model.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(tex)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        matrixStackIn.pop();

        if(!squashedStack.isEmpty())
        {
            matrixStackIn.push();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            boolean blockItem = itemRenderer.getItemModelWithOverrides(squashedStack, tileEntityIn.getWorld(), null).isGui3d();

            if(blockItem)
            {
                // Center block in the basin
                matrixStackIn.translate(0.5D, 0.27D, 0.5D);

                // Resize the block
                matrixStackIn.scale(0.75F, 0.75F, 0.75F);
            }
            else
            {
                // Center item in the basin
                matrixStackIn.translate(0.5D, 0.1D, 0.5D);

                // Rotate item flat in the basin. Use X and Y from now on
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));

                // Resize the item
                matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            }

            if(!blockItem)
            {
                for(int i = 0; i < squashedStack.getCount(); i++)
                {
                    itemRenderer.renderItem(squashedStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
                    matrixStackIn.translate(0.0D, 0.0D, -0.065D);
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(360.0F * rand.nextFloat()));
                }
            }
            else //If not normal item, just render block, to avoid any glitches and complications, just don't use blockItems here :-)
            {
                itemRenderer.renderItem(squashedStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            }
            matrixStackIn.pop();
        }
    }

    private void renderContentsList(List<ITextComponent> components, FontRenderer fontrenderer, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
            matrixStack.push();

            matrixStack.translate(0.5D, 2.0, 0.5D);
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();

            //float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            //int j = (int) (f1 * 255.0F) << 24;

            matrixStack.rotate(renderDispatcher.renderInfo.getRotation());

            matrixStack.scale(-0.025F, -0.025F, -0.025F);

            for(ITextComponent text : components)
            {
                matrixStack.translate(0.0D, 15, 0.0D);
                float f2 = (float) (-fontrenderer.getStringPropertyWidth(text) / 2);
                //fontrenderer.func_243247_a(text, f2, 0, 553648127, false, matrix4f, bufferIn, false, j, packedLightIn);
                fontrenderer.func_243247_a(text, f2, 0, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }
            matrixStack.pop();

    }

    public ResourceLocation getTexture(BasinContent content)
    {
        switch(content.getContentType())
        {
            case MILK:
            case FERMENTING_MILK:
                return MILK_TEX;

            case CHEESE:
                return CHEESE_TEX;

            case SAUCE:
                return content.getSauceType().getTexture();

            case OIL:
                return OLIVE_OIL_TEX;

            default:
                return MissingTextureSprite.getLocation();
        }
    }

    private static class ContentModel extends Model
    {
        ModelRenderer content;

        public ContentModel(int width, int height)
        {
            super(RenderType::getEntitySolid);
            this.textureHeight = height;
            this.textureWidth = width;
            this.content = new ModelRenderer(this);
            this.content.addBox(2.0F, 1.0F, 2.0F, 12.0F, 6.0F, 12.0F);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {
            this.content.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    private static class SauceModel extends Model
    {
        ModelRenderer sauce;

        public SauceModel(int width, int height, float size)
        {
            super(RenderType::getEntitySolid);
            this.textureHeight = height;
            this.textureWidth = width;
            this.sauce = new ModelRenderer(this);
            this.sauce.addBox(2.0F, 1.0F, 2.0F, 12.0F, size, 12.0F);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {
            this.sauce.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
}
