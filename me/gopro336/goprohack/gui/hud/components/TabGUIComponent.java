// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class TabGUIComponent extends HudComponentItem
{
    public TabGUIComponent() {
        super("TabGUI", 3.0f, 12.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        int l_Y = 0;
        RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), -1723580609);
        RenderUtil.drawOutlineRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 3.0f, 4472639);
        final String[] l_Array = { "Combat", "Exploit", "Misc", "Transport", "Render", "World" };
        float l_MaxWidth = 0.0f;
        final String l_Hovered = "Combat";
        for (final String l_String : l_Array) {
            if (l_Hovered == l_String) {
                RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + 12.0f, -1727870465);
                RenderUtil.drawOutlineRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + 12.0f, 3.0f, -1723580609);
            }
            final float l_Width = RenderUtil.drawStringWithShadow(l_String, this.GetX() + 2.0f, this.GetY() + l_Y, 13750737);
            if (l_Width >= l_MaxWidth) {
                l_MaxWidth = l_Width;
            }
            l_Y += 11;
        }
        this.SetWidth(l_MaxWidth + 3.5f);
        this.SetHeight((float)l_Y);
    }
}
