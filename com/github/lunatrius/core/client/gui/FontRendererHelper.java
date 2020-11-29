//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.client.gui;

import net.minecraft.client.gui.FontRenderer;

public class FontRendererHelper
{
    public static void drawLeftAlignedString(final FontRenderer fontRenderer, final String str, final int x, final int y, final int color) {
        fontRenderer.drawStringWithShadow(str, (float)x, (float)y, color);
    }
    
    public static void drawCenteredString(final FontRenderer fontRenderer, final String str, final int x, final int y, final int color) {
        fontRenderer.drawStringWithShadow(str, (float)(x - fontRenderer.getStringWidth(str) / 2), (float)y, color);
    }
    
    public static void drawRightAlignedString(final FontRenderer fontRenderer, final String str, final int x, final int y, final int color) {
        fontRenderer.drawStringWithShadow(str, (float)(x - fontRenderer.getStringWidth(str)), (float)y, color);
    }
}
