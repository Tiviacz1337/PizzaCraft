package com.tiviacz.pizzacraft.client.gui;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaBagBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaBagMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
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
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
        guiGraphics.drawString(this.font, this.blockEntity.getDisplayName(), 8, 6, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 92), 4210752, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(SCREEN_PIZZA_BAG, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}