package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaBagBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaBagMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenPizzaBag extends AbstractContainerScreen<PizzaBagMenu> implements MenuAccess<PizzaBagMenu>
{
    public static final ResourceLocation SCREEN_PIZZA_BAG = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza_bag.png");
    private final PizzaBagBlockEntity blockEntity;

    public ScreenPizzaBag(PizzaBagMenu menu, Inventory inv, Component titleIn) {
        super(menu, inv, titleIn);

        this.blockEntity = menu.blockEntity;
        this.leftPos = 0;
        this.topPos = 0;

        this.imageHeight = 133;
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        this.font.draw(poseStack, this.blockEntity.getDisplayName(), 8, 6, 4210752);
        this.font.draw(poseStack, this.playerInventoryTitle, 8, (this.imageHeight - 92), 4210752);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SCREEN_PIZZA_BAG);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}