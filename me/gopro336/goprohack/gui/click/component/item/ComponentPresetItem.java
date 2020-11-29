// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.item;

import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.preset.Preset;

public class ComponentPresetItem extends ComponentItem
{
    private Preset _preset;
    
    public ComponentPresetItem(final Preset preset, final int flags, final int state, final ComponentItemListener listener, final float width, final float height) {
        super(preset.getName(), "", flags, state, listener, width, height);
        this._preset = preset;
    }
    
    @Override
    public boolean HasState(final int p_State) {
        if ((p_State & ComponentItem.Clicked) != 0x0) {
            return this._preset.isActive();
        }
        return super.HasState(p_State);
    }
    
    public Preset getPreset() {
        return this._preset;
    }
}
