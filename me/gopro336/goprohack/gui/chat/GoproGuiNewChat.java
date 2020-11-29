//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.chat;

import javax.annotation.Nullable;
import java.util.Iterator;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.ITextComponent;
import me.gopro336.goprohack.managers.FontManager;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;

public class GoproGuiNewChat extends GuiNewChat
{
    public GoproGuiNewChat(final Minecraft mcIn) {
        super(mcIn);
    }
    
    public void drawChat(final int updateCounter) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int i = this.getLineCount();
            final int j = this.drawnChatLines.size();
            final float f = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (j > 0) {
                boolean flag = false;
                if (this.getChatOpen()) {
                    flag = true;
                }
                final float f2 = this.getChatScale();
                final int k = MathHelper.ceil(this.getChatWidth() / f2);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0f, 8.0f, 0.0f);
                GlStateManager.scale(f2, f2, 1.0f);
                int l = 0;
                final int l_I = 0;
                for (int i2 = 0; i2 + this.scrollPos < this.drawnChatLines.size() && i2 < i; ++i2) {
                    final ChatLine chatline = this.drawnChatLines.get(i2 + this.scrollPos);
                    if (chatline != null) {
                        final int j2 = updateCounter - chatline.getUpdatedCounter();
                        if (j2 < 200 || flag) {
                            double d0 = j2 / 200.0;
                            d0 = 1.0 - d0;
                            d0 *= 10.0;
                            d0 = MathHelper.clamp(d0, 0.0, 1.0);
                            d0 *= d0;
                            int l2 = (int)(255.0 * d0);
                            if (flag) {
                                l2 = 255;
                            }
                            l2 *= (int)f;
                            ++l;
                            if (l2 > 3) {
                                final int j3 = -i2 * 10;
                                drawRect(-2, j3 - 10, 0 + k + 4, j3, l2 / 2 << 24);
                                final String s = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                FontManager.Get().VerdanaBold.drawStringWithShadow(s, 0.0f, (float)(j3 - 10), -1);
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }
                if (flag) {
                    final int k2 = (int)FontManager.Get().VerdanaBold.getStringHeight("f");
                    GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                    final int l3 = j * k2 + j;
                    final int i3 = l * k2 + l;
                    final int j4 = this.scrollPos * i3 / j;
                    final int k3 = i3 * i3 / l3;
                    if (l3 != i3) {
                        final int k4 = (j4 > 0) ? 170 : 96;
                        final int l4 = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -j4, 2, -j4 - k3, l4 + (k4 << 24));
                        drawRect(2, -j4, 1, -j4 - k3, 13421772 + (k4 << 24));
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }
    
    @Nullable
    public ITextComponent getChatComponent(final int mouseX, final int mouseY) {
        if (!this.getChatOpen()) {
            return null;
        }
        final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        final int i = scaledresolution.getScaleFactor();
        final float f = this.getChatScale();
        int j = mouseX / i - 2;
        int k = mouseY / i - 40;
        j = MathHelper.floor(j / f);
        k = MathHelper.floor(k / f);
        if (j < 0 || k < 0) {
            return null;
        }
        final int l = Math.min(this.getLineCount(), this.drawnChatLines.size());
        if (j <= MathHelper.floor(this.getChatWidth() / this.getChatScale()) && k < FontManager.Get().VerdanaBold.getStringHeight("U") * l + l) {
            final int i2 = (int)(k / FontManager.Get().VerdanaBold.getStringHeight("U") + this.scrollPos);
            if (i2 >= 0 && i2 < this.drawnChatLines.size()) {
                final ChatLine chatline = this.drawnChatLines.get(i2);
                int j2 = 0;
                for (final ITextComponent itextcomponent : chatline.getChatComponent()) {
                    if (itextcomponent instanceof TextComponentString) {
                        j2 += (int)FontManager.Get().VerdanaBold.getStringWidth(GuiUtilRenderComponents.removeTextColorsIfConfigured(((TextComponentString)itextcomponent).getText(), false));
                        if (j2 > j) {
                            return itextcomponent;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
}
