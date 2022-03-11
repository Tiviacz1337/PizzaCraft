package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.container.PizzaBagContainer;
import com.tiviacz.pizzacraft.tileentity.PizzaBagTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenPizzaBag extends ContainerScreen<PizzaBagContainer>
{
    public static final ResourceLocation SCREEN_PIZZA_BAG = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza_bag.png");
    private final PizzaBagTileEntity tileEntity;

    public ScreenPizzaBag(PizzaBagContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.tileEntity = screenContainer.tileEntity;

        this.leftPos = 0;
        this.topPos = 0;

        this.imageHeight = 133;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.draw(matrixStack, this.tileEntity.getDisplayName(), (float)8, (float)6, 4210752);
        this.font.draw(matrixStack, this.inventory.getDisplayName(), (float)8, (float)(this.imageHeight - 92), 4210752);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(SCREEN_PIZZA_BAG);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}

