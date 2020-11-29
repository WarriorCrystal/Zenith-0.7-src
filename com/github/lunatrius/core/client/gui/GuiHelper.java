//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.RenderItem;

public class GuiHelper
{
    private static final RenderItem renderItem;
    
    public static void drawItemStackWithSlot(final TextureManager textureManager, final ItemStack itemStack, final int x, final int y) {
        drawItemStackSlot(textureManager, x, y);
        if (itemStack != null && itemStack.getItem() != null) {
            drawItemStack(itemStack, x + 2, y + 2);
        }
    }
    
    public static void drawItemStackSlot(final TextureManager textureManager, final int x, final int y) {
        textureManager.bindTexture(Gui.STAT_ICONS);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        final double uScale = 0.0078125;
        final double vScale = 0.0078125;
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        drawTexturedRectangle(buffer, x + 1, y + 1, x + 1 + 18, y + 1 + 18, 0.0, 0.0, 0.0, 0.140625, 0.140625);
        tessellator.draw();
    }
    
    public static void drawItemStack(final ItemStack itemStack, final int x, final int y) {
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GuiHelper.renderItem.renderItemIntoGUI(itemStack, x, y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
    
    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1) {
        buffer.pos(x0, y0, z).tex(u0, v0).endVertex();
        buffer.pos(x0, y1, z).tex(u0, v1).endVertex();
        buffer.pos(x1, y1, z).tex(u1, v1).endVertex();
        buffer.pos(x1, y0, z).tex(u1, v0).endVertex();
    }
    
    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double textureWidth, final double textureHeight, final int argb) {
        final double u0 = x0 / textureWidth;
        final double v0 = y0 / textureHeight;
        final double u2 = x1 / textureWidth;
        final double v2 = y1 / textureHeight;
        drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u2, v2, argb);
    }
    
    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1, final int argb) {
        final int a = argb >> 24 & 0xFF;
        final int r = argb >> 16 & 0xFF;
        final int g = argb >> 8 & 0xFF;
        final int b = argb & 0xFF;
        drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, r, g, b, a);
    }
    
    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1, final int r, final int g, final int b, final int a) {
        buffer.pos(x0, y0, z).tex(u0, v0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z).tex(u0, v1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z).tex(u1, v1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z).tex(u1, v0).color(r, g, b, a).endVertex();
    }
    
    public static void drawColoredRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final int argb) {
        final int a = argb >> 24 & 0xFF;
        final int r = argb >> 16 & 0xFF;
        final int g = argb >> 8 & 0xFF;
        final int b = argb & 0xFF;
        drawColoredRectangle(buffer, x0, y0, x1, y1, z, r, g, b, a);
    }
    
    public static void drawColoredRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final int r, final int g, final int b, final int a) {
        buffer.pos(x0, y0, z).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z).color(r, g, b, a).endVertex();
    }
    
    public static void drawVerticalGradientRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final int startColor, final int endColor) {
        final int sa = startColor >> 24 & 0xFF;
        final int sr = startColor >> 16 & 0xFF;
        final int sg = startColor >> 8 & 0xFF;
        final int sb = startColor & 0xFF;
        final int ea = endColor >> 24 & 0xFF;
        final int er = endColor >> 16 & 0xFF;
        final int eg = endColor >> 8 & 0xFF;
        final int eb = endColor & 0xFF;
        drawVerticalGradientRectangle(buffer, x0, y0, x1, y1, z, sr, sg, sb, sa, er, eg, eb, ea);
    }
    
    public static void drawVerticalGradientRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final int sr, final int sg, final int sb, final int sa, final int er, final int eg, final int eb, final int ea) {
        buffer.pos(x0, y0, z).color(sr, sg, sb, sa).endVertex();
        buffer.pos(x0, y1, z).color(er, eg, eb, ea).endVertex();
        buffer.pos(x1, y1, z).color(er, eg, eb, ea).endVertex();
        buffer.pos(x1, y0, z).color(sr, sg, sb, sa).endVertex();
    }
    
    public static void drawHorizontalGradientRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final int startColor, final int endColor) {
        final int sa = startColor >> 24 & 0xFF;
        final int sr = startColor >> 16 & 0xFF;
        final int sg = startColor >> 8 & 0xFF;
        final int sb = startColor & 0xFF;
        final int ea = endColor >> 24 & 0xFF;
        final int er = endColor >> 16 & 0xFF;
        final int eg = endColor >> 8 & 0xFF;
        final int eb = endColor & 0xFF;
        drawHorizontalGradientRectangle(buffer, x0, y0, x1, y1, z, sr, sg, sb, sa, er, eg, eb, ea);
    }
    
    public static void drawHorizontalGradientRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final int sr, final int sg, final int sb, final int sa, final int er, final int eg, final int eb, final int ea) {
        buffer.pos(x0, y0, z).color(sr, sg, sb, sa).endVertex();
        buffer.pos(x0, y1, z).color(sr, sg, sb, sa).endVertex();
        buffer.pos(x1, y1, z).color(er, eg, eb, ea).endVertex();
        buffer.pos(x1, y0, z).color(er, eg, eb, ea).endVertex();
    }
    
    static {
        renderItem = Minecraft.getMinecraft().getRenderItem();
    }
}
