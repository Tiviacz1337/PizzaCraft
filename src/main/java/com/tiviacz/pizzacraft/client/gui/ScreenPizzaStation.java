package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaStationBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaStationMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenPizzaStation extends AbstractContainerScreen<PizzaStationMenu> implements MenuAccess<PizzaStationMenu>
{
    public static final ResourceLocation SCREEN_PIZZA_STATION = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza_station.png");
    private static final ResourceLocation EMPTY_SLOT_SAUCE = new ResourceLocation(PizzaCraft.MODID, "item/empty_slot_sauce");
    private static final ResourceLocation EMPTY_SLOT_POTION = new ResourceLocation(PizzaCraft.MODID, "item/empty_slot_potion");
    private static final ResourceLocation EMPTY_SLOT_DOUGH = new ResourceLocation(PizzaCraft.MODID, "item/empty_slot_dough");
    public static final List<ResourceLocation> SAUCES = List.of(EMPTY_SLOT_SAUCE, EMPTY_SLOT_POTION);
    public static final List<ResourceLocation> DOUGH = List.of(EMPTY_SLOT_DOUGH);
    private final PizzaStationBlockEntity blockEntity;

    public final CyclingSlotBackground sauceIcon = new CyclingSlotBackground(2);
    private final CyclingSlotBackground doughIcon = new CyclingSlotBackground(1);

    public ScreenPizzaStation(PizzaStationMenu menu, Inventory inv, Component titleIn)
    {
        super(menu, inv, titleIn);

        this.blockEntity = menu.blockEntity;

        this.leftPos = 0;
        this.topPos = 0;

        this.imageWidth = 176;
        this.imageHeight = 166;

        this.inventoryLabelY += 1;
    }

    @Override
    public void containerTick()
    {
        super.containerTick();
        this.sauceIcon.m_266287_(SAUCES);
        this.doughIcon.m_266287_(DOUGH);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        this.font.draw(poseStack, this.blockEntity.getDisplayName(), this.titleLabelX, this.titleLabelY, 4210752);
        this.font.draw(poseStack, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752);
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
        RenderSystem.setShaderTexture(0, SCREEN_PIZZA_STATION);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        this.sauceIcon.m_266270_(this.menu, poseStack, partialTicks, this.leftPos, this.topPos);
        this.doughIcon.m_266270_(this.menu, poseStack, partialTicks, this.leftPos, this.topPos);
    }
}
