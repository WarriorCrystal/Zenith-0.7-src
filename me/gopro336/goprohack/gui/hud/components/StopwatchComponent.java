// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import java.util.concurrent.TimeUnit;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.misc.StopWatchModule;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class StopwatchComponent extends HudComponentItem
{
    private StopWatchModule Stopwatch;
    
    public StopwatchComponent() {
        super("Stopwatch", 2.0f, 275.0f);
        this.Stopwatch = null;
        this.Stopwatch = (StopWatchModule)ModuleManager.Get().GetMod(StopWatchModule.class);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String l_Seconds = ChatFormatting.GRAY + "Seconds " + ChatFormatting.WHITE + TimeUnit.MILLISECONDS.toSeconds(this.Stopwatch.ElapsedMS - this.Stopwatch.StartMS);
        RenderUtil.drawStringWithShadow(l_Seconds, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(l_Seconds));
        this.SetHeight(RenderUtil.getStringHeight(l_Seconds));
    }
}
