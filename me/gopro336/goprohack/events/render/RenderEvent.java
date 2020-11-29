// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import me.gopro336.goprohack.events.MinecraftEvent;

public class RenderEvent extends MinecraftEvent
{
    private float _partialTicks;
    
    public RenderEvent(final float partialTicks) {
        this._partialTicks = partialTicks;
    }
    
    @Override
    public float getPartialTicks() {
        return this._partialTicks;
    }
}
