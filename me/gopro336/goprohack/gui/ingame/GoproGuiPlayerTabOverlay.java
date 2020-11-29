//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.ingame;

import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.network.NetHandlerPlayClient;
import me.gopro336.goprohack.main.GoproHack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.client.renderer.GlStateManager;
import me.gopro336.goprohack.modules.ui.HudModule;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.client.network.NetworkPlayerInfo;
import javax.annotation.Nullable;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;

public class GoproGuiPlayerTabOverlay extends GuiPlayerTabOverlay
{
    public GoproGuiPlayerTabOverlay(final Minecraft mcIn, final GuiIngame guiIngameIn) {
        super(mcIn, guiIngameIn);
    }
    
    public void renderPlayerlist(final int width, final Scoreboard scoreboardIn, @Nullable final ScoreObjective scoreObjectiveIn) {
        final NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
        List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)GoproGuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        for (final NetworkPlayerInfo networkplayerinfo : list) {
            int k = this.mc.fontRenderer.getStringWidth(this.getPlayerName(networkplayerinfo));
            i = Math.max(i, k);
            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreCriteria.EnumRenderType.HEARTS) {
                k = this.mc.fontRenderer.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, k);
            }
        }
        list = list.subList(0, Math.min(list.size(), HudModule.ExtraTab.getValue()));
        int i2;
        int l3;
        int j2;
        for (l3 = (i2 = list.size()), j2 = 1; i2 > 20; i2 = (l3 + j2 - 1) / j2) {
            ++j2;
        }
        final boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
        int m;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
                m = 90;
            }
            else {
                m = j;
            }
        }
        else {
            m = 0;
        }
        final int i3 = Math.min(j2 * ((flag ? 9 : 0) + i + m + 13), width - 50) / j2;
        final int j3 = width / 2 - (i3 * j2 + (j2 - 1) * 5) / 2;
        int k2 = 10;
        int l4 = i3 * j2 + (j2 - 1) * 5;
        List<String> list2 = null;
        if (this.header != null) {
            list2 = (List<String>)this.mc.fontRenderer.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
            for (final String s : list2) {
                l4 = Math.max(l4, this.mc.fontRenderer.getStringWidth(s));
            }
        }
        List<String> list3 = null;
        if (this.footer != null) {
            list3 = (List<String>)this.mc.fontRenderer.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
            for (final String s2 : list3) {
                l4 = Math.max(l4, this.mc.fontRenderer.getStringWidth(s2));
            }
        }
        if (list2 != null) {
            drawRect(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + list2.size() * this.mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);
            for (final String s3 : list2) {
                final int i4 = this.mc.fontRenderer.getStringWidth(s3);
                this.mc.fontRenderer.drawStringWithShadow(s3, (float)(width / 2 - i4 / 2), (float)k2, -1);
                k2 += this.mc.fontRenderer.FONT_HEIGHT;
            }
            ++k2;
        }
        drawRect(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + i2 * 9, Integer.MIN_VALUE);
        for (int k3 = 0; k3 < l3; ++k3) {
            final int l5 = k3 / i2;
            final int i5 = k3 % i2;
            int j4 = j3 + l5 * i3 + l5 * 5;
            final int k4 = k2 + i5 * 9;
            drawRect(j4, k4, j4 + i3, k4 + 8, 553648127);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (k3 < list.size()) {
                final NetworkPlayerInfo networkplayerinfo2 = list.get(k3);
                final GameProfile gameprofile = networkplayerinfo2.getGameProfile();
                if (flag) {
                    final EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                    final boolean flag2 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo2.getLocationSkin());
                    final int l6 = 8 + (flag2 ? 8 : 0);
                    final int i6 = 8 * (flag2 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(j4, k4, 8.0f, (float)l6, 8, i6, 8, 8, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        final int j5 = 8 + (flag2 ? 8 : 0);
                        final int k5 = 8 * (flag2 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j4, k4, 40.0f, (float)j5, 8, k5, 8, 8, 64.0f, 64.0f);
                    }
                    j4 += 9;
                }
                final String s4 = this.getPlayerName(networkplayerinfo2);
                if (networkplayerinfo2.getGameType() == GameType.SPECTATOR) {
                    this.mc.fontRenderer.drawStringWithShadow(TextFormatting.ITALIC + s4, (float)j4, (float)k4, -1862270977);
                }
                else {
                    int l_Color = -1;
                    if (this.mc.player.getName().equalsIgnoreCase(s4)) {
                        l_Color = 2804973;
                    }
                    else if (GoproHack.GetFriendManager().IsFriend(s4)) {
                        l_Color = 6618880;
                    }
                    this.mc.fontRenderer.drawStringWithShadow(s4, (float)j4, (float)k4, l_Color);
                }
                if (scoreObjectiveIn != null && networkplayerinfo2.getGameType() != GameType.SPECTATOR) {
                    final int k6 = j4 + i + 1;
                    final int l7 = k6 + m;
                    if (l7 - k6 > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, k4, gameprofile.getName(), k6, l7, networkplayerinfo2);
                    }
                }
                this.drawPing(i3, j4 - (flag ? 9 : 0), k4, networkplayerinfo2);
            }
        }
        if (list3 != null) {
            k2 = k2 + i2 * 9 + 1;
            drawRect(width / 2 - l4 / 2 - 1, k2 - 1, width / 2 + l4 / 2 + 1, k2 + list3.size() * this.mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);
            for (final String s5 : list3) {
                final int j6 = this.mc.fontRenderer.getStringWidth(s5);
                this.mc.fontRenderer.drawStringWithShadow(s5, (float)(width / 2 - j6 / 2), (float)k2, -1);
                k2 += this.mc.fontRenderer.FONT_HEIGHT;
            }
        }
    }
    
    private void drawScoreboardValues(final ScoreObjective objective, final int p_175247_2_, final String name, final int p_175247_4_, final int p_175247_5_, final NetworkPlayerInfo info) {
        final int i = objective.getScoreboard().getOrCreateScore(name, objective).getScorePoints();
        if (objective.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
            this.mc.getTextureManager().bindTexture(GoproGuiPlayerTabOverlay.ICONS);
            if (this.lastTimeOpened == info.getRenderVisibilityId()) {
                if (i < info.getLastHealth()) {
                    info.setLastHealthTime(Minecraft.getSystemTime());
                    info.setHealthBlinkTime((long)(this.guiIngame.getUpdateCounter() + 20));
                }
                else if (i > info.getLastHealth()) {
                    info.setLastHealthTime(Minecraft.getSystemTime());
                    info.setHealthBlinkTime((long)(this.guiIngame.getUpdateCounter() + 10));
                }
            }
            if (Minecraft.getSystemTime() - info.getLastHealthTime() > 1000L || this.lastTimeOpened != info.getRenderVisibilityId()) {
                info.setLastHealth(i);
                info.setDisplayHealth(i);
                info.setLastHealthTime(Minecraft.getSystemTime());
            }
            info.setRenderVisibilityId(this.lastTimeOpened);
            info.setLastHealth(i);
            final int j = MathHelper.ceil(Math.max(i, info.getDisplayHealth()) / 2.0f);
            final int k = Math.max(MathHelper.ceil((float)(i / 2)), Math.max(MathHelper.ceil((float)(info.getDisplayHealth() / 2)), 10));
            final boolean flag = info.getHealthBlinkTime() > this.guiIngame.getUpdateCounter() && (info.getHealthBlinkTime() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;
            if (j > 0) {
                final float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0f);
                if (f > 3.0f) {
                    for (int l = j; l < k; ++l) {
                        this.drawTexturedModalRect(p_175247_4_ + l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                    }
                    for (int j2 = 0; j2 < j; ++j2) {
                        this.drawTexturedModalRect(p_175247_4_ + j2 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                        if (flag) {
                            if (j2 * 2 + 1 < info.getDisplayHealth()) {
                                this.drawTexturedModalRect(p_175247_4_ + j2 * f, (float)p_175247_2_, 70, 0, 9, 9);
                            }
                            if (j2 * 2 + 1 == info.getDisplayHealth()) {
                                this.drawTexturedModalRect(p_175247_4_ + j2 * f, (float)p_175247_2_, 79, 0, 9, 9);
                            }
                        }
                        if (j2 * 2 + 1 < i) {
                            this.drawTexturedModalRect(p_175247_4_ + j2 * f, (float)p_175247_2_, (j2 >= 10) ? 160 : 52, 0, 9, 9);
                        }
                        if (j2 * 2 + 1 == i) {
                            this.drawTexturedModalRect(p_175247_4_ + j2 * f, (float)p_175247_2_, (j2 >= 10) ? 169 : 61, 0, 9, 9);
                        }
                    }
                }
                else {
                    final float f2 = MathHelper.clamp(i / 20.0f, 0.0f, 1.0f);
                    final int i2 = (int)((1.0f - f2) * 255.0f) << 16 | (int)(f2 * 255.0f) << 8;
                    String s = "" + i / 2.0f;
                    if (p_175247_5_ - this.mc.fontRenderer.getStringWidth(s + "hp") >= p_175247_4_) {
                        s += "hp";
                    }
                    this.mc.fontRenderer.drawStringWithShadow(s, (float)((p_175247_5_ + p_175247_4_) / 2 - this.mc.fontRenderer.getStringWidth(s) / 2), (float)p_175247_2_, i2);
                }
            }
        }
        else {
            final String s2 = TextFormatting.YELLOW + "" + i;
            this.mc.fontRenderer.drawStringWithShadow(s2, (float)(p_175247_5_ - this.mc.fontRenderer.getStringWidth(s2)), (float)p_175247_2_, 16777215);
        }
    }
}
