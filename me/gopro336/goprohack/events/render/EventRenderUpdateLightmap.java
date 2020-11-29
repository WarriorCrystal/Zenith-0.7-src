// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderUpdateLightmap extends MinecraftEvent
{
    public float PartialTicks;
    
    public EventRenderUpdateLightmap(final float p_PartialTicks) {
        this.PartialTicks = p_PartialTicks;
    }
}
