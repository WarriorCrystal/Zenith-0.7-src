//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import java.util.Iterator;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.managers.NotificationManager;
import me.gopro336.goprohack.gui.hud.GuiHudEditor;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class NotificationComponent extends HudComponentItem
{
    public NotificationComponent() {
        super("Notifications", 200.0f, 60.0f);
        this.SetHidden(false);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        if (this.mc.currentScreen instanceof GuiHudEditor && NotificationManager.Get().Notifications.isEmpty()) {
            final String placeholder = "Notifications";
            this.SetWidth(RenderUtil.getStringWidth("Notifications"));
            this.SetHeight(RenderUtil.getStringHeight("Notifications"));
            RenderUtil.drawStringWithShadow("Notifications", this.GetX(), this.GetY(), 16777215);
            return;
        }
        final Iterator<NotificationManager.Notification> l_Itr = NotificationManager.Get().Notifications.iterator();
        float l_Y = this.GetY();
        float l_MaxWidth = 0.0f;
        while (l_Itr.hasNext()) {
            final NotificationManager.Notification l_Notification = l_Itr.next();
            if (l_Notification.IsDecayed()) {
                NotificationManager.Get().Notifications.remove(l_Notification);
            }
            final float l_Width = RenderUtil.getStringWidth(l_Notification.GetDescription()) + 1.5f;
            RenderUtil.drawRect(this.GetX() - 1.5f, l_Y, this.GetX() + l_Width, l_Y + 13.0f, 1963986960);
            RenderUtil.drawStringWithShadow(l_Notification.GetDescription(), this.GetX(), l_Y + l_Notification.GetY(), 16777215);
            if (l_Width >= l_MaxWidth) {
                l_MaxWidth = l_Width;
            }
            l_Y -= 13.0f;
        }
        this.SetHeight(10.0f);
        this.SetWidth(l_MaxWidth);
    }
}
