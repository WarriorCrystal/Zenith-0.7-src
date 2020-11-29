// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.item;

import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.modules.Module;

public class ComponentItemHiddenMod extends ComponentItem
{
    final Module Mod;
    
    public ComponentItemHiddenMod(final Module p_Mod, final String p_DisplayText, final String p_Description, final int p_Flags, final int p_State, final ComponentItemListener p_Listener, final float p_Width, final float p_Height) {
        super(p_DisplayText, p_Description, p_Flags, p_State, p_Listener, p_Width, p_Height);
        this.Mod = p_Mod;
    }
    
    @Override
    public boolean HasState(final int p_State) {
        if ((p_State & ComponentItem.Clicked) != 0x0) {
            return this.Mod.isHidden();
        }
        return super.HasState(p_State);
    }
}
