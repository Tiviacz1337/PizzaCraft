package com.tiviacz.pizzacraft.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientPizzaTooltipComponent implements ClientTooltipComponent
{
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private final NonNullList<ItemStack> items;

    public ClientPizzaTooltipComponent(PizzaTooltipComponent pizzaTooltip)
    {
        this.items = pizzaTooltip.getIngredients();
    }

    @Override
    public int getHeight() {
        if(!Utils.isShiftPressed()) return 0;
        return this.gridSizeY() * 20 + 2 + 4;
    }

    @Override
    public int getWidth(Font pFont)
    {
        if(!Utils.isShiftPressed()) return 0;
        return this.gridSizeX() * 18 + 2;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, PoseStack pPoseStack, ItemRenderer pItemRenderer)
    {
        if(!Utils.isShiftPressed()) return;

        int i = this.gridSizeX();
        int j = this.gridSizeY();
        int k = 0;

        for(int l = 0; l < j; ++l) {
            for(int i1 = 0; i1 < i; ++i1) {
                int j1 = pX + i1 * 18 + 1;
                int k1 = pY + l * 20 + 1;
                this.renderSlot(j1, k1, k++, pFont, pPoseStack, pItemRenderer);
            }
        }

        this.drawBorder(pX, pY, i, j, pPoseStack);
    }

    private void renderSlot(int pX, int pY, int pItemIndex, Font pFont, PoseStack poseStack, ItemRenderer itemRenderer)
    {
        if(pItemIndex >= this.items.size())
        {
            this.blit(poseStack, pX, pY, true ? Texture.BLOCKED_SLOT : Texture.SLOT);
        }
        else
        {
            ItemStack itemstack = this.items.get(pItemIndex);
            this.blit(poseStack, pX, pY, Texture.SLOT);
            itemRenderer.m_274407_(poseStack, itemstack, pX + 1, pY + 1, pItemIndex);
            itemRenderer.m_274412_(poseStack, pFont, itemstack, pX + 1, pY + 1);
        }
    }

    private void drawBorder(int pX, int pY, int pSlotWidth, int pSlotHeight, PoseStack poseStack) {
        this.blit(poseStack, pX, pY, ClientPizzaTooltipComponent.Texture.BORDER_CORNER_TOP);
        this.blit(poseStack, pX + pSlotWidth * 18 + 1, pY, ClientPizzaTooltipComponent.Texture.BORDER_CORNER_TOP);

        for(int i = 0; i < pSlotWidth; ++i) {
            this.blit(poseStack, pX + 1 + i * 18, pY, ClientPizzaTooltipComponent.Texture.BORDER_HORIZONTAL_TOP);
            this.blit(poseStack, pX + 1 + i * 18, pY + pSlotHeight * 20, ClientPizzaTooltipComponent.Texture.BORDER_HORIZONTAL_BOTTOM);
        }

        for(int j = 0; j < pSlotHeight; ++j) {
            this.blit(poseStack, pX, pY + j * 20 + 1, ClientPizzaTooltipComponent.Texture.BORDER_VERTICAL);
            this.blit(poseStack, pX + pSlotWidth * 18 + 1, pY + j * 20 + 1, ClientPizzaTooltipComponent.Texture.BORDER_VERTICAL);
        }

        this.blit(poseStack, pX, pY + pSlotHeight * 20, ClientPizzaTooltipComponent.Texture.BORDER_CORNER_BOTTOM);
        this.blit(poseStack, pX + pSlotWidth * 18 + 1, pY + pSlotHeight * 20, ClientPizzaTooltipComponent.Texture.BORDER_CORNER_BOTTOM);
    }

    private void blit(PoseStack poseStack, int pX, int pY, Texture pTexture)
    {
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        GuiComponent.blit(poseStack, pX, pY, 0, (float)pTexture.x, (float)pTexture.y, pTexture.w, pTexture.h, 128, 128);
    }

    private int gridSizeX() {
        int x = Math.max(2, (int)Math.ceil(Math.sqrt((double)this.items.size() + 1.0D)));
        return Math.min(x, 3);
    }

    private int gridSizeY()
    {
        int y = (int)Math.ceil(((double)this.items.size() + 1.0D) / (double)this.gridSizeX());
        return Math.min(y, 3);
    }

    @OnlyIn(Dist.CLIENT)
    static enum Texture {
        SLOT(0, 0, 18, 20),
        BLOCKED_SLOT(0, 40, 18, 20),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        private Texture(int pX, int pY, int pW, int pH) {
            this.x = pX;
            this.y = pY;
            this.w = pW;
            this.h = pH;
        }
    }
}