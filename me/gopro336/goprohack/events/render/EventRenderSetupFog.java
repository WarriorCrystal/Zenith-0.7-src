// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderSetupFog extends MinecraftEvent
{
    public int StartCoords;
    public float PartialTicks;
    
    public EventRenderSetupFog(final int startCoords, final float partialTicks) {
        this.StartCoords = startCoords;
        this.PartialTicks = partialTicks;
    }
}
