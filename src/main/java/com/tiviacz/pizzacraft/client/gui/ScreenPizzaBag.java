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

        this.guiLeft = 0;
        this.guiTop = 0;

        this.ySize = 133;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.func_243248_b(matrixStack, this.tileEntity.getDisplayName(), (float)8, (float)6, 4210752);
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float)8, (float)(this.ySize - 92), 4210752);
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
        this.minecraft.getTextureManager().bindTexture(SCREEN_PIZZA_BAG);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
    }
}

