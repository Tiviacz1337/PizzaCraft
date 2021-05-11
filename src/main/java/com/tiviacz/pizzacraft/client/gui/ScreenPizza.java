package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.container.PizzaContainer;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenPizza extends ContainerScreen<PizzaContainer>
{
    public static final ResourceLocation SCREEN_PIZZA = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza.png");
    private final ScreenImage PIZZA_IMAGE = new ScreenImage(61, 9, 54, 54);
    private final ScreenImage PIZZA_PATTERN = new ScreenImage(61, 9, 54, 54);
    private final RedCrossImage RED_CROSS = new RedCrossImage(1, 159, 14, 14);
    private final PizzaTileEntity tileEntity;

    public ScreenPizza(PizzaContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);

        this.tileEntity = screenContainer.tileEntity;

        this.guiLeft = 0;
        this.guiTop = 0;

        this.xSize = 176;
        this.ySize = 158;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        //this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(SCREEN_PIZZA);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);

        PIZZA_IMAGE.draw(matrixStack, this, tileEntity.isRaw() ? 1 : 112, 159);
        PIZZA_PATTERN.draw(matrixStack, this, tileEntity.isRaw() ? 56 : 167, 158);

     //   matrixStack.push();
     //   float rotation = 360F / partialTicks;
     //   Vector3f.YP.rotationDegrees(rotation);
    //    this.renderPizzaPreview(matrixStack, x + 35, y + 25);
    //    matrixStack.pop();

     /*   if(tileEntity.getInventory().getStackInSlot(0).isEmpty())
        {
            RED_CROSS.draw(matrixStack, this, 32, 29);

            for(int i = 0; i < 3; i++)
            {
                RED_CROSS.draw(matrixStack, this, 68 + i * 18, 11);
                RED_CROSS.draw(matrixStack, this, i == 0 ? 68 : 104, 29);
                RED_CROSS.draw(matrixStack, this, 68 + i * 18, 47);
            }
        } */

   /*     if(tileEntity.getInventory().getStackInSlot(0).isEmpty())
        {
            RED_CROSS.draw(matrixStack, this, 32, 29);
        }

        for(int i = 0; i < 3; i++)
        {
            if(tileEntity.getInventory().getStackInSlot(i).isEmpty())
            {
                RED_CROSS.draw(matrixStack, this, 68 + i * 18, 11);
            }
            if(i < 2 && tileEntity.getInventory().getStackInSlot(i + 3).isEmpty())
            {
                RED_CROSS.draw(matrixStack, this, i == 0 ? 68 : 104, 29);
            }
            if(tileEntity.getInventory().getStackInSlot(i + 5).isEmpty())
            {
                RED_CROSS.draw(matrixStack, this, 68 + i * 18, 47);
            }
        } */
    }

 /*   public void renderPizzaPreview(MatrixStack matrixStack, int x, int y)
    {
        ItemStack stack = new ItemStack(tileEntity.getBlockState().getBlock().asItem());

        matrixStack.push();
        Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, x, y);
        matrixStack.pop();
    } */

    public void blit(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, boolean b)
    {
        blit(matrixStack, x, y, 0, (float)uOffset, (float)vOffset, uWidth, vHeight, 256, 256);
    }

    public static void blit(MatrixStack matrixStack, int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureHeight, int textureWidth) {
        innerBlit(matrixStack, x, x + uWidth, y, y + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    private static void innerBlit(MatrixStack matrixStack, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(matrixStack.getLast().getMatrix(), x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
    }

    private static void innerBlit(Matrix4f matrix, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV)
    {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferbuilder.pos(matrix, (float)x1, (float)y2, (float)blitOffset).color(1.0F, 1.0F, 1.0F, 0.2F).tex(minU, maxV).endVertex();
        bufferbuilder.pos(matrix, (float)x2, (float)y2, (float)blitOffset).color(1.0F, 1.0F, 1.0F, 0.2F).tex(maxU, maxV).endVertex();
        bufferbuilder.pos(matrix, (float)x2, (float)y1, (float)blitOffset).color(1.0F, 1.0F, 1.0F, 0.2F).tex(maxU, minV).endVertex();
        bufferbuilder.pos(matrix, (float)x1, (float)y1, (float)blitOffset).color(1.0F, 1.0F, 1.0F, 0.2F).tex(minU, minV).endVertex();
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }

    public static class ScreenImage
    {
        private int X;
        private int Y;
        private int W;
        private int H;

        public ScreenImage(int U, int V, int W, int H)
        {
            this.X = U;
            this.Y = V;
            this.W = W;
            this.H = H;
        }

        public void draw(MatrixStack matrixStack, ScreenPizza screen, int U, int V)
        {
            screen.blit(matrixStack, screen.getGuiLeft() + X, screen.getGuiTop() + Y, U, V, W, H);
        }
    }

    public static class RedCrossImage
    {
        private int U;
        private int V;
        private int W;
        private int H;

        public RedCrossImage(int U, int V, int W, int H)
        {
            this.U = U;
            this.V = V;
            this.W = W;
            this.H = H;
        }

        public void draw(MatrixStack matrixStack, ScreenPizza screen, int X, int Y)
        {
            screen.blit(matrixStack, screen.getGuiLeft() + X, screen.getGuiTop() + Y, U, V, W, H, true);
        }
    }
}
