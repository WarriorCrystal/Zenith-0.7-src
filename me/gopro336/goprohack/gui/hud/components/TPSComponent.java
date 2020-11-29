// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.managers.TickRateManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class TPSComponent extends HudComponentItem
{
    public TPSComponent() {
        super("TPS", 2.0f, 125.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String tickrate = String.format(ChatFormatting.GRAY + "TPS%s %.2f", ChatFormatting.WHITE, TickRateManager.Get().getTickRate());
        RenderUtil.drawStringWithShadow(tickrate, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(tickrate));
        this.SetHeight(RenderUtil.getStringHeight(tickrate) + 1.0f);
    }
}
