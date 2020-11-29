// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.item;

import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.modules.Module;

public class ComponentItemMod extends ComponentItem
{
    final Module Mod;
    
    public ComponentItemMod(final Module p_Mod, final String p_DisplayText, final String p_Description, final int p_Flags, final int p_State, final ComponentItemListener p_Listener, final float p_Width, final float p_Height) {
        super(p_DisplayText, p_Description, p_Flags, p_State, p_Listener, p_Width, p_Height);
        this.Mod = p_Mod;
    }
    
    @Override
    public String GetDisplayText() {
        String l_DisplayText = this.Mod.getDisplayName();
        for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth(); l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
        return l_DisplayText;
    }
    
    @Override
    public String GetDescription() {
        return this.Mod.getDesc();
    }
    
    @Override
    public void Update() {
    }
    
    @Override
    public boolean HasState(final int p_State) {
        if ((p_State & ComponentItem.Clicked) != 0x0) {
            return this.Mod.isEnabled();
        }
        return super.HasState(p_State);
    }
}
