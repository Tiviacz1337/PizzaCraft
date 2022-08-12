package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.PizzaBlockEntity;
import com.tiviacz.pizzacraft.container.PizzaMenu;
import com.tiviacz.pizzacraft.util.FoodUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenPizza extends AbstractContainerScreen<PizzaMenu> implements MenuAccess<PizzaMenu>
{
    public static final ResourceLocation SCREEN_PIZZA = new ResourceLocation(PizzaCraft.MODID, "textures/gui/pizza.png");
    private final ScreenImage PIZZA_IMAGE = new ScreenImage(61, 9, 54, 54);
    private final ScreenImage PIZZA_PATTERN = new ScreenImage(61, 9, 54, 54);
    private final ScreenImage HUNGER_INFO = new ScreenImage(127, 30, 13, 13);
    private final PizzaBlockEntity blockEntity;

    public ScreenPizza(PizzaMenu screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, titleIn);

        this.blockEntity = screenContainer.blockEntity;

        this.leftPos = 0;
        this.topPos = 0;

        this.imageWidth = 176;
        this.imageHeight = 158;

        this.inventoryLabelY -= 6;
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        this.font.draw(poseStack, this.blockEntity.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    public List<Component> getTooltip()
    {
        List<Component> components = new ArrayList<>();
        components.add(new TranslatableComponent("information.pizzacraft.hunger", FoodUtils.getHungerForSlice(blockEntity.getRefillmentValues().getFirst(), false), ((blockEntity.getRefillmentValues().getFirst() % 7 != 0) ? " (+" + blockEntity.getRefillmentValues().getFirst() % 7 + ")" : ""), blockEntity.getRefillmentValues().getFirst()).withStyle(ChatFormatting.BLUE));
        components.add(new TranslatableComponent("information.pizzacraft.saturation", (float)(Math.round(blockEntity.getRefillmentValues().getSecond() / 7 * 100.0) / 100.0), (float)(Math.round(blockEntity.getRefillmentValues().getSecond() * 100.0) / 100.0)).withStyle(ChatFormatting.BLUE));

        if(!blockEntity.getEffects().isEmpty())
        {
            components.add(new TranslatableComponent("information.pizzacraft.effects").withStyle(ChatFormatting.GOLD));

            for(Pair<MobEffectInstance, Float> pair : blockEntity.getEffects())
            {
                components.add(new TranslatableComponent(pair.getFirst().getDescriptionId()).withStyle(pair.getFirst().getEffect().getCategory().getTooltipFormatting()));
            }
        }

        return components;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);

        if(this.HUNGER_INFO.inButton(this, mouseX, mouseY))
        {
            this.renderComponentTooltip(poseStack, getTooltip(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SCREEN_PIZZA);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);

        PIZZA_IMAGE.draw(poseStack, this, blockEntity.isRaw() ? 1 : 112, 159);
        PIZZA_PATTERN.draw(poseStack, this, blockEntity.isRaw() ? 56 : 167, 158);
        HUNGER_INFO.draw(poseStack, this, 127, 30);
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

        public void draw(PoseStack poseStack, ScreenPizza screen, int U, int V)
        {
            screen.blit(poseStack, screen.getGuiLeft() + X, screen.getGuiTop() + Y, U, V, W, H);
        }

        public boolean inButton(ScreenPizza screen, int mouseX, int mouseY)
        {
            mouseX -= screen.getGuiLeft();
            mouseY -= screen.getGuiTop();
            return X <= mouseX && mouseX <= X + W && Y <= mouseY && mouseY <= Y + H;
        }
    }
}
