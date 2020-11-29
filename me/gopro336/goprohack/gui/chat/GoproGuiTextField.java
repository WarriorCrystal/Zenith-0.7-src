//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.chat;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import me.gopro336.goprohack.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GoproGuiTextField extends GuiTextField
{
    public boolean HandleCommandsInternal;
    
    public GoproGuiTextField(final int componentId, final FontRenderer RenderUtilObj, final int x, final int y, final int par5Width, final int par6Height) {
        super(componentId, RenderUtilObj, x, y, par5Width, par6Height);
        this.HandleCommandsInternal = true;
    }
    
    public GoproGuiTextField(final int componentId, final FontRenderer RenderUtilObj, final int x, final int y, final int par5Width, final int par6Height, final boolean b) {
        this(componentId, RenderUtilObj, x, y, par5Width, par6Height);
        this.HandleCommandsInternal = b;
    }
    
    public void setSelectionPos(int position) {
        final int i = this.text.length();
        if (position > i) {
            position = i;
        }
        if (position < 0) {
            position = 0;
        }
        this.selectionEnd = position;
        if (this.lineScrollOffset > i) {
            this.lineScrollOffset = i;
        }
        final int j = this.getWidth();
        final String s = RenderUtil.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
        final int k = s.length() + this.lineScrollOffset;
        if (position == this.lineScrollOffset) {
            this.lineScrollOffset -= RenderUtil.trimStringToWidth(this.text, j, true).length();
        }
        if (position > k) {
            this.lineScrollOffset += position - k;
        }
        else if (position <= this.lineScrollOffset) {
            this.lineScrollOffset -= this.lineScrollOffset - position;
        }
        this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean flag = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
        if (this.canLoseFocus) {
            this.setFocused(flag);
        }
        if (this.isFocused && flag && mouseButton == 0) {
            int i = mouseX - this.x;
            if (this.enableBackgroundDrawing) {
                i -= 4;
            }
            final String s = RenderUtil.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(RenderUtil.trimStringToWidth(s, i).length() + this.lineScrollOffset);
            return true;
        }
        return false;
    }
    
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
                drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
            }
            final int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            final int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            final String s = RenderUtil.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            final boolean flag = j >= 0 && j <= s.length();
            final boolean flag2 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            final int l = this.enableBackgroundDrawing ? (this.x + 4) : this.x;
            final int i2 = this.enableBackgroundDrawing ? (this.y + (this.height - 8) / 2) : this.y;
            int j2 = l;
            if (k > s.length()) {
                k = s.length();
            }
            if (!s.isEmpty()) {
                final String s2 = flag ? s.substring(0, j) : s;
                j2 = (int)RenderUtil.drawStringWithShadow(s2, (float)l, (float)i2, 16777215) + 6;
            }
            final boolean flag3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k2 = j2;
            if (!flag) {
                k2 = ((j > 0) ? (l + this.width) : l);
            }
            else if (flag3) {
                k2 = j2 - 1;
                --j2;
            }
            if (!s.isEmpty() && flag && j < s.length()) {
                j2 = (int)RenderUtil.drawStringWithShadow(s.substring(j), (float)j2, (float)i2, i);
            }
            if (flag2) {
                if (flag3) {
                    Gui.drawRect(k2, i2 - 1, k2 + 1, (int)(i2 + 1 + RenderUtil.getStringHeight("_")), -3092272);
                }
                else {
                    RenderUtil.drawStringWithShadow("_", (float)k2, (float)i2, i);
                }
            }
            if (k != j) {
                final int l2 = (int)(l + RenderUtil.getStringWidth(s.substring(0, k)));
                this.drawSelectionBox(k2, i2 - 1, l2 - 1, i2 + 1 + (int)RenderUtil.getStringHeight("_"));
            }
        }
    }
    
    private void drawSelectionBox(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            final int i = startX;
            startX = endX;
            endX = i;
        }
        if (startY < endY) {
            final int j = startY;
            startY = endY;
            endY = j;
        }
        if (endX > this.x + this.width) {
            endX = this.x + this.width;
        }
        if (startX > this.x + this.width) {
            startX = this.x + this.width;
        }
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)startX, (double)endY, 0.0).endVertex();
        bufferbuilder.pos((double)endX, (double)endY, 0.0).endVertex();
        bufferbuilder.pos((double)endX, (double)startY, 0.0).endVertex();
        bufferbuilder.pos((double)startX, (double)startY, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
}
