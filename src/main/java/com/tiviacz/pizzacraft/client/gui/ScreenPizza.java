package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.container.PizzaContainer;
import com.tiviacz.pizzacraft.tileentity.PizzaTileEntity;
import com.tiviacz.pizzacraft.util.FoodUtils;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenPizza extends ContainerScreen<PizzaContainer>
{
    public static final ResourceLocation SCREEN_PIZZA = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza.png");
    private final ScreenImage PIZZA_IMAGE = new ScreenImage(61, 9, 54, 54);
    private final ScreenImage PIZZA_PATTERN = new ScreenImage(61, 9, 54, 54);
    private final ScreenImage HUNGER_INFO = new ScreenImage(127, 30, 13, 13);
    private final PizzaTileEntity tileEntity;

    public ScreenPizza(PizzaContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);

        this.tileEntity = screenContainer.tileEntity;

        this.leftPos = 0;
        this.topPos = 0;

        this.imageWidth = 176;
        this.imageHeight = 158;

        this.inventoryLabelY -= 6;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.draw(matrixStack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    public List<ITextComponent> getTooltip()
    {
        List<ITextComponent> components = new ArrayList<>();
        components.add(new TranslationTextComponent("information.pizzacraft.hunger", FoodUtils.getHungerForSlice(tileEntity.getRefillmentValues().getFirst(), false), ((tileEntity.getRefillmentValues().getFirst() % 7 != 0) ? " (+" + tileEntity.getRefillmentValues().getFirst() % 7 + ")" : ""), tileEntity.getRefillmentValues().getFirst()).withStyle(TextFormatting.BLUE));
        components.add(new TranslationTextComponent("information.pizzacraft.saturation", (float)(Math.round(tileEntity.getRefillmentValues().getSecond() / 7 * 100.0) / 100.0), (float)(Math.round(tileEntity.getRefillmentValues().getSecond() * 100.0) / 100.0)).withStyle(TextFormatting.BLUE));

        if(!tileEntity.getEffects().isEmpty())
        {
            components.add(new TranslationTextComponent("information.pizzacraft.effects").withStyle(TextFormatting.GOLD));

            for(Pair<EffectInstance, Float> pair : tileEntity.getEffects())
            {
                components.add(new TranslationTextComponent(pair.getFirst().getDescriptionId()).withStyle(pair.getFirst().getEffect().getCategory().getTooltipFormatting()));
            }
        }

        return components;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);

        if(this.HUNGER_INFO.inButton(this, mouseX, mouseY))
        {
            this.renderComponentTooltip(matrixStack, getTooltip(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(SCREEN_PIZZA);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);

        PIZZA_IMAGE.draw(matrixStack, this, tileEntity.isRaw() ? 1 : 112, 159);
        PIZZA_PATTERN.draw(matrixStack, this, tileEntity.isRaw() ? 56 : 167, 158);
        HUNGER_INFO.draw(matrixStack, this, 127, 30);
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

        public void draw(MatrixStack matrixStack, ScreenPizza screen, int U, int V)
        {
            screen.blit(matrixStack, screen.getGuiLeft() + X, screen.getGuiTop() + Y, U, V, W, H);
        }

        public boolean inButton(ScreenPizza screen, int mouseX, int mouseY)
        {
            mouseX -= screen.getGuiLeft();
            mouseY -= screen.getGuiTop();
            return X <= mouseX && mouseX <= X + W && Y <= mouseY && mouseY <= Y + H;
        }
    }
}
