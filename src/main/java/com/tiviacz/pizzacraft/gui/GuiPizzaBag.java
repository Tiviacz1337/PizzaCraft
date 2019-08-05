package com.tiviacz.pizzacraft.gui;

import com.tiviacz.pizzacraft.gui.container.ContainerPizzaBag;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizzaBag;
import com.tiviacz.pizzacraft.util.TextUtils;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPizzaBag extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURE = TextUtils.setResourceLocation("textures/gui/pizza_bag.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityPizzaBag tile;

    public GuiPizzaBag(EntityPlayer player, TileEntityPizzaBag tile)
    {
        super(new ContainerPizzaBag(player, tile));
        this.tile = tile;
        this.playerInventory = player.inventory;
        
        this.ySize = 133;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(I18n.format(this.tile.getName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 4, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}