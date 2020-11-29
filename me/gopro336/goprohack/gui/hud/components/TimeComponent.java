// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import java.util.Date;
import java.text.SimpleDateFormat;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class TimeComponent extends HudComponentItem
{
    public TimeComponent() {
        super("Time", 2.0f, 110.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String time = new SimpleDateFormat("h:mm a").format(new Date());
        RenderUtil.drawStringWithShadow(time, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(time));
        this.SetHeight(RenderUtil.getStringHeight(time));
    }
}
