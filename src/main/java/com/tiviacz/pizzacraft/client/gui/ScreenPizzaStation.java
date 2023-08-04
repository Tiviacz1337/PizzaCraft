package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaStationBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaStationMenu;
import com.tiviacz.pizzacraft.network.ServerboundRenamePizzaPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenPizzaStation extends AbstractContainerScreen<PizzaStationMenu> implements MenuAccess<PizzaStationMenu>, ContainerListener
{
    public static final ResourceLocation SCREEN_PIZZA_STATION = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza_station.png");
    private static final ResourceLocation EMPTY_SLOT_SAUCE = new ResourceLocation(PizzaCraft.MODID, "item/empty_slot_sauce");
    private static final ResourceLocation EMPTY_SLOT_POTION = new ResourceLocation(PizzaCraft.MODID, "item/empty_slot_potion");
    private static final ResourceLocation EMPTY_SLOT_DOUGH = new ResourceLocation(PizzaCraft.MODID, "item/empty_slot_dough");
    public static final List<ResourceLocation> SAUCES = List.of(EMPTY_SLOT_SAUCE, EMPTY_SLOT_POTION);
    private static final List<ResourceLocation> DOUGH = List.of(EMPTY_SLOT_DOUGH);
    private final PizzaStationBlockEntity blockEntity;
    private EditBox name;

    public final CyclingSlotBackground sauceIcon = new CyclingSlotBackground(2);
    private final CyclingSlotBackground doughIcon = new CyclingSlotBackground(1);

    public ScreenPizzaStation(PizzaStationMenu menu, Inventory inv, Component titleIn)
    {
        super(menu, inv, titleIn);

        this.blockEntity = menu.blockEntity;

        this.leftPos = 0;
        this.topPos = 0;

        this.titleLabelX = 55;
        this.titleLabelY = 6;

        this.imageWidth = 176;
        this.imageHeight = 196;

        this.inventoryLabelY += 31;
    }

    @Override
    public void containerTick()
    {
        super.containerTick();
        this.name.tick();
        this.sauceIcon.m_266287_(SAUCES);
        this.doughIcon.m_266287_(DOUGH);
    }

    @Override
    public void init()
    {
        super.init();
        subInit();
        this.menu.addSlotListener(this);
    }

    protected void subInit()
    {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.name = new EditBox(this.font, i + 57, j + 24, 98, 12, blockEntity.getDisplayName());
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onNameChanged);
        this.name.setValue("");
        this.addWidget(this.name);
        this.m_264313_(this.name);
        this.name.setEditable(false);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
        this.renderFg(poseStack, mouseX, mouseY, partialTicks);
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

    public void renderFg(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.name.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight)
    {
        String s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.name.setValue(s);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers)
    {
        if(pKeyCode == 256)
        {
            this.minecraft.player.closeContainer();
        }

        return !this.name.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.name.canConsumeInput() ? super.keyPressed(pKeyCode, pScanCode, pModifiers) : true;
    }

    private void onNameChanged(String name)
    {
        if(!name.isEmpty())
        {
            String s = name;
            Slot slot = this.menu.getSlot(0);
            if(slot != null && slot.hasItem() && !slot.getItem().hasCustomHoverName() && name.equals(slot.getItem().getHoverName().getString()))
            {
                s = "";
            }
            this.menu.setItemName(s);
            PizzaCraft.NETWORK.sendToServer(new ServerboundRenamePizzaPacket(s));
        }
    }

    @Override
    public void removed()
    {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    @Override
    public void slotChanged(AbstractContainerMenu pContainerToSend, int pSlotInd, ItemStack pStack)
    {
        if(pSlotInd == 0)
        {
            this.name.setValue(pStack.isEmpty() ? "" : pStack.getHoverName().getString());
            this.name.setEditable(!pStack.isEmpty());
            this.setFocused(this.name);
        }
    }

    @Override
    public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue)
    {
        this.slotChanged(pContainerMenu, 0, pContainerMenu.getSlot(0).getItem());
    }
}
