package com.tiviacz.pizzacraft.client.gui;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.common.TasteHandler;
import com.tiviacz.pizzacraft.container.PizzaMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenPizza extends AbstractContainerScreen<PizzaMenu> implements MenuAccess<PizzaMenu>
{
    public static final ResourceLocation SCREEN_PIZZA = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza.png");
    private final ScreenImage HUNGER_INFO = new ScreenImage(126, 40, 13, 13);
    private final PizzaBlockEntity blockEntity;

    private final CyclingSlotBackground sauceIcon = new CyclingSlotBackground(10);

    public ScreenPizza(PizzaMenu menu, Inventory inv, Component titleIn)
    {
        super(menu, inv, titleIn);

        this.blockEntity = menu.blockEntity;

        this.leftPos = 0;
        this.topPos = 0;

        this.imageWidth = 176;
        this.imageHeight = 158;

        this.inventoryLabelY -= 6;
    }

    @Override
    public void containerTick()
    {
        super.containerTick();
        this.sauceIcon.tick(ScreenPizzaStation.SAUCES);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
        guiGraphics.drawString(this.font, this.blockEntity.getDisplayName(), this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public List<Component> getTooltip()
    {
        List<Component> components = new ArrayList<>();

        components.add(Component.translatable("information.pizzacraft.taste", new TasteHandler(blockEntity.getUniqueness(), 9).getTaste().toString()));
        components.add(Component.translatable("information.pizzacraft.hunger", blockEntity.getHunger() / 7, blockEntity.getHunger()).withStyle(ChatFormatting.BLUE));
        components.add(Component.translatable("information.pizzacraft.saturation", decimalFormat.format(blockEntity.getSaturation())).withStyle(ChatFormatting.BLUE));

        if(!blockEntity.getInventory().getStackInSlot(9).isEmpty())
        {
            PotionUtils.addPotionTooltip(blockEntity.getInventory().getStackInSlot(9), components, 1.0F);
        }

        return components;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        if(this.HUNGER_INFO.inButton(this, mouseX, mouseY))
        {
            guiGraphics.renderComponentTooltip(this.font, getTooltip(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(SCREEN_PIZZA, x, y, 0, 0, this.imageWidth, this.imageHeight);

        this.sauceIcon.render(this.menu, guiGraphics, partialTicks, this.leftPos, this.topPos);

       // HUNGER_INFO.draw(guiGraphics, SCREEN_PIZZA, this, 127, 30);
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

        //public void draw(GuiGraphics guiGraphics, ResourceLocation texture, ScreenPizza screen, int U, int V)
        //{
            //guiGraphics.blit(texture, screen.getGuiLeft() + X, screen.getGuiTop() + Y, U, V, W, H);
        //}

        public boolean inButton(ScreenPizza screen, int mouseX, int mouseY)
        {
            mouseX -= screen.getGuiLeft();
            mouseY -= screen.getGuiTop();
            return X <= mouseX && mouseX <= X + W && Y <= mouseY && mouseY <= Y + H;
        }
    }
}
