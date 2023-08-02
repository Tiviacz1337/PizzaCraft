package com.tiviacz.pizzacraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CyclingSlotBackground
{
    private static final int f_265999_ = 30;
    private static final int f_265991_ = 16;
    private static final int f_265954_ = 4;
    private final int f_265871_;
    private List<ResourceLocation> f_266106_ = List.of();
    private int f_266101_;
    private int f_265868_;

    public CyclingSlotBackground(int p_267314_)
    {
        this.f_265871_ = p_267314_;
    }

    public void m_266287_(List<ResourceLocation> p_267074_) {
        if (!this.f_266106_.equals(p_267074_)) {
            this.f_266106_ = p_267074_;
            this.f_265868_ = 0;
        }

        if (!this.f_266106_.isEmpty() && ++this.f_266101_ % 30 == 0) {
            this.f_265868_ = (this.f_265868_ + 1) % this.f_266106_.size();
        }

    }

    public void m_266270_(AbstractContainerMenu p_267293_, PoseStack p_266914_, float p_266785_, int p_266711_, int p_266841_) {
        Slot slot = p_267293_.getSlot(this.f_265871_);
        if (!this.f_266106_.isEmpty() && !slot.hasItem()) {
            boolean flag = this.f_266106_.size() > 1 && this.f_266101_ >= 30;
            float f = flag ? this.m_266271_(p_266785_) : 1.0F;
            if (f < 1.0F) {
                int i = Math.floorMod(this.f_265868_ - 1, this.f_266106_.size());
                this.m_266427_(slot, this.f_266106_.get(i), 1.0F - f, p_266914_, p_266711_, p_266841_);
            }

            this.m_266427_(slot, this.f_266106_.get(this.f_265868_), f, p_266914_, p_266711_, p_266841_);
        }
    }

    private void m_266427_(Slot p_266726_, ResourceLocation p_267015_, float p_267083_, PoseStack p_266980_, int p_266972_, int p_266696_) {
        TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(p_267015_);
        RenderSystem.setShaderTexture(0, textureatlassprite.atlas().location());
        m_266587_(p_266980_, p_266972_ + p_266726_.x, p_266696_ + p_266726_.y, 0, 16, 16, textureatlassprite, 1.0F, 1.0F, 1.0F, p_267083_);
    }

    private float m_266271_(float p_266904_) {
        float f = (float)(this.f_266101_ % 30) + p_266904_;
        return Math.min(f, 4.0F) / 4.0F;
    }

    public static void m_266587_(PoseStack p_267237_, int p_266847_, int p_266730_, int p_266944_, int p_266929_, int p_266735_, TextureAtlasSprite p_266828_, float p_267032_, float p_267019_, float p_267126_, float p_266756_) {
        m_266344_(p_267237_.last().pose(), p_266847_, p_266847_ + p_266929_, p_266730_, p_266730_ + p_266735_, p_266944_, p_266828_.getU0(), p_266828_.getU1(), p_266828_.getV0(), p_266828_.getV1(), p_267032_, p_267019_, p_267126_, p_266756_);
    }

    private static void m_266344_(Matrix4f p_267291_, int p_266998_, int p_266799_, int p_267254_, int p_267187_, int p_267149_, float p_266788_, float p_266950_, float p_267255_, float p_267102_, float p_267305_, float p_267134_, float p_266747_, float p_266801_) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.enableBlend();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(p_267291_, (float)p_266998_, (float)p_267254_, (float)p_267149_).color(p_267305_, p_267134_, p_266747_, p_266801_).uv(p_266788_, p_267255_).endVertex();
        bufferbuilder.vertex(p_267291_, (float)p_266998_, (float)p_267187_, (float)p_267149_).color(p_267305_, p_267134_, p_266747_, p_266801_).uv(p_266788_, p_267102_).endVertex();
        bufferbuilder.vertex(p_267291_, (float)p_266799_, (float)p_267187_, (float)p_267149_).color(p_267305_, p_267134_, p_266747_, p_266801_).uv(p_266950_, p_267102_).endVertex();
        bufferbuilder.vertex(p_267291_, (float)p_266799_, (float)p_267254_, (float)p_267149_).color(p_267305_, p_267134_, p_266747_, p_266801_).uv(p_266950_, p_267255_).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }
}