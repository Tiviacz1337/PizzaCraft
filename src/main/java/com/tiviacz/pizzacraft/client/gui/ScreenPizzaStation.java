package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaStationBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaStationMenu;
import com.tiviacz.pizzacraft.network.ServerboundRenamePizzaPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
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
        this.sauceIcon.tick(SAUCES);
        this.doughIcon.tick(DOUGH);
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
        this.setInitialFocus(this.name);
        this.name.setEditable(false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
        this.renderFg(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public void renderFg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.name.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(SCREEN_PIZZA_STATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
        this.sauceIcon.render(this.menu, guiGraphics, partialTicks, this.leftPos, this.topPos);
        this.doughIcon.render(this.menu, guiGraphics, partialTicks, this.leftPos, this.topPos);
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