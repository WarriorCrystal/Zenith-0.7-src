// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import me.gopro336.goprohack.main.GoproHack;
import me.gopro336.goprohack.util.render.GoproFontRenderer;

public class FontManager
{
    public GoproFontRenderer[] FontRenderers;
    public GoproFontRenderer TWCenMt18;
    public GoproFontRenderer TwCenMtStd28;
    public GoproFontRenderer VerdanaBold;
    public GoproFontRenderer Comfortaa;
    public GoproFontRenderer Comfortaa48;
    
    public FontManager() {
        this.FontRenderers = new GoproFontRenderer[25];
        this.TWCenMt18 = null;
        this.TwCenMtStd28 = null;
        this.VerdanaBold = null;
        this.Comfortaa = null;
        this.Comfortaa48 = null;
    }
    
    public void Load() {
        this.TWCenMt18 = new GoproFontRenderer("TwCenMT-Bold-Italic", 18.0f);
        this.TwCenMtStd28 = new GoproFontRenderer("Comfortaa-regular", 28.14f);
        this.VerdanaBold = new GoproFontRenderer("VerdanaBold", 20.0f);
        this.Comfortaa = new GoproFontRenderer("Comfortaa-regular", 18.0f);
        this.Comfortaa48 = new GoproFontRenderer("Comfortaa-regular", 48.0f);
        for (int l_I = 0; l_I < this.FontRenderers.length; ++l_I) {
            this.FontRenderers[l_I] = new GoproFontRenderer("TwCenMT-Bold-Italic", (float)l_I);
        }
    }
    
    public void LoadCustomFont(final String customFont) {
        for (int l_I = 0; l_I < this.FontRenderers.length; ++l_I) {
            this.FontRenderers[l_I] = new GoproFontRenderer(customFont, (float)l_I);
        }
    }
    
    public GoproFontRenderer GetFontBySize(int p_Size) {
        if (p_Size > this.FontRenderers.length) {
            p_Size = this.FontRenderers.length - 1;
        }
        return this.FontRenderers[p_Size];
    }
    
    public float DrawStringWithShadow(final String p_Name, final float p_X, final float p_Y, final int p_Color) {
        return (float)this.FontRenderers[22].drawStringWithShadow(p_Name, p_X, p_Y, p_Color);
    }
    
    public float GetStringHeight(final String p_Name) {
        return this.FontRenderers[22].getStringHeight(p_Name);
    }
    
    public float GetStringWatermarkHeight(final String p_Name) {
        return this.FontRenderers[22].getStringWatermarkHeight(p_Name);
    }
    
    public float GetStringWidth(final String p_Name) {
        return this.FontRenderers[22].getStringWidth(p_Name);
    }
    
    public static FontManager Get() {
        return GoproHack.GetFontManager();
    }
}
