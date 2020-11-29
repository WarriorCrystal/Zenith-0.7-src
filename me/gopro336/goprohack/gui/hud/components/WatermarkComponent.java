//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.main.Wrapper;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class WatermarkComponent extends HudComponentItem
{
    public final Value<Boolean> Reliant;
    private static String WatermarkString;
    
    public WatermarkComponent() {
        super("Watermark", 2.0f, 2.0f);
        this.Reliant = new Value<Boolean>("Gopro", new String[] { "" }, "Shows goprohack text instead of zenith", false);
        this.SetHidden(false);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        if (this.Reliant.getValue()) {
            final String l_Text = "GoproHack Beta 0.6";
            Wrapper.GetMC().fontRenderer.drawStringWithShadow("GoproHack Beta 0.6", this.GetX(), this.GetY(), 9765121);
            this.SetWidth((float)Wrapper.GetMC().fontRenderer.getStringWidth("GoproHack Beta 0.6"));
            this.SetHeight((float)Wrapper.GetMC().fontRenderer.FONT_HEIGHT);
        }
        else {
            RenderUtil.drawStringWithShadow(WatermarkComponent.WatermarkString, this.GetX(), this.GetY(), 15319296);
            this.SetWidth(RenderUtil.getStringWidth(WatermarkComponent.WatermarkString));
            this.SetHeight(RenderUtil.getStringWatermarkHeight(WatermarkComponent.WatermarkString));
        }
    }
    
    static {
        WatermarkComponent.WatermarkString = "Zenith" + ChatFormatting.WHITE + " " + "Beta 0.6";
    }
}
